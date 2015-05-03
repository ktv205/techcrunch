package com.example.krishnateja.buddiesnearby.Models;

/**
 * Created by krishnateja on 5/2/2015.
 */
public class AppConstants {

    public static class ServerVariables{
        public static final String SCHEME = "http";
        public static final String AUTHORITY = "209.208.62.85";
        public static final String PATH = "bigapple";
        public static final String FILE = "bigapple.php";
        public static final String METHOD = "GET";
        public static final String GETMTAVARIABLE = "MTA";
        public static final String GETCITIVARIABLE = "citi";
        public static final String LAT = "lat";
        public static final String LNG = "lng";
        public static final String OFFSET = "offset";
        public static final String PUBLIC = "public";
        public static final String INDEX = "index.php";
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
    public static abstract class Flags {
        public final static int SIGNUP_FLAG = 1991;
        public final static int LOGIN_FLAG = 1992;
        public final static int VERIFICATION_FLAG = 1993;
        public final static int ACTIVE_FLAG = 1994;
        public final static int BACK_FLAG = 1995;
        public final static int LOGIN_BACK = 1996;
        public final static int SIGNUP_SUCCESS = 1998;
        public final static int HELPER_COLOR_FLAG = 1999;
        public final static int VICTIM_COLOR_FLAG = 2000;
        public final static int USER_COLOR_FLAG = 2001;
        public final static int NOTIFICATION_FLAG=2002;

    }

    public static abstract class IntentExtras {
        public final static String signuptoverification = "signuptoverification";
        public final static String verificationtomain = "verificationtomain";
        public final static String verificationtoauthentication = "verificationtoauthentication";
        public final static String COORDINATES = "coordinates";
        public final static String USERID = "userid";
        public final static String LOCATIONS = "locations";
        public final static String NOCONNECTION = "no connections";
        public final static String CHANGE = "change password";
        public final static String HIGH_ACCURACY = "high_accuracy";
        public final static String BALANCED_POWER = "balanced_power";
        public final static String NEW_PASSWORD = "new password";
        public final static String INITIAL_LOCATIONS = "initial_locations";
        public final static String ActivityRecognitionService_EXTRA_MESSAGE = "ActivityRecognitionService.EXTRA_MESSAGE";
        public final static String ReceiveLocationService_EXTRA_MESSAGE = "ReceiveLocationService_EXTRA_MESSAGE";
        public final static String HELP_EXTRA="help_extra";
    }

}
