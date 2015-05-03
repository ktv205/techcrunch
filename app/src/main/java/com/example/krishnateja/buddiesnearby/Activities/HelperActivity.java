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
import com.example.krishnateja.buddiesnearby.Models.User;

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
        ArrayList<User> user = new ArrayList<>();
        if(1==2) {
            User userr = new User();
            userr.set_id("10153289056981115");
            user.add(userr);
            Intent intent = new Intent(this, ChatActivity.class);
            intent.putExtra(AppConstants.InAppConstants.FRIENDS, user);
        }else{
            sendToAuth();
        }


    }

    public void sendToAuth() {
        Intent intent = new Intent(mApplicationContext, AuthActivity.class);
        startActivity(intent);
    }
}
