package com.xdgames.mysterygame;


import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.xdgames.mysterygame.Models.Account;
import com.xdgames.mysterygame.Utils.RegistrationRequest;

public class AuthenticationActivity extends ActionBarActivity {
    private static final String TAG = "AuthenticationActivity";
    public final static String EXTRA_INVITATION = "com.xdgames.mysterygame.INVITATION";
    public final static String EXTRA_FNAME = "com.xdgames.mysterygame.FNAME";
    public final static String EXTRA_LNAME = "com.xdgames.mysterygame.LNAME";
    public final static String EXTRA_EMAIL = "com.xdgames.mystergygame.EMAIL";
    public final static String EXTRA_PASSWORD = "com.xdgames.mysterygame.PASSWORD";
    public final static String EXTRA_USERNAME = "com.xdgames.mysterygame.USERNAME";

    private Account account;

    private void setDefaultValue () {
        Intent intent = getIntent();
        String usernameEmailValue = intent.getStringExtra(LoginActivity.EXTRA_USERNAME_EMAIL);
        if (!usernameEmailValue.isEmpty()){
            if (Account.checkEmail(usernameEmailValue)) {
                EditText emailView = (EditText) findViewById(R.id.registration_email);
                emailView.setText(usernameEmailValue);
            }
            else {
                EditText usernameView = (EditText) findViewById(R.id.registration_username);
                usernameView.setText(usernameEmailValue);
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);
        setDefaultValue();
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

    public void displayToastMessage (String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.show();
    }

    private boolean checkValidForm () {
        String passwordConfirmValue = ((EditText)findViewById(R.id.registration_confirm_password)).getText().toString();
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
        String invitationCodeValue = ((EditText)findViewById(R.id.registration_invitation_code)).getText().toString();
        String firstNameValue = ((EditText)findViewById(R.id.registration_first_name)).getText().toString();
        String lastNameValue =  ((EditText) findViewById(R.id.registration_last_name)).getText().toString();
        String emailValue = ((EditText)findViewById(R.id.registration_email)).getText().toString();
        String passwordValue = ((EditText)findViewById(R.id.registration_password)).getText().toString();
        String usernameValue = ((EditText)findViewById(R.id.registration_username)).getText().toString();

        account = new Account (firstNameValue, lastNameValue, usernameValue,
                passwordValue, emailValue, invitationCodeValue);
        if (!checkValidForm()) {
            return;
        }
        RegistrationRequest registrationRequest = new RegistrationRequest(this);
        registrationRequest.execute(account);
    }
}
