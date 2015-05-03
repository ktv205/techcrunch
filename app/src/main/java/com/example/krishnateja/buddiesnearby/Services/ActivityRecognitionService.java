package com.example.krishnateja.buddiesnearby.Services;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.example.krishnateja.buddiesnearby.Models.AppConstants;
import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.DetectedActivity;

/**
 * Created by krishnateja on 5/2/2015.
 */
public class ActivityRecognitionService extends IntentService {
    private static final String TAG = ActivityRecognitionService.class.getSimpleName();
    private Context mContext;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    public ActivityRecognitionService() {
        super(ActivityRecognitionService.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (mContext == null) {
            mContext = getApplicationContext();
        }
        String activity = null;
        if (intent != null) {
            ActivityRecognitionResult result = ActivityRecognitionResult
                    .extractResult(intent);
            if (result != null) {
                Log.d(TAG, "result is not null");

                DetectedActivity detectedActivity = result
                        .getMostProbableActivity();
                if (detectedActivity.getType() == DetectedActivity.IN_VEHICLE) {
                    activity = AppConstants.ActivityRecognitionConstants.VEHICLE;
                } else if (detectedActivity.getType() == DetectedActivity.ON_FOOT) {
                    activity = AppConstants.ActivityRecognitionConstants.WALKING;
                } else if (detectedActivity.getType() == DetectedActivity.RUNNING) {
                    activity = AppConstants.ActivityRecognitionConstants.WALKING;
                } else if (detectedActivity.getType() == DetectedActivity.STILL) {
                    activity = AppConstants.ActivityRecognitionConstants.STILL;
                } else if (detectedActivity.getType() == DetectedActivity.ON_BICYCLE) {
                    activity = AppConstants.ActivityRecognitionConstants.WALKING;
                } else if (detectedActivity.getType() == DetectedActivity.UNKNOWN) {

                } else if (detectedActivity.getType() == DetectedActivity.WALKING) {
                    activity = AppConstants.ActivityRecognitionConstants.WALKING;
                } else if (detectedActivity.getType() == DetectedActivity.TILTING) {

                }
            }


        }
    }

}
