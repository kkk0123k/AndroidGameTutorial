package com.tutorial.androidgametutorial.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.tutorial.androidgametutorial.R;
import com.tutorial.androidgametutorial.helpers.interfaces.BitmapMethods;
import com.tutorial.androidgametutorial.main.MainActivity;

public enum HealthIcons implements BitmapMethods {

    // Enum constants representing different health icons, each with a specific x-position on the sprite atlas.

    HEART_FULL(0),
    HEART_3Q(1),
    HEART_HALF(2),
    HEART_1Q(3),
    HEART_EMPTY(4);

    // Bitmap to store the individual heart icon.
    private Bitmap icon;

    // The width and height for each heart icon, assuming square dimensions.
    private int widthHeight = 16;

    /**
     * Constructor for HealthIcons enum.
     * Loads the health icon from a sprite atlas, crops it based on the x-position,
     * and scales the cropped bitmap to the desired size.
     *
     * @param xPos The x-position of the icon in the sprite atlas (column position).
     */
    HealthIcons(int xPos) {
        // Disable automatic scaling when loading resources
        options.inScaled = false;

        // Load the health icons sprite atlas
        Bitmap atlas = BitmapFactory.decodeResource(MainActivity.getGameContext().getResources(), R.drawable.health_icons, options);

        // Crop the correct icon based on the xPos and scale it
        icon = getScaledBitmap(Bitmap.createBitmap(atlas, xPos * widthHeight, 0, widthHeight, widthHeight));
    }

    /**
     * Retrieves the health icon bitmap for the current enum constant.
     *
     * @return The scaled Bitmap representing the health icon.
     */
    public Bitmap getIcon() {
        return icon;
    }
}
