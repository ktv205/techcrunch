package com.example.krishnateja.buddiesnearby.Utils;

import android.os.AsyncTask;
import android.util.Log;

import com.example.krishnateja.buddiesnearby.Models.RequestParams;

/**
 * Created by krishnateja on 5/2/2015.
 */
public class SendDataAsyncTask extends AsyncTask<RequestParams, Void, String> {
    private static final String TAG = SendDataAsyncTask.class.getSimpleName();

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(RequestParams... params) {
        Log.d(TAG, "doInBackGround");
        return HttpManager.sendUserData(params[0]);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}
