package com.xdgames.mysterygame;


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

    public void displayToastMessage (String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.show();
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
        if (!checkValidForm()) {
            return;
        }
        RegistrationRequest registrationRequest = new RegistrationRequest(this);
        registrationRequest.execute(account);
    }
}
