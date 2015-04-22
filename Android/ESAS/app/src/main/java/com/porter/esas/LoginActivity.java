package com.porter.esas;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.util.Base64;

import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AUTH;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.AuthState;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends Activity implements LoaderCallbacks<Cursor> {

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private int userType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        populateAutoComplete();

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        Button mRegisterButton = (Button) findViewById(R.id.register_button);
        mRegisterButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
              launchRegister();

            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }
    private void launchRegister()
    {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);

    }

    private void populateAutoComplete() {
        getLoaderManager().initLoader(0, null, this);
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    public void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;


        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mAuthTask = new UserLoginTask(email, password,this);
            mAuthTask.execute((Void) null);

        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<String>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addEmailsToAutoComplete(emails);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }


    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(LoginActivity.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        mEmailView.setAdapter(adapter);
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;
        private final LoginActivity activity;
        private boolean mySuccess;
        private String[] patientsProviderInfo;
        private String receivedSurveys;
        private String topSurveys;
        private String patientsList;
        String[] patientsSurveysStrings;
        UserLoginTask(String email, String password, LoginActivity activity) {
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

            try {
//user's own info- attempt login
                HttpGet httpGet = new HttpGet("http://10.0.2.2:3888/v1/patients");

                final String basicAuth = "Basic " + Base64.encodeToString((mEmail+":"+mPassword).getBytes(), Base64.NO_WRAP);
                httpGet.setHeader("Authorization", basicAuth);

                HttpResponse httpResponse = httpclient.execute(httpGet);


                String resp_body = EntityUtils.toString(httpResponse.getEntity());
                JSONObject jsobj = new JSONObject(resp_body);
                String providerId = jsobj.get("providerid").toString();
        //provider data
                HttpGet httpGet2 = new HttpGet("http://10.0.2.2:3888/v1/providers");

                httpGet2.setHeader("Authorization", basicAuth);

                HttpResponse httpResponse2 = httpclient.execute(httpGet2);

                String resp_body2 = EntityUtils.toString(httpResponse2.getEntity());
                JSONObject patientsProvider = new JSONObject(resp_body2);
                String name = patientsProvider.get("firstname").toString()+" "+patientsProvider.get("lastname").toString();
                String phone =  patientsProvider.get("phone").toString();
                String email = patientsProvider.get("email").toString();
                Log.e("mylog","provider name: "+name+" "+phone+" "+email);
                patientsProviderInfo = new String[3];
                patientsProviderInfo[0] = name;
                patientsProviderInfo[1] = phone;
                patientsProviderInfo[2] = email;

//surveys (for this user)
                HttpGet httpGet3 = new HttpGet("http://10.0.2.2:3888/v1/surveys");

                httpGet3.setHeader("Authorization", basicAuth);

                HttpResponse httpResponse3 = httpclient.execute(httpGet3);

                String resp_body3 = EntityUtils.toString(httpResponse3.getEntity());
              // JSONObject surveys = new JSONObject(resp_body3);
                receivedSurveys = resp_body3;
              Log.e("mylog","surveys received: "+receivedSurveys.toString());

                    mySuccess = true;
                      userType = 0;

                return true;

            } catch (ClientProtocolException e) {
                Log.e("mylog", "didn't connect");
            } catch (IOException e) {
                Log.e("mylog", "didn't connect");
            }
            catch(JSONException e)
            {
                Log.e("mylog", "json exception (patient)");

            }
            try {



                //try as provider
                HttpGet httpGet2 = new HttpGet("http://10.0.2.2:3888/v1/providers");
                final String basicAuth = "Basic " + Base64.encodeToString((mEmail+":"+mPassword).getBytes(), Base64.NO_WRAP);
                httpGet2.setHeader("Authorization", basicAuth);

                HttpResponse httpResponse2 = httpclient.execute(httpGet2);


                String resp_body2 = EntityUtils.toString(httpResponse2.getEntity());
                JSONObject jsobj2 = new JSONObject(resp_body2);

                //provider info

                HttpGet httpGet3 = new HttpGet("http://10.0.2.2:3888/v1/providers");

                httpGet3.setHeader("Authorization", basicAuth);

                HttpResponse httpResponse3 = httpclient.execute(httpGet3);

                String resp_body3 = EntityUtils.toString(httpResponse3.getEntity());
                // JSONObject surveys = new JSONObject(resp_body3);
              //  receivedSurveys = resp_body3;
                Log.e("mylog","provider info: "+resp_body3);
//top surveys for this provider
                HttpGet httpGet4 = new HttpGet("http://10.0.2.2:3888/v1/surveys");

                httpGet4.setHeader("Authorization", basicAuth);

                HttpResponse httpResponse4 = httpclient.execute(httpGet4);

                String resp_body4 = EntityUtils.toString(httpResponse4.getEntity());
                // JSONObject surveys = new JSONObject(resp_body3);
                topSurveys = resp_body4;
                Log.e("mylog","top surveys for provider: "+topSurveys);
                HttpGet httpGet5 = new HttpGet("http://10.0.2.2:3888/v1/patients");

                httpGet5.setHeader("Authorization", basicAuth);

                HttpResponse httpResponse5 = httpclient.execute(httpGet5);

                String resp_body5 = EntityUtils.toString(httpResponse5.getEntity());
                // JSONObject surveys = new JSONObject(resp_body3);
                patientsList = resp_body5;
                Log.e("mylog","patients for provider: "+patientsList);
                    JSONObject patientsJSON = new JSONObject(patientsList);
                    JSONArray patientsArray = patientsJSON.getJSONArray("patients");

                 patientsSurveysStrings = new String[patientsArray.length()];
                for (int i = 0; i < patientsArray.length(); i++) {
                        JSONObject patient = (JSONObject) patientsArray.get(i);
                        String id = patient.get("patientid").toString();
                    HttpGet httpGet6 = new HttpGet("http://10.0.2.2:3888/v1/surveys/"+id);
                    httpGet6.setHeader("Authorization", basicAuth);
                    HttpResponse httpResponse6 = httpclient.execute(httpGet6);
                    String resp_body6 = EntityUtils.toString(httpResponse6.getEntity());
                    patientsSurveysStrings[i] = resp_body6;

                    //TODO: request survyes and put it in surveys strings
                    }


                mySuccess = true;
                userType = 1;
                return true;

            } catch (ClientProtocolException e) {
                Log.e("mylog", "didn't connect");
            } catch (IOException e) {
                Log.e("mylog", "didn't connect");
            }
            catch(JSONException e)
            {
                Log.e("mylog", "json exception (provider)ap");

            }

            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (mySuccess) {
                Intent intent = new Intent(activity, MainActivity.class);
                intent.putExtra("com.porter.user_type", userType);
                intent.putExtra("com.porter.email", mEmail);
                intent.putExtra("com.porter.password", mPassword);
                if (userType == 0) {

                    intent.putExtra("com.porter.providerName", patientsProviderInfo[0]);
                    intent.putExtra("com.porter.providerPhone", patientsProviderInfo[1]);
                    intent.putExtra("com.porter.providerEmail", patientsProviderInfo[2]);
                    intent.putExtra("com.porter.receivedSurveysJSON", receivedSurveys);
                } else if (userType == 1) {
                    intent.putExtra("com.porter.topSurveys", topSurveys);
                    intent.putExtra("com.porter.patientsList", patientsList);
                    Bundle b=new Bundle();
                    b.putStringArray("surveystrings", patientsSurveysStrings);
                    intent.putExtra("com.porter.patientsSurveyStrings", b);
                    startActivity(intent);
                    //  finish();
                } else {
                    mPasswordView.setError(getString(R.string.error_incorrect_password));
                    mPasswordView.requestFocus();
                }
            }
        }
        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}



