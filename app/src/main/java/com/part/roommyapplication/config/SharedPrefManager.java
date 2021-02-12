package com.part.roommyapplication.config;

import android.content.Context;
import android.content.SharedPreferences;

import com.part.roommyapplication.Login.User;


public class SharedPrefManager {
        private static final String SHARED_PREF_NAME = "participant";
        private static final String URL="oururl";
        private static final String ID = "sessionId";
        private static final String USERNAME = "keyUsername";
        private static final String USER_EMAIL="email";
        private static final String USER_TYPE="userType";
        private static final String USER_GENDER="gender";
        private static final String USER_CLASS="class";
        private static final String USER_STREAM="stream";
        private static final String USER_DOB="dob";
        private static final String USER_PROFILEPIC="profilepicture";
        private static final String ACCESS_TOKEN="accessToken";
        private static final String REFRESH_TOKEN="refreshToken";
        private static final String TOKEN_EXPIRY="tokenExpiry";
        private static final String USER_ID="userid";


        private static SharedPrefManager mInstance;// Shared Preference Instance
        private static Context ctx;

        public SharedPrefManager(Context context) {
            ctx = context;
        }
         //This method creATES AN INSTANCE of shared preference
        public static synchronized SharedPrefManager getInstance(Context context) {
            if (mInstance == null) {
                mInstance = new SharedPrefManager(context);
            }
            return mInstance;
        }

        public void setUrl(String url){
            SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            String strUrl=url.toString();
            editor.putString(URL,strUrl);
            editor.apply();
        }

            // Setting userId for vital information use
          public void setUserId(int userId){
            SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(USER_ID,userId);
            editor.apply();
        }

    //Getting the url

        public int  getUserId(){
            SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
            return sharedPreferences.getInt(USER_ID,0);
        }

        //Getting the url

//        public String  getUrl(){
//            SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
//            return sharedPreferences.getString(URL,"http://192.168.1.120/");
//        }

        //this method will store the user data in shared preferences
        public void userLogin(User user) {
            SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(ID, user.getSession_id());
            editor.putString(USERNAME, user.getName());
            editor.putString(USER_GENDER,user.getGender());
            editor.putString(USER_CLASS,user.getStandard());
            editor.putString(USER_EMAIL,user.getEmail());
            editor.putString(USER_TYPE,user.getUserType());
            editor.putString(ACCESS_TOKEN,user.getAccessToken());
            editor.putString(TOKEN_EXPIRY,user.getTokenExpiry());
            editor.putString(REFRESH_TOKEN,user.getRefreshToken());
            editor.putString(USER_STREAM,user.getStream());
            editor.putString(USER_DOB,user.getDateOfBirth());
            editor.putString(USER_PROFILEPIC,user.getProfilePicture());
            editor.apply();
        }

        //this method will check whether user is already logged in or not
        public boolean isLoggedIn() {
            SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
            return sharedPreferences.getString(USERNAME, null) != null;
        }

        //this method will give the logged in user
        public User getUser() {
            SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
            return new User(
                    sharedPreferences.getString(USER_EMAIL, null),
                    sharedPreferences.getString(ID, null),
                    sharedPreferences.getString(USERNAME,null),
                    sharedPreferences.getString(USER_TYPE ,null),
                    sharedPreferences.getString(USER_STREAM,null),
                    sharedPreferences.getString(USER_GENDER,null),
                    sharedPreferences.getString(USER_DOB, null),
                    sharedPreferences.getString(USER_PROFILEPIC,null),
                    sharedPreferences.getString(ACCESS_TOKEN ,null),
                    sharedPreferences.getString(REFRESH_TOKEN,null),
                    sharedPreferences.getString(TOKEN_EXPIRY,null),
                    sharedPreferences.getString(USER_CLASS, null)
            );
        }

}
