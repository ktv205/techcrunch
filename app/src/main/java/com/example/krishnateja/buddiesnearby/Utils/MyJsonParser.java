package com.example.krishnateja.buddiesnearby.Utils;

import android.util.Log;

import com.example.krishnateja.buddiesnearby.Models.AppConstants;
import com.example.krishnateja.buddiesnearby.Models.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by krishnateja on 5/3/2015.
 */
public class MyJSONParser {
    private static final String LAT = "latitude";
    private static final String LNG = "longitude";
    private static final String EMAIL = "userid";
    private static final String CODE = "code";
    private static final String DATA = "data";
    private static final String TAG = MyJSONParser.class.getSimpleName();
    private static final String USER = "user";
    private static final String VICTIM = "victim";
    private static final String HELPER = "helper";
    private static final String HELPERS = "helpers";
    private static final String ROLE = "role";
    private static final String NAME="username";

    public static ArrayList<User> parseLocation(
            String jsonString) {
        ArrayList<User> locationArrayList = new ArrayList<User>();
        int code = 0;
        JSONObject mainObject;
        try {
            mainObject = new JSONObject(jsonString);
            code = mainObject.getInt(CODE);
            Log.d(TAG, "code->" + code);

            if (code == 0 || code ==999) {

                // Do nothing
            } else {
                JSONObject dataObject = mainObject.getJSONObject(DATA);
                JSONArray usersArray = dataObject.getJSONArray(HELPERS);
                for (int i = 0; i < usersArray.length(); i++) {
                    JSONObject obj = usersArray.getJSONObject(i);
                    User myModel = new User();
                    myModel.set_latitude(obj.getDouble(LAT));
                    myModel.set_longitude(obj.getDouble(LNG));
                    myModel.set_id(obj.getString(EMAIL));
                    myModel.set_name(obj.getString(NAME));
                    String role = obj.getString(ROLE);
                    if (role.equals(VICTIM)) {
                        myModel.set_colour(AppConstants.Flags.VICTIM_COLOR_FLAG);
                    } else if (role.equals(USER)) {
                        myModel.set_colour(AppConstants.Flags.USER_COLOR_FLAG);
                    } else if (role.equals(HELPER)) {
                        myModel.set_colour(AppConstants.Flags.HELPER_COLOR_FLAG);
                    }
                    locationArrayList.add(myModel);
                }
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return locationArrayList;

    }

    public static int AuthenticationParser(String jsonString) {
        int code = 0;
        try {
            JSONObject mainObject = new JSONObject(jsonString);
            code = mainObject.getInt(CODE);

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return code;
    }
}
