package com.tutorial.androidgametutorial.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.tutorial.androidgametutorial.R;
import com.tutorial.androidgametutorial.helpers.interfaces.BitmapMethods;
import com.tutorial.androidgametutorial.main.MainActivity;

/**
 * Enum to manage button images for the game.
 * Each button image is defined by a drawable resource and has a normal and pushed state.
 * It implements the BitmapMethods interface to provide bitmap handling functionalities.
 */
public enum ButtonImages implements BitmapMethods {

    // Enum constants representing different button images with associated drawable resource IDs, widths, and heights.

    MENU_START(R.drawable.mainmenu_button_start, 300, 140),
    MENU_CONTINUE(R.drawable.final_mainmenu_button_continue, 300, 140),
    MENU_SETTINGS(R.drawable.mainmenu_button_settings, 300, 140),
    PLAYING_MENU(R.drawable.playing_button_menu, 140, 140),
    MENU_MENU(R.drawable.mainmenu_button_menu, 300, 140),
    MENU_REPLAY(R.drawable.mainmenu_button_replay, 300, 140),
    PLAYING_PAUSE(R.drawable.pause_button,140,140),
    PLAYING_RESUME(R.drawable.playing_button_resume,300,140),
    SETTING_BACK(R.drawable.settings_button_back,300,140),
    VICTORY_NEXT(R.drawable.victory_button_next,300,140);

    // Private instance variables to hold width, height, and bitmap representations of the button states.
    private final int width;
    private final int height;
    private final Bitmap normal;
    private final Bitmap pushed;

    /**
     * Constructor for ButtonImages enum.
     * Initializes button images based on the given resource ID, width, and height.
     * Each button has two states: normal and pushed.
     *
     * @param resID  The resource ID of the button image (R.drawable.<image>).
     * @param width  The width of the button image.
     * @param height The height of the button image.
     */
    ButtonImages(int resID, int width, int height) {
        // Disable automatic image scaling when loading resources
        options.inScaled = false;

        this.width = width;
        this.height = height;

        // Load the button image as a Bitmap from the resource ID
        Bitmap buttonAtlas = BitmapFactory.decodeResource(MainActivity.getGameContext().getResources(), resID, options);

        // Create the "normal" state of the button by extracting a part of the buttonAtlas
        normal = Bitmap.createBitmap(buttonAtlas, 0, 0, width, height);

        // Create the "pushed" state of the button by extracting another part of the buttonAtlas
        pushed = Bitmap.createBitmap(buttonAtlas, width, 0, width, height);
    }

    /**
     * Gets the width of the button image.
     *
     * @return The width of the button image.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Gets the height of the button image.
     *
     * @return The height of the button image.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Returns the appropriate button image based on the state of the button.
     *
     * @param isBtnPushed A boolean flag indicating if the button is in a "pushed" state.
     *                    If true, returns the pushed image, otherwise returns the normal image.
     * @return The Bitmap representing the button in its current state (either normal or pushed).
     */
    public Bitmap getBtnImg(boolean isBtnPushed) {
        return isBtnPushed ? pushed : normal;
    }
}
