package com.example.krishnateja.buddiesnearby.Utils;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.example.krishnateja.buddiesnearby.Activities.MainActivity;
import com.example.krishnateja.buddiesnearby.Models.AppConstants;
import com.example.krishnateja.buddiesnearby.R;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.layer.sdk.services.GcmBroadcastReceiver;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by krishnateja on 5/3/2015.
 */
public class GcmIntentService extends IntentService {
    private static final String TAG = "GcmIntentService";
    private static final int HElP_REQUESTED = 1;
    private static final int HELP_RECEIVED = 0;
    private Handler mHandler;

    // private static final int ID_HELP_RECEIVED_NOTIFICATION = 2;

    public GcmIntentService() {
        super(TAG);
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mHandler = new Handler();
        return super.onStartCommand(intent, flags, startId);
    }
    @Override
    protected void onHandleIntent(Intent intent) {

        Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        String messageType = gcm.getMessageType(intent);

        if (!extras.isEmpty()) {
            if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR
                    .equals(messageType)) {
                sendNotification("Send error: " + extras.toString());
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED
                    .equals(messageType)) {
                sendNotification("Deleted messages on server: "
                        + extras.toString());
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE
                    .equals(messageType)) {

                sendNotification(extras.getString("message"));
            }
        }
        GcmBroadcastReceiver.completeWakefulIntent(intent);

    }

    private void sendNotification(String msg) {
        //Log.d(TAG,msg);
        NotificationManager mNotificationManagerCompat = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        int notificationId = -1;
        Intent intent = new Intent(this, MainActivity.class);
        JSONArray array;
        int helpRequested = -1;
        String userId = null;
        try {
            array = new JSONArray(msg);
            JSONObject obj = array.getJSONObject(0);
            if (obj.has("helprequested")) {
                helpRequested = obj.getInt("helprequested");
            }
            notificationId = obj.getInt("id");
            userId = obj.getString("userid");
            intent.putExtra(
                    AppConstants.IntentExtras.COORDINATES,
                    new double[] { obj.getDouble("latitude"),
                            obj.getDouble("longitude") });
            intent.putExtra(AppConstants.IntentExtras.USERID, userId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (helpRequested == HElP_REQUESTED) {
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent notifyPendingIntent = PendingIntent.getActivity(this,
                    0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                    this).setSmallIcon(R.drawable.com_facebook_button_icon)
                    .setContentTitle("Alert")
                    .setContentText(userId.split("@")[0] + " needs help");
            mBuilder.setContentIntent(notifyPendingIntent);
            Uri alarmSound = RingtoneManager
                    .getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            mBuilder.setSound(alarmSound);
            mBuilder.setAutoCancel(true);
            // notification.sound = Uri.parse("android.resource://"
            // + context.getPackageName() + "/" + R.raw.siren);
            mNotificationManagerCompat = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            mNotificationManagerCompat.notify(notificationId, mBuilder.build());
        } else if (helpRequested == HELP_RECEIVED) {

            mHandler.post(new DisplayToast(this,userId.split("@")[0] + " received help"));
            mNotificationManagerCompat.cancel(notificationId);
        }
    }

    public static class DisplayToast implements Runnable {
        private final Context mContext;
        String mText;

        public DisplayToast(Context mContext, String text){
            this.mContext = mContext;
            mText = text;
        }

        public void run(){
            Toast.makeText(mContext, mText, Toast.LENGTH_SHORT).show();
        }
    }
}
