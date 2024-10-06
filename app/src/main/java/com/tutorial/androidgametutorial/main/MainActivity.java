package com.tutorial.androidgametutorial.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

import com.tutorial.androidgametutorial.User.LoginActivity;

/**
 * Main activity for the game, responsible for setting up the game environment.
 */
public class MainActivity extends AppCompatActivity {

    private static Context gameContext; // Static context for the game
    public static int GAME_WIDTH, GAME_HEIGHT; // Dimensions for the game

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameContext = this; // Initialize the static context

        DisplayMetrics dm = new DisplayMetrics(); // To retrieve display metrics
        getWindowManager().getDefaultDisplay().getRealMetrics(dm); // Get real display metrics

        // Set the game dimensions based on the display metrics
        GAME_WIDTH = dm.widthPixels;
        GAME_HEIGHT = dm.heightPixels;

        // Set the system UI visibility for immersive full-screen mode
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                        View.SYSTEM_UI_FLAG_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        // If the device supports it, adjust for display cutouts (notches)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            getWindow().getAttributes().layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
        }

        // Start LoginActivity and wait for result
        Intent intent = new Intent(this, LoginActivity.class);
        startActivityForResult(intent, 1001);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1001 && resultCode == RESULT_OK) {
            // Login successful, proceed with setting up the game
            setContentView(new GamePanel(this));
        }
        // No need to handle other result codes here
    }

    /**
     * Static method to get the game context.
     *
     * @return The current game context.
     */
    public static Context getGameContext() {
        return gameContext; // Return the static game context
    }
}