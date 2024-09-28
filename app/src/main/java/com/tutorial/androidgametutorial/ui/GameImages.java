package com.tutorial.androidgametutorial.ui;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.tutorial.androidgametutorial.R;
import com.tutorial.androidgametutorial.helpers.interfaces.BitmapMethods;
import com.tutorial.androidgametutorial.main.MainActivity;

public enum GameImages implements BitmapMethods {

    // Enum constants representing different game images with associated drawable resource IDs.

    MAINMENU_MENUBG(R.drawable.mainmenu_menubackground),
    DEATH_MENU_MENUBG(R.drawable.menu_youdied_background),
    MENU_BACKGROUND(R.drawable.background); // Add your GIF as a static resource

    // Private variable to hold the loaded image as a Bitmap.
    private final Bitmap image;

    /**
     * Constructor for GameImages enum.
     * Loads a Bitmap image from the given resource ID, with scaling disabled.
     *
     * @param resID The resource ID of the image (R.drawable.<image>).
     */
    GameImages(int resID) {
        // Disable automatic image scaling when loading resources
        options.inScaled = false;

        // Load the image as a Bitmap using the resource ID
        image = BitmapFactory.decodeResource(MainActivity.getGameContext().getResources(), resID, options);
    }

    /**
     * Returns the Bitmap image for the current enum constant.
     *
     * @return The Bitmap representing the image.
     */
    public Bitmap getImage() {
        return image;
    }
}

