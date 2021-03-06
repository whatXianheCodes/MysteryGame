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
 * Created by Xianhe on 12/25/2014.
 */
public class RegistrationRequest extends AsyncTask <Account, Void, String>{

    private static final String TAG = "RegistrationRequest";

    private JSONObject registrationJSON;
    private String apiEndpoint = "http://xdgames.xianheh.com/MysteryGame/register";
    private Activity activity;
    private ProgressDialog progressDialog;

    public RegistrationRequest(Activity activity) {
        this.activity = activity;
    }


    private void createRegistrationJson (Account account) {
        registrationJSON = new JSONObject();
        JSONObject userJSON = new JSONObject();
        try {
            userJSON.put("firstname", account.getFirstName());
            userJSON.put("lastname", account.getLastName());
            userJSON.put("username", account.getUsername());
            userJSON.put("password", account.getPassword());
            userJSON.put("email", account.getEmail());
            userJSON.put("invitation_code", account.getInvitationCode());
            registrationJSON.put("registration", userJSON);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private String handleRegistrationResponse (HttpResponse response) {
        if (response.getStatusLine().getStatusCode() == 200) {
            return "You have successfully created an account";
        }
        else {
            return "You have failed to create an account.";
        }
    }

    @Override
    protected String doInBackground(Account... params) {
         publishProgress();
         createRegistrationJson(params[0]);
         HttpPost httpPost = new HttpPost(apiEndpoint);
         httpPost.setHeader("Content-Type", "application/json");
         try {
             httpPost.setEntity(new StringEntity(registrationJSON.toString()));
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
