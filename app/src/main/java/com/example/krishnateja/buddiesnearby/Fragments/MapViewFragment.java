package com.example.krishnateja.buddiesnearby.Fragments;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.krishnateja.buddiesnearby.Models.RequestParams;
import com.example.krishnateja.buddiesnearby.R;
import com.example.krishnateja.buddiesnearby.Services.ActivityRecognitionService;
import com.example.krishnateja.buddiesnearby.Services.LocationUpdateService;
import com.example.krishnateja.buddiesnearby.Services.SingleLocationUpdateService;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.ActivityRecognition;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;
import java.util.Calendar;

import static com.example.krishnateja.buddiesnearby.Utils.CommonFunctions.isMyServiceRunning;

/**
 * Created by krishnateja on 5/1/2015.
 */
public class MapViewFragment extends Fragment implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        LocationListener, LocationSource {

    // TAG for debugging
    private static final String TAG = MapViewFragment.class.getSimpleName();

    // Application context to be used through out the activity
    private Context mContext;

    // GoogleMap object
    private GoogleMap mGoogleMap;

    // googleapiclient object
    private GoogleApiClient mGoogleApiClient;

    // boolean to check and set location reqeust to be high accuracy or low
    // accuracy
    private static boolean mIsHighAccuracy = false;

    // OnLocationChangeListener object
    private OnLocationChangedListener mOnLocationChangeListener;

    // Markers object
    private ArrayList<Marker> mMarkers;

    // people text view
    private TextView mPeopleTextView;

    // help button
    private Button mHelpButton;

    // paths for server urls
    private static final String ASK_HELP_PATH = "askhelp";
    private static final String HELPED_PATH = "helped";
    private static final String HELP_RECEIVED_PATH = "helpreceived";

    // help button texts and flags
    private static final String ASK_HELP = "Ask For Help";
    private static final String ASKED_HELP = "Asked For Help(click here after receiving help)";
    private static final String HELPING = "Helping a Friend(click here after helping)";

    private static final int ASK_HELP_FLAG = 0;
    private static final int ASKED_HELP_FLAG = 1;
    private static final int HELPING_FLAG = 2;
    private static int mHelpFlag = 0;

    private final static String ASK_HELP_TEXT = "notifying nearby people";
    private final static String HELPED_TEXT = "notifying others";

    // Animation object
    private AlphaAnimation mAnimation;

    // userEmail
    private String mUserEmail = "example@nyu.edu";

    // type of update location
    private static final String UPDATE_HOME = "uh";
    private static final String UPDATE = "u";

    // paths for helperlist,victimlist
    private static final String HELPER_LIST = "helperlist";
    private static final String TRACK_VICTIM = "trackvictim";
    // private static final String HOME = "home";

    // victim useremail
    private String mVictimUserEmail = "example@nyu.edu";

    // Key for savedInstance of flag
    private final static String KEY_STATE = "state_of_user";

    //Linear layout for no network
    private LinearLayout mNoNetworkLinearLayout;

    //field for constant NO_CONNECTION to check for response from the httpmanager
    private static final int NO_CONNECTION = 999;

    //check if Activity recognition is enabled
    private boolean mIsEnabled = false;

    //alarm manager to send location updates every 30 min no matter what
    private PendingIntent mPendingIntent;

    //boolean to check if he is still
    private boolean mIsStill = false;

    //boolean to check if the activity is in foreground
    private boolean mIsForeground = true;

    //searching
    private final static String SEARCHING = "searching";

    private View mView;

    private MapView mMapView;


    //

	/*
     * (non-Javadoc)
	 *
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (mContext == null) {
            mContext = getActivity();
        }
        if (isMyServiceRunning(LocationUpdateService.class, mContext)) {
            mContext.stopService(new Intent(mContext, LocationUpdateService.class));
        }


        // Markers
        mMarkers = new ArrayList<>();

        // animating button
        buttonAnimation();

        // initialize button and textview
        acessViews();

        // get Intent from the notification
        Intent intent = getActivity().getIntent();
        if (intent != null) {
            retriveIntentExtras(intent);
        }

        //get ActivityRecognition status from saved preferences
        mIsEnabled = getActivityRecognitionStatus();

        //register a alarm manager
        setUpStillLocationUpdateAlarmManager();

        mGoogleMap=mMapView.getMap();
        mGoogleMap.setMyLocationEnabled(true);
        mGoogleMap.setLocationSource(this);
        buildGoogleApiClient();
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_map, container, false);
        mMapView = (MapView) mView.findViewById(R.id.map_view);
        mMapView.onCreate(savedInstanceState);
        return mView;
    }






	/*
     * (non-Javadoc)
	 *
	 * @see
	 * com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener
	 * #onConnectionFailed(com.google.android.gms.common.ConnectionResult)
	 */

