package com.gcme.wedechurchdatacollector.PrefManagers;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

import com.gcme.wedechurchdatacollector.Activity.LoginActivity;

/**
 * Created by Aman on 8/15/17.
 */

public class UserPrefManager {
    // LogCat tag
    private static String TAG = UserPrefManager.class.getSimpleName();



    // Shared Preferences
    SharedPreferences pref,Name,Email;

    Editor editor;
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // User name (make variable public to access from outside)
    public static final String KEY_NAME = "name";

    public static final String KEY_UNAME = "uname";

    // Email address (make variable public to access from outside)
    public static final String KEY_PASS = "password";

    // Email address (make variable public to access from outside)
    public static final String KEY_Email = "email";

    // Shared preferences file name
    private static final String PREF_NAME = "WedeChurchData";

    private static final String KEY_IS_LOGGEDIN = "isLoggedIn";
    private static  SharedPreferences prefs;

    public UserPrefManager(Context context) {
        this._context = context;

        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setLogin(boolean isLoggedIn,String name,String uname, String pass,String email) {

        editor.putBoolean(KEY_IS_LOGGEDIN, isLoggedIn);

        editor.putString(KEY_NAME, name);

        editor.putString(KEY_UNAME, uname);

        editor.putString(KEY_Email, email);

        // Storing email in pref
        editor.putString(KEY_PASS, pass);

        // commit changes
        editor.commit();

        Log.d(TAG, "User login session modified!");
    }


    public String getUserName() {

       return pref.getString(KEY_NAME, "");

    }
    public String getUserEmail() {

        return pref.getString(KEY_Email, "");
    }







    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Loing Activity
        Intent i = new Intent(_context, LoginActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);
    }

    public boolean isLoggedIn(){
        return pref.getBoolean(KEY_IS_LOGGEDIN, false);
    }
}
