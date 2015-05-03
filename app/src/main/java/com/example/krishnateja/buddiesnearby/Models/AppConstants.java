package com.example.krishnateja.buddiesnearby.Models;

/**
 * Created by krishnateja on 5/2/2015.
 */
public class AppConstants {

    public static class ServerVariables{
        public static final String SCHEME = "http";
        public static final String AUTHORITY = "52.4.108.84";
        public static final String PATH = "bigapple";
        public static final String FILE = "bigapple.php";
        public static final String METHOD = "GET";
        public static final String GETMTAVARIABLE = "MTA";
        public static final String GETCITIVARIABLE = "citi";
        public static final String LAT = "lat";
        public static final String LNG = "lng";
        public static final String OFFSET = "offset";
        public static final String PUBLIC = "";
        public static final String INDEX = "";
    }
    public static class InAppConstants{
        public static final String FACEBOOK_TAG="facebook";
        public static final String FRIENDS = "friends";
        public static final String LATITUDE = "latitude";
        public static final String LONGITUDE = "longitude";

    }
    public static class AppSharedPref{
        public static final String NAME="auth";
        public static final String USER_ID = "fb_user_id";
        public static final String USER_NAME="fb_user_name";
        public static final String USER_PIC="fb_user_pic";
        public static final String USER_EMAIL="fb_user_email";
    }
    public static class ActivityRecognitionConstants{
        public final static String ENABLED = "enabled";
        public final static String WALKING = "walking";
        public final static String STILL = "still";
        public final static String VEHICLE = "onvehicle";
        public final static String activityType = "activityType";
        public final static String TYPE = "type";
    }
    public static class LeftDrawerConstants{
        public final static String SETTINGS="Settings";
        public final static String LOGOUT="Logout";
        public final static String FEEDBACK="Feedback";
    }
}
