package com.porter.esas;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends ActionBarActivity implements ActionBar.TabListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;
    HistoryFragment historyFragment;
    PatientsFragment patientsFragment;
    RecentSurveysFragment recentSurveysFragment;
    ProviderInfoFragment providerInfoFragment;
    int userType; //0 = patient, 1 = doctor
    String email;
    String password;
    //patient-only
    String[] patientsProviderInfo;
    ArrayList<Survey> receivedSurveys;
    String surveyJSONStrings;
    boolean historyTableBuilt = false;
    //provider-only
    private SubmitSurveyTask mAuthTask = null;
    private String providersPatientsList;
    private String topSurveys;
    private String[] patientSurveysStrings;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userType = 0;
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        setTitle("Settings");
       userType = extras.getInt("com.porter.user_type");
        email = extras.getString("com.porter.email");
        password = extras.getString("com.porter.password");
        if(userType ==0)
        {
            patientsProviderInfo = new String[3];
            patientsProviderInfo[0]=extras.getString("com.porter.providerName");
            patientsProviderInfo[1]=extras.getString("com.porter.providerPhone");
            patientsProviderInfo[2]=extras.getString("com.porter.providerEmail");
            receivedSurveys = new ArrayList<Survey>();
             surveyJSONStrings = extras.getString("com.porter.receivedSurveysJSON");
           historyFragment = HistoryFragment.newInstance(userType);

        }
        else if(userType==1)
        {
             topSurveys  = extras.getString("com.porter.topSurveys");
             providersPatientsList =extras.getString("com.porter.patientsList");
            patientSurveysStrings=extras.getStringArray("surveystrings");
        }
        Log.e("mylog", "Main received data: " + userType + " " + email + " " + password);


        // Set up the action bar.
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by
            // the adapter. Also specify this Activity object, which implements
            // the TabListener interface, as the callback (listener) for when
            // this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }
        if(userType ==0) {
          //  historyFragment = HistoryFragment.newInstance(userType);
            providerInfoFragment = ProviderInfoFragment.newInstance("","","");
        }
        else
        {
            recentSurveysFragment = RecentSurveysFragment.newInstance();
            patientsFragment = PatientsFragment.newInstance();
        }


    }
    public void setSurveyJSONStrings(String in)
    {
        surveyJSONStrings = in;
    }

    public void setupHistoryTable( ) {




            if (surveyJSONStrings.length() > 0)//send empty from register
            {
                receivedSurveys = new ArrayList<Survey>();
                try {
                    JSONObject surveysJSON = new JSONObject(surveyJSONStrings);
                    JSONArray surveysArray = surveysJSON.getJSONArray("surveys");
                    for (int i = 0; i < surveysArray.length(); i++) {
                        JSONObject survey = (JSONObject) surveysArray.get(i);
                        int[] surveyArray = new int[8];
                        for (int j = 0; j < 8; j++) {

                            surveyArray[j] = Integer.parseInt(survey.get(Survey.SERVER_FIELD_NAMES[j]).toString());
                        }
                        Survey newS = new Survey(surveyArray, "");
                        newS.setDate(Long.parseLong(survey.get("timestamp").toString()), this);
                        receivedSurveys.add(newS);
                    }

                } catch (JSONException e) {
                }
            }


    }
    public void setupProviderPatientsSurveys( ) {

//TODO: set patients list, each owning survey; then do top surveys knowing patients by id

ArrayList<Survey> topSurveysList;
        ArrayList<Patient> patientsList;
        if (providersPatientsList.length() > 0)//send empty from register
        {
            patientsList = new ArrayList<Patient>();
            try {
                JSONObject patientsJSON = new JSONObject(providersPatientsList);
                JSONArray patientsArray = patientsJSON.getJSONArray("patients");
                for (int i = 0; i < patientsArray.length(); i++) {
                    JSONObject patient = (JSONObject) patientsArray.get(i);

                    Patient newP = new Patient(patient);
                    newP.setupSurveys();
                    patientsList.add(newP);
                }

            } catch (JSONException e) {
            }
        }

        if (topSurveys.length() > 0)//send empty from register
        {
            topSurveysList = new ArrayList<Survey>();
            try {
                JSONObject surveysJSON = new JSONObject(topSurveys);
                JSONArray surveysArray = surveysJSON.getJSONArray("surveys");
                for (int i = 0; i < surveysArray.length(); i++) {
                    JSONObject survey = (JSONObject) surveysArray.get(i);
                    int[] surveyArray = new int[8];
                    for (int j = 0; j < 8; j++) {

                        surveyArray[j] = Integer.parseInt(survey.get(Survey.SERVER_FIELD_NAMES[j]).toString());
                    }
                    Survey newS = new Survey(surveyArray, "");
                    newS.setDate(Long.parseLong(survey.get("timestamp").toString()), this);
                    receivedSurveys.add(newS);
                }

            } catch (JSONException e) {
            }
        }


    }

    public String[] getPatientsProviderInfo()
    {
        return patientsProviderInfo;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.
         if(historyFragment!=null && historyFragment.isSet()) {
             setupHistoryTable();
             historyFragment.setGraphSurveys(receivedSurveys);

         }
             mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if(userType == 0) //patient
            {
                if (position == 0) {
                    return historyFragment;
                }
                if(position == 1)
                {
                    return providerInfoFragment;
                }
                if (position == 2) {
                    return SurveyFragment.newInstance(0);
                }
            }
            else//doctor
            {
                if (position == 0) {
                    return recentSurveysFragment;
                }
                if (position == 1) {
                    return patientsFragment;
                }
            }
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            if(userType==0)
                return 3;
            else
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();

            if(userType==0) {
                switch (position) {
                    case 0:
                        return "History";
                    case 1:
                        return "Provider Info";
                    case 2:
                        return "Enter Survey";
                }
            }
            else
            {
                switch (position) {
                    case 0:
                        return "Recent Surveys";
                    case 1:
                        return "Patients";

                }
            }
            return null;
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }
    public void showDatePickerDialog(View v) {
        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
    }
    public void setHistoryDate(int[] selectedDate)
    {

        historyFragment.setDate(selectedDate, receivedSurveys);
    }
    public void submitSurvey(Survey s)
    {
        mAuthTask = new SubmitSurveyTask(s,email, password,this);
        mAuthTask.execute((Void) null);
    }
    public void setProviderInfoFragment(ProviderInfoFragment piFragment)
    {
      this.providerInfoFragment = piFragment;
    }


    public class SubmitSurveyTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;
        private final Survey mSurvey;
        private final MainActivity activity;
        private boolean mySuccess;

        SubmitSurveyTask(Survey s,String email,String password, MainActivity activity) {
            mEmail = email;
            mPassword = password;
            this.activity =activity;
            mSurvey = s;
            mySuccess= false;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://10.0.2.2:3888/v1/surveys");

            try {

                JSONObject json = new JSONObject();
                final String basicAuth = "Basic " + Base64.encodeToString((mEmail+":"+mPassword).getBytes(), Base64.NO_WRAP);
                httppost.setHeader("Authorization", basicAuth);
                 int[] surveyVals = mSurvey.getSurveyValues();
                json.put("pain",surveyVals[0]);
                json.put("drowsiness",surveyVals[1]);
                json.put("nausea",surveyVals[2]);
                json.put("appetite",surveyVals[3]);
                json.put("shortnessofbreath",surveyVals[4]);
                json.put("depression",surveyVals[5]);
                json.put("anxiety",surveyVals[6]);
                json.put("wellbeing",surveyVals[7]);


                StringEntity se = new StringEntity( json.toString());
                se.setContentType(new BasicHeader("Content-type", "application/json"));
                httppost.setEntity(se);

                // Execute HTTP Post Request
                HttpResponse response = httpclient.execute(httppost);
                BufferedReader reader = null;


                reader = new BufferedReader(new InputStreamReader(response
                        .getEntity().getContent()));
                String line = null;
                String result = "";
                while ((line = reader.readLine()) != null) {
                    result += line;
                }
                Log.e("mylog", "result" + result);
                HttpGet httpGet3 = new HttpGet("http://10.0.2.2:3888/v1/surveys");

                httpGet3.setHeader("Authorization", basicAuth);

                HttpResponse httpResponse3 = httpclient.execute(httpGet3);

                String surveystringresp = EntityUtils.toString(httpResponse3.getEntity());
                activity.setSurveyJSONStrings(surveystringresp);
                activity.setupHistoryTable();
                if(historyFragment!=null)
                    historyFragment.setGraphSurveys(receivedSurveys);




            } catch (ClientProtocolException e) {
                Log.e("mylog", "didn't connect");
            } catch (IOException e) {
                Log.e("mylog", "didn't connect");
            }
            catch(JSONException e)
            {

            }
return true;

        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;

        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;

        }
    }
}
