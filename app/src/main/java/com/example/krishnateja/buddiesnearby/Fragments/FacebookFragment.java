package com.example.krishnateja.buddiesnearby.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.krishnateja.buddiesnearby.Activities.MainActivity;
import com.example.krishnateja.buddiesnearby.R;
import com.example.krishnateja.buddiesnearby.Utils.CommonFuntions;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by krishnateja on 5/2/2015.
 */
public class FacebookFragment extends Fragment {
    private static final String TAG = "FacebookFragment";
    private CallbackManager mCallBackManager;
    private AccessToken mAccessToken;

    private View mView;
    private LoginButton mFacebookButton;

    private FacebookCallback<LoginResult> mCallback=new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            Log.d(TAG,"success to login");
            AccessToken accessToken=loginResult.getAccessToken();
            CommonFuntions.getUserDetails(accessToken,getActivity());
            CommonFuntions.getUserFriends(accessToken,getActivity());
            getActivity().startActivity(new Intent(getActivity(), MainActivity.class));
        }

        @Override
        public void onCancel() {

        }

        @Override
        public void onError(FacebookException e) {

        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getActivity().getApplicationContext());
        mCallBackManager=CallbackManager.Factory.create();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG," in onCreate of facebook fragment");

        mView = inflater.inflate(R.layout.fragment_facebook, container, false);

        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFacebookButton = (LoginButton) mView.findViewById(R.id.fragment_facebook_login_button);
        mFacebookButton.setReadPermissions("user_friends","email");
        mFacebookButton.setFragment(this);
        mFacebookButton.registerCallback(mCallBackManager,mCallback);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallBackManager.onActivityResult(requestCode, resultCode, data);
    }

}
