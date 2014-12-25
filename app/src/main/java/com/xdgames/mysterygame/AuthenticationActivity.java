package com.xdgames.mysterygame;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpConnection;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class AuthenticationActivity extends ActionBarActivity {
    private static final String TAG = "AuthenticationActivity";
    public final static String EXTRA_INVITATION = "com.xdgames.mysterygame.INVITATION";
    public final static String EXTRA_FNAME = "com.xdgames.mysterygame.FNAME";
    public final static String EXTRA_LNAME = "com.xdgames.mysterygame.LNAME";
    public final static String EXTRA_EMAIL = "com.xdgames.mystergygame.EMAIL";
    public final static String EXTRA_PASSWORD = "com.xdgames.mysterygame.PASSWORD";
    public final static String EXTRA_USERNAME = "com.xdgames.mysterygame.USERNAME";

    private String apiEndpoint = "http://xdgames.xianheh.com/MysteryGame";
    private Account account;
    private JSONObject registrationJSON;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_authentication, menu);
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

    private void displayToastMessage (String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.show();
    }

    private void createRegistrationJson () {
        registrationJSON = new JSONObject();
        JSONObject userJSON = new JSONObject();
        try {
            userJSON.put("firstname", account.getFirstName());
            userJSON.put("lastname", account.getLastName());
            userJSON.put("username", account.getUsername());
            userJSON.put("password", account.getPassword());
            userJSON.put("email", account.getEmail());
            userJSON.put("invitation_code", account.getInvitationCode());
            registrationJSON.put("registration", userJSON);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void sendRegistration () {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpPost httpPost = new HttpPost(apiEndpoint);
                httpPost.setHeader("Content-Type", "application/json");
                try {
                    httpPost.setEntity(new StringEntity(registrationJSON.toString()));
                    HttpParams httpParams = new BasicHttpParams();
                    HttpConnectionParams.setConnectionTimeout(httpParams, 10* 1000);
                    HttpConnectionParams.setSoTimeout(httpParams, 10*1000);
                    DefaultHttpClient httpClient = new DefaultHttpClient(httpParams);
                    HttpResponse response = httpClient.execute(httpPost);
                }
                catch (IOException e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void handleRegistrationResponse (HttpResponse response) {
        if (response.getStatusLine().getStatusCode() == 200) {
            displayToastMessage("Login Successed");
        }
        else {
            displayToastMessage("Login Failed");
        }
    }

    private boolean checkValidForm () {
        EditText passwordConfirmView = (EditText)findViewById(R.id.confirm_password);
        String passwordConfirmValue = passwordConfirmView.getText().toString();
        if (!account.checkName()) {
            displayToastMessage(getResources().getString(R.string.invalid_name_message));
            return false;
        }
        else if (!account.checkUsername()) {
            displayToastMessage(getResources().getString(R.string.invalid_username_message));
            return false;
        }
        else if (!account.checkPassword()){
            displayToastMessage(getResources().getString(R.string.invalid_password_message));
            return false;
        }
        else if (!account.checkConfirmPassword(passwordConfirmValue)){
            displayToastMessage(getResources().getString(R.string.invalid_password_confirm_message));
            return false;
        }
        else if (!account.checkEmail()) {
            displayToastMessage(getResources().getString(R.string.invalid_email_message));
            return false;
        }
        else if (!account.checkInvitation()) {
            displayToastMessage(getResources().getString(R.string.invalid_invitation_message));
            return false;
        }
        else {
            return true;
        }
    }
    public void submitForm (View view) {
        EditText invitationCodeView = (EditText)findViewById(R.id.invitation_code);
        String invitationCodeValue = invitationCodeView.getText().toString();
        EditText firstNameView = (EditText)findViewById(R.id.first_name);
        String firstNameValue = firstNameView.getText().toString();
        EditText lastNameView = (EditText) findViewById(R.id.last_name);
        String lastNameValue =  lastNameView.getText().toString();
        EditText emailView = (EditText)findViewById(R.id.email);
        String emailValue = emailView.getText().toString();
        EditText passwordView = (EditText)findViewById(R.id.password);
        String passwordValue = passwordView.getText().toString();
        EditText usernameView = (EditText)findViewById(R.id.username);
        String usernameValue = usernameView.getText().toString();

        account = new Account (firstNameValue, lastNameValue, usernameValue,
                passwordValue, emailValue, invitationCodeValue);

//        if (!checkValidForm()) {
//            return;
//        }
        createRegistrationJson();
        sendRegistration();
    }
}
