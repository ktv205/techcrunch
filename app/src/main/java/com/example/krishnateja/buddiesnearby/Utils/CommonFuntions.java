package com.example.krishnateja.buddiesnearby.Utils;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.example.krishnateja.buddiesnearby.Models.RequestParams;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by krishnateja on 5/2/2015.
 */
public class CommonFuntions {

    public static final String TAG=CommonFuntions.class.getSimpleName();

    public static String  getUserDetails(AccessToken accessToken,final Context context){
        final String[] jsonString = new String[1];
        GraphRequest request = GraphRequest.newMeRequest(accessToken,new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject jsonObject, GraphResponse graphResponse) {
                      jsonString[0] =jsonObject.toString();
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

    public static String getUserFriends(AccessToken accessToken,final Context context){
        final String[] jsonString = new String[1];
        GraphRequest request = GraphRequest.newMyFriendsRequest(
                accessToken,new GraphRequest.GraphJSONArrayCallback() {
                    @Override
                    public void onCompleted(JSONArray jsonArray, GraphResponse graphResponse) {
                        if(jsonArray!=null){
                            jsonString[0] =jsonArray.toString();
                            Log.d(TAG, jsonArray.toString());
                            RequestParams params=CreateSendRequestParams(jsonString[0]);
                            new SendDataAsyncTask().execute(params);


                        }else{
                            Log.d(TAG,"json array is null");
                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "friends");
        request.setParameters(parameters);
        request.executeAsync();
        return jsonString[0];


    }

    public static RequestParams CreateSendRequestParams(String jsonString){


        return null;
    }
}
