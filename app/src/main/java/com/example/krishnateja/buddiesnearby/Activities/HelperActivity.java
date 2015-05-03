package com.example.krishnateja.buddiesnearby.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;

import com.example.krishnateja.buddiesnearby.Models.AppConstants;
import com.example.krishnateja.buddiesnearby.Models.RequestParams;
import com.example.krishnateja.buddiesnearby.Models.User;
import com.example.krishnateja.buddiesnearby.Utils.HttpManager;

import org.json.JSONArray;

import java.net.HttpURLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

/**
 * Created by krishnateja on 5/2/2015.
 */
public class HelperActivity extends AppCompatActivity {
    private Context mApplicationContext;
    private Context mActivityContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApplicationContext = getApplicationContext();
        mActivityContext = HelperActivity.this;
        String user_id = mApplicationContext.getSharedPreferences(AppConstants.AppSharedPref.USER_ID,
                Context.MODE_PRIVATE).toString();
        if(user_id == null || user_id.equals(""))
        {
            Intent intent = new Intent(this,AuthActivity.class);
            startActivity(intent);
        }
        else{
            String latitude = getIntent().getStringExtra(AppConstants.InAppConstants.LATITUDE);
            String longitude = getIntent().getStringExtra(AppConstants.InAppConstants.LONGITUDE);
            ArrayList<User> users = new ArrayList<>();
            RequestParams rp = new RequestParams();
            rp.setMethod("GET");
            rp.setParam("id", user_id);
            rp.setParam("lat",latitude);
            rp.setParam("lng",longitude);
            String result = HttpManager.sendUserData(rp);
            JSONArray jsUsers = new JSONArray(result);
            for()
            User user = new User();
            user.set_id("10153289056981115");
            user.add(userr);
            Intent intent = new Intent(this, ChatActivity.class);
            intent.putExtra(AppConstants.InAppConstants.FRIENDS, user);
        }

        //    sendToAuth();



    }

    public void sendToAuth() {
        Intent intent = new Intent(mApplicationContext, AuthActivity.class);
        startActivity(intent);
    }
}
