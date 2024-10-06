package com.tutorial.androidgametutorial.User;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log; // Import Log class for debugging
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.tutorial.androidgametutorial.R;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity"; // Tag for logging
    private static final String KEY_STATUS = "status";
    private static final String KEY_MESSAGE = "message";
    private static final String KEY_FULL_NAME = "full_name";
    private static final String KEY_PROGRESSION = "progression";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_EMPTY = "";
    private EditText etUsername;
    private EditText etPassword;
    private String username;
    private String password;
    private ProgressDialog pDialog;
    private String login_url = "http://192.168.1.3/member/login.php";
    private SessionHandler session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        session = new SessionHandler(getApplicationContext());

        if (session.isLoggedIn()) {
            Log.d(TAG, "User is already logged in, loading dashboard");
        }
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.etLoginUsername);
        etPassword = findViewById(R.id.etLoginPassword);

        Button register = findViewById(R.id.btnLoginRegister);
        Button login = findViewById(R.id.btnLogin);

        // Launch Registration screen when Register Button is clicked
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Register button clicked, launching Registration screen");
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);
                // finish(); Removed to allow returning to LoginActivity
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve the data entered in the edit texts
                username = etUsername.getText().toString().toLowerCase().trim();
                password = etPassword.getText().toString().trim();
                Log.d(TAG, "Attempting to log in with username: " + username);
                if (validateInputs()) {
                    login();
                }
            }
        });
    }

    /**
     * Display Progress bar while Logging in
     */
    private void displayLoader() {
        pDialog = new ProgressDialog(LoginActivity.this);
        pDialog.setMessage("Logging In.. Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
        Log.d(TAG, "Displaying progress dialog");
    }

    private void login() {
        displayLoader();
        JSONObject request = new JSONObject();
        try {
            // Populate the request parameters
            request.put(KEY_USERNAME, username);
            request.put(KEY_PASSWORD, password);
            Log.d(TAG, "Login request parameters: " + request.toString());
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(TAG, "JSON Exception while creating request: " + e.getMessage());
        }

        JsonObjectRequest jsArrayRequest = new JsonObjectRequest
                (Request.Method.POST, login_url, request, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        pDialog.dismiss();
                        Log.d(TAG, "Response received: " + response.toString());
                        try {
                            // Check if user got logged in successfully
                            if (response.getInt(KEY_STATUS) == 0) {
                                Log.d(TAG, "Login successful for user: " + username);
                                session.loginUser(username, response.getString(KEY_FULL_NAME), response.getString(KEY_PROGRESSION));
                                setResult(RESULT_OK); // Set result to indicate successful login
                                finish(); // Close LoginActivity
                            } else {
                                Log.d(TAG, "Login failed: " + response.getString(KEY_MESSAGE));
                                Toast.makeText(getApplicationContext(),
                                        response.getString(KEY_MESSAGE), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e(TAG, "JSON Exception while processing response: " + e.getMessage());
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pDialog.dismiss();
                        Log.e(TAG, "Volley Error: " + error.getMessage());
                        // Display error message whenever an error occurs
                        Toast.makeText(getApplicationContext(),
                                error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsArrayRequest);
    }

    /**
     * Validates inputs and shows error if any
     * @return
     */
    private boolean validateInputs() {
        if (KEY_EMPTY.equals(username)) {
            etUsername.setError("Username cannot be empty");
            etUsername.requestFocus();
            Log.d(TAG, "Validation failed: Username cannot be empty");
            return false;
        }
        if (KEY_EMPTY.equals(password)) {
            etPassword.setError("Password cannot be empty");
            etPassword.requestFocus();
            Log.d(TAG, "Validation failed: Password cannot be empty");
            return false;
        }
        Log.d(TAG, "Validation successful");
        return true;
    }
}
