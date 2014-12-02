package com.xdgames.mysterygame;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class AuthenticationActivity extends ActionBarActivity {
    private static final String TAG = "AuthenticationActivity";
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

    private boolean checkInvitation () {
        //TODO: add security check to determine whether the code is valid
        EditText securityCodeView = (EditText)findViewById(R.id.invitation_code);
        String securityCodeValue = securityCodeView.getText().toString();
        final String INVITE_CODE_PATTERN = "^[A-Za-z0-9]{4}-[A-Za-z0-9]{4}-[A-Za-z0-9]{4}-[A-Za-z0-9]{4}$";
        return securityCodeValue.matches(INVITE_CODE_PATTERN);
    }

    private boolean checkName () {
        EditText firstNameView = (EditText)findViewById(R.id.first_name);
        String firstNameValue = firstNameView.getText().toString();
        EditText lastNameView = (EditText) findViewById(R.id.last_name);
        String lastNameValue =  lastNameView.getText().toString();
        final String NAME_PATTERN = "[-A-Za-z]{1,}";
        return (firstNameValue.matches(NAME_PATTERN) && lastNameValue.matches(NAME_PATTERN));
    }

    private boolean checkEmail () {
        EditText emailView = (EditText)findViewById(R.id.email);
        String emailValue = emailView.getText().toString();
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        return emailValue.matches(EMAIL_PATTERN);
    }

    private boolean checkPassword () {
        EditText passwordView = (EditText)findViewById(R.id.password);
        String passwordValue = passwordView.getText().toString();
        return (passwordValue.length()!= 0);
    }

    public void submitForm (View view) {
        if (!checkInvitation()) {
            return;
        }
        else if (!checkName()) {
            return;
        }
        else if (!checkEmail()) {
            return;
        }
        else if (!checkPassword()){
            return;
        }

    }
}
