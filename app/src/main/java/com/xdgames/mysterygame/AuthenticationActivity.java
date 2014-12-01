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
        final String INVITE_CODE = "....-....-....-....$";
        Pattern r = Pattern.compile(INVITE_CODE);

        Matcher m = r.matcher(securityCodeValue);
        return m.find();
    }

    private boolean checkName () {
        return true;
    }

    private boolean checkEmail () {
        EditText emailView = (EditText)findViewById(R.id.invitation_code);
        String emailValue = emailView.getText().toString();
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\\\+]+(\\\\.[_A-Za-z0-9-]+)*@\"\n" +
                "\t\t+ \"[A-Za-z0-9-]+(\\\\.[A-Za-z0-9]+)*(\\\\.[A-Za-z]{2,})$";
        Pattern r = Pattern.compile(EMAIL_PATTERN);

        Matcher m = r.matcher(emailValue);
        return m.find();
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

    }
}
