package com.example.krishnateja.buddiesnearby.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.krishnateja.buddiesnearby.Fragments.FacebookFragment;
import com.example.krishnateja.buddiesnearby.Models.AppConstants;
import com.example.krishnateja.buddiesnearby.R;
import com.facebook.FacebookSdk;

/**
 * Created by krishnateja on 5/2/2015.
 */
public class AuthActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        addFacebookFragment();


    }

    public void addFacebookFragment(){
        FacebookFragment facebookFragment=new FacebookFragment();
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.activity_auth_main_linear_layout,
                facebookFragment,AppConstants.InAppConstants.FACEBOOK_TAG);
        fragmentTransaction.commit();
    }
}
