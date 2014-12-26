package com.xdgames.mysterygame.Utils;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.xdgames.mysterygame.Models.Account;
import com.xdgames.mysterygame.UI.ProgressDialogFragment;

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
    private String apiEndpoint = "http://xdgames.xianheh.com/MysteryGame";
    private Context context;

    public RegistrationRequest(Context context) {
        this.context = context;
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
            //displayToastMessage("Login Successed");
            return "You have successfully created an account";
        }
        else {
            //displayToastMessage("Login Failed");
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
        super.onProgressUpdate(values);
        Log.d(TAG, "testing...");
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("This is a message")
                .setTitle("Alert")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d(TAG, "Ok Pressed");
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Toast toast = Toast.makeText(context, s, Toast.LENGTH_SHORT);
        toast.show();
    }
}
