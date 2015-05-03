package com.example.krishnateja.buddiesnearby.Utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.example.krishnateja.buddiesnearby.Activities.MainActivity;
import com.example.krishnateja.buddiesnearby.Models.AppConstants;
import com.example.krishnateja.buddiesnearby.Models.RequestParams;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by krishnateja on 5/2/2015.
 */
public class CommonFunctions {

    public static final String TAG=CommonFunctions.class.getSimpleName();
    public static final String EXTRA_MESSAGE = "message";
    public static final String PROPERTY_REG_ID = "registration_id";
    private static final String PROPERTY_APP_VERSION = "appVersion";
    private static String mRegid;

    public static String  getUserDetails(AccessToken accessToken,final Context context){
        final String[] jsonString = new String[1];
        GraphRequest request = GraphRequest.newMeRequest(accessToken,new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject jsonObject, GraphResponse graphResponse) {
                      jsonString[0] =jsonObject.toString();
                storeUserId(jsonString[0],context);
                Log.d(TAG, jsonObject.toString());
                RequestParams params=CreateSendRequestParams(jsonString[0]);
                new SendDataAsyncTask().execute(params);

            }
        });
        String NAME = "name";
        String ID = "id";
        String PICTURE = "picture";
        String EMAIL = "email";
        String FIELDS = "fields";
        String REQUEST_FIELDS = TextUtils.join(",", new String[]{
                ID, NAME, PICTURE, EMAIL
        });

        Bundle parameters = new Bundle();
        parameters.putString(FIELDS, REQUEST_FIELDS);
        request.setParameters(parameters);
        request.executeAsync();

        return jsonString[0];

    }

    private static void storeUserId(String s, Context context) {
        try {
            JSONObject json = new JSONObject(s);
            String fb_user_id = null;
            String fb_user_name=null;
            String fb_user_pic=null;
            String fb_user_email=null;
            fb_user_id = json.get("id").toString();
            fb_user_name = json.get("name").toString();
            fb_user_pic = ((JSONObject)((JSONObject)json.get("picture"))
                    .get("data"))
                    .getString("url");
            fb_user_email = json.get("email").toString();
            SharedPreferences sp = context.getSharedPreferences(AppConstants.AppSharedPref.NAME,
                    context.MODE_PRIVATE);
            SharedPreferences.Editor editor=sp.edit();
            editor.putString(AppConstants.AppSharedPref.USER_ID, fb_user_id);
            editor.putString(AppConstants.AppSharedPref.USER_NAME, fb_user_name);
            editor.putString(AppConstants.AppSharedPref.USER_PIC, fb_user_pic);
            editor.putString(AppConstants.AppSharedPref.USER_EMAIL, fb_user_email);
            editor.commit();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public static String getUserFriends(AccessToken accessToken,final Context context){
        final String[] jsonString = new String[1];
        GraphRequest request = GraphRequest.newMyFriendsRequest(
                accessToken,new GraphRequest.GraphJSONArrayCallback() {
                    @Override
                    public void onCompleted(JSONArray jsonArray, GraphResponse graphResponse) {
                        if(jsonArray!=null){
                            jsonString[0] =jsonArray.toString();
                            Log.d(TAG,jsonString[0]);
                            storeFriendsUserId(jsonString[0]);
                            Log.d(TAG, jsonArray.toString());
                            RequestParams params=CreateSendRequestParams(jsonString[0]);
                            new SendDataAsyncTask().execute(params);


                        }else{
                            Log.d(TAG,"json array is null");
                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "me/friends");
        request.setParameters(parameters);
        request.executeAsync();
        return jsonString[0];


    }

    public static RequestParams CreateSendRequestParams(String jsonString){
        return null;
    }

    public static boolean isMyServiceRunning(Class<?> serviceClass,
                                             Context context) {
        ActivityManager manager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        boolean running = false;
        for (ActivityManager.RunningServiceInfo service : manager
                .getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                running = true;
            }
        }
        return running;
    }

    public static void storeFriendsUserId(String jsonArray){
        try {
            JSONArray array=new JSONArray(jsonArray);
            JSONObject object= (JSONObject) array.get(0);

            JSONObject friends=(JSONObject)object.getJSONObject("friends");
            JSONObject paging=friends.getJSONObject("paging");
            String url=paging.getString("next");
            RequestParams params=new RequestParams();
            params.setMethod("GET");
            params.setURI(url);
            params.setProtocol("https");
            Log.d(TAG, HttpManager.sendUserData(params));



        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static boolean checkIfGCMInfoIsSent(Context context) {
        if (checkPlayServices(context)) {
            mRegid = getRegistrationId(context);
            Log.d(TAG, "mRegid->" + mRegid);
            if (mRegid.isEmpty()) {
                // registerInBackground();
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    private static int getAppVersion(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException("Could not get package name: " + e);
        }
    }

    public static boolean checkPlayServices(Context context) {
        int resultCode = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(context);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                // GooglePlayServicesUtil.getErrorDialog(resultCode, mContext,
                // PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                // finish();
            }
            return false;
        } else {
            return true;
        }
    }

    private static String getRegistrationId(Context context) {
        final SharedPreferences prefs = getGCMPreferences(context);
        String registrationId = prefs.getString(PROPERTY_REG_ID, "");
        if (registrationId.isEmpty()) {
            return "";
        }
        int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION,
                Integer.MIN_VALUE);
        int currentVersion = getAppVersion(context);
        if (registeredVersion != currentVersion) {
            return "";
        }
        return registrationId;
    }

    private static SharedPreferences getGCMPreferences(Context context) {
        return getSharedPreferences(context, MainActivity.class.getSimpleName());
    }

    public static void storeRegistrationId(Context context, String regId) {
        final SharedPreferences prefs = getGCMPreferences(context);
        int appVersion = getAppVersion(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PROPERTY_REG_ID, regId);
        editor.putInt(PROPERTY_APP_VERSION, appVersion);
        editor.commit();
    }
    public static SharedPreferences getSharedPreferences(Context context,
                                                         String name) {
        return context.getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    public static RequestParams setParams(String[] paths, Context context) {
        RequestParams params = new RequestParams();
        Uri.Builder url = new Uri.Builder();
        url.scheme(AppConstants.ServerVariables.SCHEME)
                .authority(AppConstants.ServerVariables.AUTHORITY).build();
        url.appendPath(AppConstants.ServerVariables.PUBLIC);
        url.appendPath(AppConstants.ServerVariables.INDEX);
        for (String s : paths) {
            url.appendPath(s);
        }
        url.build();
        params.setURI(url.toString());
        params.setMethod("GET");
        return params;
    }




}