    // callbacks for the googleapiclient

    @Override
    public void onConnectionFailed(ConnectionResult arg0) {

    }

	/*
     * (non-Javadoc)
	 *
	 * @see
	 * com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks
	 * #onConnected(android.os.Bundle)
	 */

    @Override
    public void onConnected(Bundle arg0) {
        settingUpMapLocationSource();
        if (!mIsEnabled) {
            settingUpActivityRecognition();
        }

    }

	/*
     * (non-Javadoc)
	 *
	 * @see
	 * com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks
	 * #onConnectionSuspended(int)
	 */

    @Override
    public void onConnectionSuspended(int arg0) {

    }

	/*
     * (non-Javadoc)
	 *
	 * @see
	 * com.google.android.gms.maps.LocationSource#activate(com.google.android
	 * .gms.maps.LocationSource.OnLocationChangedListener)
	 */

    // call backs for location source interface

    @Override
    public void activate(OnLocationChangedListener arg0) {
        mOnLocationChangeListener = arg0;

    }

	/*
     * (non-Javadoc)
	 *
	 * @see com.google.android.gms.maps.LocationSource#deactivate()
	 */

    @Override
    public void deactivate() {
        mOnLocationChangeListener = null;
    }

	/*
     * (non-Javadoc)
	 *
	 * @see
	 * com.google.android.gms.location.LocationListener#onLocationChanged(android
	 * .location.Location)
	 */

    // call back for interface location listener

