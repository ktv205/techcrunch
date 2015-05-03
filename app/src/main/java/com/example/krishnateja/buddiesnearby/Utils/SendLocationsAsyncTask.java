package com.example.krishnateja.buddiesnearby.Utils;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.krishnateja.buddiesnearby.Models.RequestParams;
import com.example.krishnateja.buddiesnearby.Models.User;

import java.util.ArrayList;

/**
 * Created by krishnateja on 5/3/2015.
 */
public class SendLocationsAsyncTask extends
        AsyncTask<RequestParams, Void, String> {
    Context mContext;
    private GetOtherUsersLocations mUserLocations;

    private static final String TAG = SendLocationsAsyncTask.class.getSimpleName();


    public interface GetOtherUsersLocations {
        public void getData(int code, ArrayList<User> arrayList);
    }

    public SendLocationsAsyncTask(Context context) {
        mContext = context;
        try {
            mUserLocations = (GetOtherUsersLocations) mContext;
        } catch (ClassCastException e) {
            throw new ClassCastException(mContext.toString()
                    + " must implement OnHeadlineSelectedListener");
        }

    }

    public SendLocationsAsyncTask() {

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(RequestParams... params) {
        return HttpManager.sendUserData(params[0]);
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if (result != null) {
            //Toast.makeText(mContext,result,Toast.LENGTH_SHORT).show();
            Log.d(TAG, result);
            int code = MyJSONParser.AuthenticationParser(result);
            mUserLocations.getData(code, MyJSONParser.parseLocation(result));


        }
    }

}

