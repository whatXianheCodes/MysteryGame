package com.xdgames.mysterygame.Utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.xdgames.mysterygame.Models.Account;

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

/**
 * Created by Xianhe on 12/29/2014.
 */
public class LoginRequest extends AsyncTask <String, Void, String> {
    private static final String TAG = "LoginRequest";
    private Activity activity;
    private JSONObject loginJSON;
    private String apiEndpoint = "http://xdgames.xianheh.com/MysteryGame/login";
    private ProgressDialog progressDialog;


    public LoginRequest(Activity activity) {
        this.activity = activity;
    }

    private void createRegistrationJson (String... credentials) {
        loginJSON = new JSONObject();
        JSONObject credentialJSON = new JSONObject();
        try {
            credentialJSON.put("username", credentials[0]);
            credentialJSON.put("email", credentials[1]);
            credentialJSON.put("password", credentials[2]);
            loginJSON.put("login", credentialJSON);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private String handleRegistrationResponse (HttpResponse response) {
        if (response.getStatusLine().getStatusCode() == 200) {
            return "You have successfully logged in";
        }
        else {
            return "You have failed to login";
        }
    }

    @Override
    protected String doInBackground(String... params) {
        publishProgress();
        createRegistrationJson(params);
        HttpPost httpPost = new HttpPost(apiEndpoint);
        httpPost.setHeader("Content-Type", "application/json");
        try {
            httpPost.setEntity(new StringEntity(loginJSON.toString()));
            HttpParams httpParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParams, 10 * 1000);
            HttpConnectionParams.setSoTimeout(httpParams, 10*1000);
            DefaultHttpClient httpClient = new DefaultHttpClient(httpParams);
            HttpResponse response = httpClient.execute(httpPost);
            return handleRegistrationResponse(response);
        }
        catch (IOException e){
            e.printStackTrace();
            return "You have failed to create an account.";
        }
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onProgressUpdate(values);
        progressDialog = ProgressDialog.show(activity, "Please wait ...", "Sending request to server", true);
        progressDialog.setCancelable(false);

    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        progressDialog.dismiss();
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
        Toast toast = Toast.makeText(activity, s, Toast.LENGTH_SHORT);
        toast.show();
    }
}
