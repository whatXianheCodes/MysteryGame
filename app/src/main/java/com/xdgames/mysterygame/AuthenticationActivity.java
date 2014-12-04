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

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class AuthenticationActivity extends ActionBarActivity {
    private static final String TAG = "AuthenticationActivity";
    public final static String EXTRA_INVITATION = "com.xdgames.mysterygame.INVITATION";
    public final static String EXTRA_FNAME = "com.xdgames.mysterygame.FNAME";
    public final static String EXTRA_LNAME = "com.xdgames.mysterygame.LNAME";
    public final static String EXTRA_EMAIL = "com.xdgames.mystergygame.EMAIL";
    public final static String EXTRA_PASSWORD = "com.xdgames.mysterygame.PASSWORD";




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

    private boolean checkInvitation (String securityCodeValue) {
        //TODO: add security check to determine whether the code is valid
        final String INVITE_CODE_PATTERN = "^[A-Za-z0-9]{4}-[A-Za-z0-9]{4}-[A-Za-z0-9]{4}-[A-Za-z0-9]{4}$";
        return securityCodeValue.matches(INVITE_CODE_PATTERN);
    }

    private boolean checkName (String firstNameValue, String lastNameValue) {
        final String NAME_PATTERN = "[-A-Za-z]{1,}";
        return (firstNameValue.matches(NAME_PATTERN) && lastNameValue.matches(NAME_PATTERN));
    }

    private boolean checkEmail (String emailValue) {
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        return emailValue.matches(EMAIL_PATTERN);
    }

    private boolean checkPassword (String passwordValue) {
        return (passwordValue.length()!= 0);
    }

    private boolean checkConfirmPassword (String passwordValue, String passwordConfirmValue) {
        return (passwordValue.equals(passwordConfirmValue));
    }

    private boolean checkUsername (String usernameValue) {
        // TODO: check database if username isn't taken. Need database first
        return (usernameValue.length() != 0);
    }
    private void displayInvalidMessageToast (String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.show();
    }

    public void submitForm (View view) {
        EditText securityCodeView = (EditText)findViewById(R.id.invitation_code);
        String securityCodeValue = securityCodeView.getText().toString();
        EditText firstNameView = (EditText)findViewById(R.id.first_name);
        String firstNameValue = firstNameView.getText().toString();
        EditText lastNameView = (EditText) findViewById(R.id.last_name);
        String lastNameValue =  lastNameView.getText().toString();
        EditText emailView = (EditText)findViewById(R.id.email);
        String emailValue = emailView.getText().toString();
        EditText passwordView = (EditText)findViewById(R.id.password);
        String passwordValue = passwordView.getText().toString();
        EditText passwordConfirmView = (EditText)findViewById(R.id.confirm_password);
        String passwordConfirmValue = passwordView.getText().toString();
        EditText usernameView = (EditText)findViewById(R.id.username);
        String usernameValue = usernameView.getText().toString();
        if (!checkName(firstNameValue, lastNameValue)) {
            displayInvalidMessageToast(getResources().getString(R.string.invalid_name_message));
            return;
        }
        else if (!checkUsername(usernameValue)) {
            displayInvalidMessageToast(getResources().getString(R.string.invalid_username_message));
            return;
        }
        else if (!checkPassword(passwordValue)){
            displayInvalidMessageToast(getResources().getString(R.string.invalid_password_message));
            return;
        }
        else if (!checkConfirmPassword(passwordValue, passwordConfirmValue)){
            displayInvalidMessageToast(getResources().getString(R.string.invalid_password_confirm_message));
            return;
        }
        else if (!checkEmail(emailValue)) {
            displayInvalidMessageToast(getResources().getString(R.string.invalid_email_message));
            return;
        }
        else if (!checkInvitation(securityCodeValue)) {
            displayInvalidMessageToast(getResources().getString(R.string.invalid_invitation_message));
            return;
        }
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(EXTRA_INVITATION, securityCodeValue);
        intent.putExtra(EXTRA_FNAME, firstNameValue);
        intent.putExtra(EXTRA_LNAME, lastNameValue);
        intent.putExtra(EXTRA_EMAIL, emailValue);
        intent.putExtra(EXTRA_PASSWORD, passwordValue);
        startActivity(intent);
    }
}
