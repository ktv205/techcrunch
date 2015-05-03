package com.example.krishnateja.buddiesnearby.Activities;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.krishnateja.buddiesnearby.Models.AppConstants;
import com.example.krishnateja.buddiesnearby.Models.RequestParams;
import com.example.krishnateja.buddiesnearby.R;
import com.example.krishnateja.buddiesnearby.Utils.CommonFunctions;
import com.example.krishnateja.buddiesnearby.Utils.HttpManager;
import com.example.krishnateja.buddiesnearby.Utils.SendDataAsyncTask;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;


/**
 * Created by krishnateja on 5/2/2015.
 */
public class HelperActivity extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {

    // private static final String TAG = HelperActivity.class.getSimpleName();
    private Context mContext;
    public static final int SLEEP_AUTHENTICATION = 3000;
    private GoogleCloudMessaging mGcm;
    private String mRegid;
    // private static final String UPDATE_HOME = "uh";
    private static final String TAG = HelperActivity.class.getSimpleName();
    private String userEmail;
    private static final String UPDATE_GCM = "updategcm";
    private static final String UPDATE = "u";
    private GoogleApiClient mGoogleApiClient;
    private String SENDER_ID = "563675166055";

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getApplicationContext();
        overridePendingTransition(0, 0);
        setContentView(R.layout.activity_helper);
        if (getSharedPreferences(AppConstants.AppSharedPref.NAME, Context.MODE_PRIVATE).getString(AppConstants.AppSharedPref.USER_ID, "").isEmpty()) {
            startActivity(new Intent(this, AuthActivity.class));
    //            if(LoginManager.getInstance()!=null){
    //                LoginManager.getInstance().logOut();
    //            }
            finishAffinity();
        } else {
            if (CommonFunctions.checkIfGCMInfoIsSent(mContext)) {
                // nothing to do if already info is sent
                startGoogleApiClient();
            } else {
                registerInBackground();
                startGoogleApiClient();
            }
        }


    }

    @Override
    public void overridePendingTransition(int enterAnim, int exitAnim) {
        super.overridePendingTransition(enterAnim, exitAnim);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }

	/*
     *
	 * (non-Javadoc)
	 *
	 * @see
	 * com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks
	 * #onConnected(android.os.Bundle)
	 *
	 * GoogleApiClient.isConnect() is called it will give you three methods that
	 * returns the success or failure or suspended
	 */

    @Override
    public void onConnected(Bundle arg0) {
        Location location = LocationServices.FusedLocationApi
                .getLastLocation(mGoogleApiClient);
        if (location != null) {

            String[] paths = {"safety", "public", "index.php", "updatelocation", getSharedPreferences(AppConstants.AppSharedPref.NAME, Context.MODE_PRIVATE).getString(AppConstants.AppSharedPref.USER_ID, ""),
                    String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude()), "walking", "uh"};
            RequestParams params = CommonFunctions.setParams(paths, mContext);
            new SendDataAsyncTask().execute(params);
            startActivity(new Intent(this, MainActivity.class));


        }
    }

    @Override
    public void onConnectionSuspended(int arg0) {
        Log.d(TAG, "onConnectionSuspended");
    }

    @Override
    public void onConnectionFailed(ConnectionResult arg0) {
        Log.d(TAG, "onConnectionFailed");
    }

    // user defined methods



	/*
     * The method goToAuthentication will send the user to sign up page if he is
	 * not logged in pauseActivity() is to let the activity wait for a while so
	 * that the transaction wont take place fast
	 */

    public void goToAuthenticationActivity() {
        startActivity(new Intent(mContext, AuthActivity.class));
        finish();
    }



	/*
	 * this method is to create a GoogleApiClient and call connect on it
	 */

    public void startGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API).addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).build();

        mGoogleApiClient.connect();
    }

	/*
	 * just sending the user to mainactivity after grabbing his initial location
	 *
	 */


    // getting and Sending gcm to server

    public void registerInBackground() {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String msg = "";
                try {
                    if (mGcm == null) {
                        mGcm = GoogleCloudMessaging.getInstance(mContext);
                    }
                    mRegid = mGcm.register(SENDER_ID);
                    msg = "Device registered, registration ID=" + mRegid;
                    Log.d(TAG, msg);
                    sendRegistrationIdToBackend(mRegid);
                    CommonFunctions.storeRegistrationId(mContext, mRegid);
                } catch (IOException ex) {
                    msg = "Error :" + ex.getMessage();
                }
                return msg;
            }

        }.execute();
    }

    /*
     * This the method where we create the params that are required to build the
     * url to be called to send the regid to the server
     */
    private void sendRegistrationIdToBackend(String regid) {
        Log.d(TAG, "sendRegistrationToBackEnd");
        String[] paths = {"safety", "public", "index.php", UPDATE_GCM, getSharedPreferences(AppConstants.AppSharedPref.NAME, Context.MODE_PRIVATE).getString(AppConstants.AppSharedPref.USER_ID, ""), regid};
        RequestParams params = CommonFunctions.setParams(paths, mContext);
        HttpManager.sendUserData(params);

    }
}
