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


public class LoginActivity extends ActionBarActivity {

    private static final String TAG = "LoginActivity";
    public final static String EXTRA_USERNAME_EMAIL = "com.xdgames.mysterygame.login_username_email";
    private final String PREFERENCE_CREDIENTIAL = "com.xdgames.mysterygame.credential";
    private final String PASSWORD_VALUE_CREDENTIAL = "com.xdgame.mysterygame.password";

    private String userNameValue;
    private String passwordValue;
    SharedPreferences setting;

    private void setDefaultValue () {
        userNameValue = setting.getString(EXTRA_USERNAME_EMAIL, "");
        passwordValue = setting.getString(PASSWORD_VALUE_CREDENTIAL, "");
        userNameValue = setting.getString(EXTRA_USERNAME_EMAIL, "");
        passwordValue = setting.getString(PASSWORD_VALUE_CREDENTIAL, "");
        if (!userNameValue.isEmpty()) {
            EditText userNameView = (EditText) findViewById(R.id.login_username_email);
            userNameView.setText(userNameValue);
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
        if(rememberMeValue){
            SharedPreferences.Editor editor = setting.edit();
            editor.putString(EXTRA_USERNAME_EMAIL, userNameValue);
            editor.putString(PASSWORD_VALUE_CREDENTIAL, passwordValue);
            editor.apply();
        }
        else {
            SharedPreferences.Editor editor = setting.edit();
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
        if (userNameValue.isEmpty()) {
            userNameValue = ((EditText)findViewById(R.id.login_username_email)).getText().toString();

        }
        if(passwordValue.isEmpty()) {
            passwordValue = ((EditText)findViewById(R.id.login_password)).getText().toString();
        }
        handleRememberme();
    }
}
