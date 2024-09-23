package com.tutorial.androidgametutorial.entities;

import static com.tutorial.androidgametutorial.helpers.GameConstants.Sprite.SCALE_MULTIPLIER;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PointF;

import com.tutorial.androidgametutorial.R;
import com.tutorial.androidgametutorial.helpers.interfaces.BitmapMethods;
import com.tutorial.androidgametutorial.main.MainActivity;

public enum Buildings implements BitmapMethods {

    // Enum constants representing different building types with their respective parameters.
    HOUSE_ONE(0, 0, 64, 48, 23, 42, 12, 36),
    HOUSE_TWO(64, 4, 62, 44, 23, 36, 11, 31),
    HOUSE_SIX(304, 0, 64, 48, 39, 40, 18, 35);

    Bitmap houseImg; // Bitmap image representing the house.
    PointF doorwayPoint; // Point representing the position of the doorway.
    int hitboxRoof, hitboxFloor, hitboxHeight, hitboxWidth; // Dimensions for hitboxes.

    /**
     * Constructor for the Buildings enum.
     * Initializes the bitmap image, doorway point, and hitbox dimensions based on the provided parameters.
     *
     * @param x         The x-coordinate in the image atlas for the building.
     * @param y         The y-coordinate in the image atlas for the building.
     * @param width     The width of the building sprite.
     * @param height    The height of the building sprite.
     * @param doorwayX  The x-coordinate of the doorway.
     * @param doorwayY  The y-coordinate of the doorway.
     * @param hitboxRoof The height of the roof hitbox.
     * @param hitboxFloor The height of the floor hitbox.
     */
    Buildings(int x, int y, int width, int height, int doorwayX, int doorwayY, int hitboxRoof, int hitboxFloor) {
        options.inScaled = false; // Disable scaling for the bitmap options.

        // Calculate hitbox dimensions based on scale multiplier.
        this.hitboxRoof = hitboxRoof * SCALE_MULTIPLIER;
        this.hitboxFloor = hitboxFloor * SCALE_MULTIPLIER;
        this.hitboxHeight = this.hitboxFloor - this.hitboxRoof;
        this.hitboxWidth = width * SCALE_MULTIPLIER;

        // Load the bitmap image from the resource atlas and scale it.
        Bitmap atlas = BitmapFactory.decodeResource(MainActivity.getGameContext().getResources(), R.drawable.buildings_atlas, options);
        houseImg = getScaledBitmap(Bitmap.createBitmap(atlas, x, y, width, height));

        // Set the doorway point based on the scaled coordinates.
        doorwayPoint = new PointF(doorwayX * SCALE_MULTIPLIER, doorwayY * SCALE_MULTIPLIER);
    }

    /**
     * Returns the point representing the doorway position.
     *
     * @return The doorway point as a PointF object.
     */
    public PointF getDoorwayPoint() {
        return doorwayPoint;
    }

    /**
     * Returns the height of the roof hitbox.
     *
     * @return The height of the roof as an integer.
     */
    public int getHitboxRoof() {
        return hitboxRoof;
    }

    /**
     * Returns the bitmap image of the house.
     *
     * @return The house image as a Bitmap object.
     */
    public Bitmap getHouseImg() {
        return houseImg;
    }
}
