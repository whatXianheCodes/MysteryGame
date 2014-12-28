package com.xdgames.mysterygame;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;


public class LoginActivity extends ActionBarActivity {

    private static final String TAG = "LoginActivity";
    public final static String EXTRA_USERNAME_EMAIL = "com.xdgames.mysterygame.login_username_email";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
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

    public void redirectRegistration (View view) {
        EditText userNameView = (EditText)findViewById(R.id.login_username_email);
        String userNameValue = userNameView.getText().toString();
        Intent intent = new Intent(this, AuthenticationActivity.class);
        intent.putExtra(EXTRA_USERNAME_EMAIL, userNameValue);
        startActivity(intent);
    }
}
