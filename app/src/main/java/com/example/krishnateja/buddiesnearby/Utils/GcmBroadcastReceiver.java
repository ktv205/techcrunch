package com.example.krishnateja.buddiesnearby.Utils;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import static android.support.v4.content.WakefulBroadcastReceiver.startWakefulService;

/**
 * Created by krishnateja on 5/3/2015.
 */
public class GcmBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG =GcmBroadcastReceiver.class.getSimpleName() ;
    //private static final String TAG="GcmBroadcastReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "omReceive");
        ComponentName comp = new ComponentName(context.getPackageName(),
                GcmIntentService.class.getName());
        startWakefulService(context, (intent.setComponent(comp)));
        setResultCode(Activity.RESULT_OK);

    }

}
