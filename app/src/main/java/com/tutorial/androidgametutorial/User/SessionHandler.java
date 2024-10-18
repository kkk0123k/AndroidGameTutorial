package com.tutorial.androidgametutorial.User;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;

/**
 * Created by AndroidIgniter on 23 Mar 2019 020.
 */

public class SessionHandler {
    private static final String PREF_NAME = "UserSession";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_EXPIRES = "expires";
    private static final String KEY_FULL_NAME = "full_name";
    private static final String KEY_PROGRESSION = "progression";
    private static final String KEY_EMPTY = "";
    private final Context mContext;
    private final SharedPreferences.Editor mEditor;
    private final SharedPreferences mPreferences;

    public SessionHandler(Context mContext) {
        this.mContext = mContext;
        mPreferences = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        this.mEditor = mPreferences.edit();
    }

    /**
     * Logs in the user by saving user details and setting session
     *
     * @param username
     * @param fullName
     */
    public void loginUser(String username, String fullName, String progression) {
        mEditor.putString(KEY_USERNAME, username);
        mEditor.putString(KEY_FULL_NAME, fullName);
        mEditor.putString(KEY_PROGRESSION, progression);
        Date date = new Date();

        //Set user session for next 7 days
        long millis = date.getTime() + (7 * 24 * 60 * 60 * 1000);
        mEditor.putLong(KEY_EXPIRES, millis);
        mEditor.commit();
    }

    public void updateProgression(String username, String newProgression) {
        String url = "http://192.168.1.3/member/update.php"; // Replace with your server address

        HashMap<String, String> params = new HashMap<>();
        params.put("username", username);
        params.put("progression", newProgression);

        Log.d("SessionHandler", "start updateProgression"); // Add this line
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params),
                response -> {
                    try {
                        Log.d("SessionHandler", "Server Response: " + response.toString()); // Add this line

                        if (response.getBoolean("success")) {
                            Log.d("SessionHandler", "Progression updated successfully");
                            // Progression updated successfully on server
                            // You can add any UI updates or feedback here (optional)
                        } else {
                            String message = response.getString("message");
                            Log.d("SessionHandler", "Error updating progression: " + message);
                            // Handle error (e.g., show a message to the user)
                            // ... display error message to the user ...
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        // Handle JSON parsing error
                    }
                },
                error -> {
                    error.printStackTrace();
                    //Handle network error
                });

        // Debug print for the JSON request
        Log.d("SessionHandler", "JSON Request: " + new JSONObject(params));

        MySingleton.getInstance(mContext).addToRequestQueue(request);
    }

    /**
     * Checks whether user is logged in
     *
     * @return
     */
    public boolean isLoggedIn() {
        Date currentDate = new Date();

        long millis = mPreferences.getLong(KEY_EXPIRES, 0);

        /* If shared preferences does not have a value
         then user is not logged in
         */
        if (millis == 0) {
            return false;
        }
        Date expiryDate = new Date(millis);

        /* Check if session is expired by comparing
        current date and Session expiry date
        */
        return currentDate.before(expiryDate);
    }

    /**
     * Fetches and returns user details
     *
     * @return user details
     */
    public User getUserDetails() {
        //Check if user is logged in first
        if (!isLoggedIn()) {
            return null;
        }
        User user = new User();
        user.setUsername(mPreferences.getString(KEY_USERNAME, KEY_EMPTY));
        user.setProgression(mPreferences.getString(KEY_FULL_NAME, KEY_EMPTY));
        user.setSessionExpiryDate(new Date(mPreferences.getLong(KEY_EXPIRES, 0)));
        user.setProgression(mPreferences.getString(KEY_PROGRESSION, KEY_EMPTY));
        return user;
    }

    /**
     * Logs out user by clearing the session
     */
    public void logoutUser(){
        mEditor.clear();
        mEditor.commit();
    }

}
