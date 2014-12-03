package com.xdgames.mysterygame;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class AuthenticationActivity extends ActionBarActivity {
    private static final String TAG = "AuthenticationActivity";
    private int toast_duration = Toast.LENGTH_SHORT;
    private Context context = getApplicationContext();

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
            CharSequence text = getResources().getString(R.string.invalid_invitation_message);
            Toast toast = Toast.makeText(context, text, toast_duration);
            toast.show();
            return;
        }
        else if (!checkName()) {
            CharSequence text = getResources().getString(R.string.invalid_name_message);
            Toast toast = Toast.makeText(context, text, toast_duration);
            toast.show();
            return;
        }
        else if (!checkEmail()) {
            CharSequence text = getResources().getString(R.string.invalid_email_message);
            Toast toast = Toast.makeText(context, text, toast_duration);
            toast.show();
            return;
        }
        else if (!checkPassword()){
            CharSequence text = getResources().getString(R.string.invalid_password_message);
            Toast toast = Toast.makeText(context, text, toast_duration);
            toast.show();
            return;
        }
        //make it go to new activity
    }
}
