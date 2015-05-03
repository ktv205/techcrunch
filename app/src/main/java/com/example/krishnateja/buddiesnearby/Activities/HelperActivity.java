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

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by krishnateja on 5/2/2015.
 */
public class HelperActivity extends AppCompatActivity {
    private Context mApplicationContext;
    private Context mActivityContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApplicationContext=getApplicationContext();
        mActivityContext=HelperActivity.this;
        sendToAuth();

    }

    public void sendToAuth(){
        Intent intent=new Intent(mApplicationContext,AuthActivity.class);
        startActivity(intent);
    }
}
