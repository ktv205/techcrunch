package com.example.krishnateja.buddiesnearby.Utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.example.krishnateja.buddiesnearby.Models.AppConstants;
import com.example.krishnateja.buddiesnearby.Models.RequestParams;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by krishnateja on 5/2/2015.
 */
public class CommonFuntions {

    public static final String TAG=CommonFuntions.class.getSimpleName();
    public static String fb_user_id = null;

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
            fb_user_id = ((JSONObject)json.getJSONArray("data").get(0)).get("id").toString();
            SharedPreferences sp = context.getSharedPreferences(AppConstants.AppSharedPref.USER_ID,
                    context.MODE_PRIVATE);
            sp.edit().putString(AppConstants.AppSharedPref.USER_ID, fb_user_id);
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
            Log.d(TAG,HttpManager.sendUserData(params));



        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



}
