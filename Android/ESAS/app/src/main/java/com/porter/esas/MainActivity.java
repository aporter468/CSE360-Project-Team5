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

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
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
    //provider-only
    ArrayList<Survey> surveyList;
    private UserDataTask mAuthTask = null;

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

        }
        Log.e("mylog", "Main received data: " + userType + " " + email + " " + password);

        mAuthTask = new UserDataTask(email, password,this);
        mAuthTask.execute((Void) null);

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
            historyFragment = HistoryFragment.newInstance(userType);
            providerInfoFragment = ProviderInfoFragment.newInstance("","","");
            surveyList = new ArrayList<Survey>();
        }
        else
        {
            recentSurveysFragment = RecentSurveysFragment.newInstance();
            patientsFragment = PatientsFragment.newInstance();
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
        historyFragment.setDate(selectedDate);
    }
    public void submitSurvey()
    {

    }
    public void setProviderInfoFragment(ProviderInfoFragment piFragment)
    {
      this.providerInfoFragment = piFragment;
    }
    public void addSubmittedSurvey(Survey s)
    {
        surveyList.add(s);
    }


    public class UserDataTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;
        private final MainActivity activity;
        private boolean mySuccess;

        UserDataTask(String email, String password, MainActivity activity) {
            mEmail = email;
            mPassword = password;
            this.activity =activity;
            mySuccess= false;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            Log.e("mylog","signin?");
            HttpClient httpclient = new DefaultHttpClient();
            // HttpPost httppost = new HttpPost("http://10.0.2.2:3888/login");
            if(userType==0) //patient data tasks
            {
                try {

                    HttpGet httpGet = new HttpGet("http://10.0.2.2:3888/v1/providers");

                    final String basicAuth = "Basic " + Base64.encodeToString((mEmail + ":" + mPassword).getBytes(), Base64.NO_WRAP);
                    httpGet.setHeader("Authorization", basicAuth);

                    HttpResponse httpResponse = httpclient.execute(httpGet);

                    String resp_body = EntityUtils.toString(httpResponse.getEntity());
                    JSONObject patientsProvider = new JSONObject(resp_body);
                    String name = patientsProvider.get("firstname").toString()+" "+patientsProvider.get("lastname").toString();
                    String phone =  patientsProvider.get("phone").toString();
                    String email = patientsProvider.get("email").toString();


                    mySuccess = true;
                    userType = 0;
                    Log.e("mylog", "user data task success: " + resp_body);

                    return true;

                } catch (ClientProtocolException e) {
                    Log.e("mylog", "didn't connect");
                } catch (IOException e) {
                    Log.e("mylog", "didn't connect");
                } catch (JSONException e) {
                    Log.e("mylog", "json exception (patient)");

                }
            }
            //prxovider data tasks
            else if(userType==1)
            {

            }

            return false;
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
