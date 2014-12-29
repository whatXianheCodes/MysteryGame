package com.xdgames.mysterygame;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.xdgames.mysterygame.Models.Account;
import com.xdgames.mysterygame.Utils.LoginRequest;


public class LoginActivity extends ActionBarActivity {

    private static final String TAG = "LoginActivity";
    public final static String EXTRA_USERNAME_EMAIL = "com.xdgames.mysterygame.login_username_email";
    private final String PREFERENCE_CREDIENTIAL = "com.xdgames.mysterygame.credential";
    private final String PASSWORD_VALUE_CREDENTIAL = "com.xdgames.mysterygame.password";
    private final String REMEMBER_ME_CREDIENTIAL = "com.xdgames.mysterygame.remember_me";

    private String userNameEmailValue;
    private String passwordValue;
    SharedPreferences setting;

    private void setDefaultValue () {
        userNameEmailValue = setting.getString(EXTRA_USERNAME_EMAIL, "");
        passwordValue = setting.getString(PASSWORD_VALUE_CREDENTIAL, "");
        Boolean rememberMeValue = setting.getBoolean(REMEMBER_ME_CREDIENTIAL, false);
        CheckBox rememberMeView = (CheckBox) findViewById(R.id.login_remember_me_checkbox);
        rememberMeView.setChecked(rememberMeValue);

        if (!userNameEmailValue.isEmpty()) {
            EditText userNameView = (EditText) findViewById(R.id.login_username_email);
            userNameView.setText(userNameEmailValue);
        }
        if(!passwordValue.isEmpty()){
            EditText passwordView = (EditText) findViewById(R.id.login_password);
            passwordView.setText(passwordValue);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setting = getApplication().getSharedPreferences(PREFERENCE_CREDIENTIAL, Context.MODE_PRIVATE);
        setDefaultValue();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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

    private void handleRememberme() {
        boolean rememberMeValue = ((CheckBox) findViewById(R.id.login_remember_me_checkbox)).isChecked();
        SharedPreferences.Editor editor = setting.edit();
        editor.putBoolean(REMEMBER_ME_CREDIENTIAL, rememberMeValue);
        if(rememberMeValue){
            editor.putString(EXTRA_USERNAME_EMAIL, userNameEmailValue);
            editor.putString(PASSWORD_VALUE_CREDENTIAL, passwordValue);
            editor.apply();
        }
        else {
            editor.putString(EXTRA_USERNAME_EMAIL, "");
            editor.putString(PASSWORD_VALUE_CREDENTIAL, "");
            editor.apply();
        }
    }

    public void redirectRegistration (View view) {
        String userNameValue = ((EditText)findViewById(R.id.login_username_email)).getText().toString();
        Intent intent = new Intent(this, AuthenticationActivity.class);
        intent.putExtra(EXTRA_USERNAME_EMAIL, userNameValue);
        startActivity(intent);
    }


    public void loginRequest (View view) {
         userNameEmailValue = ((EditText)findViewById(R.id.login_username_email)).getText().toString();
         passwordValue = ((EditText)findViewById(R.id.login_password)).getText().toString();

        handleRememberme();
        if (userNameEmailValue.isEmpty() || passwordValue.isEmpty()) {
            return;
        }

        LoginRequest registrationRequest = new LoginRequest(this);
        if (Account.checkEmail(userNameEmailValue)) {
            // first value is username, second value is email, third value is password
            registrationRequest.execute("",userNameEmailValue, passwordValue);
        }
        else {
            registrationRequest.execute(userNameEmailValue,"", passwordValue);
        }
    }
}