    @Override
    public void onLocationChanged(Location arg0) {
        if (mOnLocationChangeListener != null && arg0 != null) {
            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(arg0
                    .getLatitude(), arg0.getLongitude())));
            mOnLocationChangeListener.onLocationChanged(arg0);
            RequestParams helpParams = null, helpingParams = null;
            double lat = arg0.getLatitude();
            double lng = arg0.getLongitude();

        }
    }


    // building the googleapi
    public void buildGoogleApiClient() {
        GoogleApiClient.Builder builder = new GoogleApiClient.Builder(mContext)
                .addApi(LocationServices.API).addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this);
        if (!mIsEnabled) {
            builder.addApi(ActivityRecognition.API);
        }

        mGoogleApiClient = builder.build();
        mGoogleApiClient.connect();
    }

    // setting up location request
    public void settingUpMapLocationSource() {
        LocationRequest locationRequest = new LocationRequest();

        if (mIsHighAccuracy) {
            locationRequest.setInterval(5000);
            locationRequest.setFastestInterval(2000);
            locationRequest
                    .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        } else {
            locationRequest.setInterval(10000);
            locationRequest.setFastestInterval(5000);
            locationRequest
                    .setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, locationRequest, this);
    }

    // accessing views and button
    public void acessViews() {
        mPeopleTextView = (TextView)mView.findViewById(R.id.text_people);
        mPeopleTextView.setText("searching...");
        mHelpButton = (Button)mView.findViewById(R.id.button_help);
        mHelpButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mHelpFlag == ASK_HELP_FLAG) {
                    buttonClickStuff(true, false, true, ASKED_HELP_FLAG, ASKED_HELP, SEARCHING);
                } else if (mHelpFlag == ASKED_HELP_FLAG) {
                    buttonClickStuff(false, false, false, ASK_HELP_FLAG, ASK_HELP, SEARCHING);
                } else if (mHelpFlag == HELPING_FLAG) {
                    buttonClickStuff(false, false, false, ASK_HELP_FLAG, ASK_HELP, SEARCHING);
                }

            }
        });
    }

    /*
     * button animation method
     */
    public void buttonAnimation() {
        mAnimation = new AlphaAnimation(1, 0); // Change alpha from fully
        // visible
        // to invisible
        mAnimation.setDuration(500); // duration - half a second
        mAnimation.setInterpolator(new LinearInterpolator()); // do not alter
        // animation
        // rate
        mAnimation.setRepeatCount(Animation.INFINITE); // Repeat animation
        // infinitely
        mAnimation.setRepeatMode(Animation.REVERSE); // Reverse animation at the
        // end so the button
        // will
        // fade back in

    }

    /*
     * resetting the text on button
     */
    public void changeTextOfButton(String text) {
        mHelpButton.setText(text);
    }

    /*
     * method to get stuff from intents
     */
    public void retriveIntentExtras(Intent intent) {


    }

	/*
     * method to reset the accuracy of how precise we want the location update
	 */

    public void resetAccuracyOfLocation() {
        stopLocationUpdates();
        startLocationUpdates();

    }

    public void startLocationUpdates() {
        if (mGoogleMap != null) {
            this.activate(mOnLocationChangeListener);
            mGoogleMap.setMyLocationEnabled(true);
            settingUpMapLocationSource();
        }
    }

    public void stopLocationUpdates() {
        if (mGoogleMap != null) {
            mGoogleMap.setMyLocationEnabled(false);
            LocationServices.FusedLocationApi.removeLocationUpdates(
                    mGoogleApiClient, this);
            this.deactivate();
        }
    }

    /*
     * filling map with users
     */
    public void fillMap() {


    }

    public void settingTextOfButton(int flag) {
        if (flag == HELPING_FLAG) {
            mHelpButton.startAnimation(mAnimation);
            mHelpButton.setText(HELPING);
        } else if (flag == ASKED_HELP_FLAG) {
            mHelpButton.startAnimation(mAnimation);
            mHelpButton.setText(ASKED_HELP);
        } else if (flag == ASK_HELP_FLAG) {
            mHelpButton.setText(ASK_HELP);
        }
    }

    public void removeMarkers() {
        if (mMarkers.size() != 0) {
            for (Marker marker : mMarkers) {
                marker.remove();
            }
        }
    }

    public void setNoConnectionView() {

    }

    public void settingUpActivityRecognition() {
        if (!mIsEnabled) {
            Intent intent = new Intent(mContext, ActivityRecognitionService.class);
            PendingIntent callbackIntent = PendingIntent.getService(mContext, 0,
                    intent, PendingIntent.FLAG_UPDATE_CURRENT);
            PendingResult<Status> result = ActivityRecognition.ActivityRecognitionApi
                    .requestActivityUpdates(mGoogleApiClient, // your connected
                            // GoogleApiClient
                            300000, // how often you want callbacks
                            callbackIntent); // the PendingIntent which will
            // receive updated activities
            result.setResultCallback(new ResultCallback<Status>() {
                @Override
                public void onResult(Status status) {
                    if (status.isSuccess()) {
                        Log.d(TAG, "success in registering with activity recognition");
                    }
                }
            });
        }
    }

    public boolean getActivityRecognitionStatus() {
           return false;
    }

    private BroadcastReceiver mActivityBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "activity received");
            if (intent != null && intent.hasExtra(Intent.EXTRA_TEXT)) {


            }
        }
    };

    public void setUpStillLocationUpdateAlarmManager() {
        Calendar calendar = Calendar.getInstance();
        Intent intent = new Intent(mContext, SingleLocationUpdateService.class);
        mPendingIntent = PendingIntent.getService(mContext, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager)mContext.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 60 * 1000 * 30, mPendingIntent);
    }

    public void stopAlarmManager() {
        AlarmManager alarmManager = (AlarmManager)mContext. getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(mPendingIntent);
    }

    public void sendLocationUpdate(double lat, double lng, String activityType, String mode) {

    }

    public void buttonClickStuff(boolean animation, boolean markers, boolean accuracy, int helpFlag, String buttonText, String peopleText) {
        if (helpFlag == ASKED_HELP_FLAG) {

        } else if (helpFlag == ASK_HELP_FLAG && mHelpFlag == HELPING_FLAG) {

        } else if (helpFlag == ASK_HELP_FLAG && mHelpFlag == ASKED_HELP_FLAG) {

        } else if (helpFlag == HELPING_FLAG) {
            mHelpFlag = helpFlag;
        }

        changeTextOfButton(buttonText);
        if (mAnimation != null && animation) {
            mHelpButton.startAnimation(mAnimation);
        } else if (mAnimation != null && !animation) {
            mHelpButton.clearAnimation();
        }
        mPeopleTextView.setText(SEARCHING);
        if (markers) {
            removeMarkers();
        }
        if (accuracy) {
            mIsHighAccuracy = true;
        } else {
            mIsHighAccuracy = false;
        }
        resetAccuracyOfLocation();


    }


}
