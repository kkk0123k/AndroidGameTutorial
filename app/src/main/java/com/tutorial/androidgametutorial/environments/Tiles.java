package com.tutorial.androidgametutorial.environments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.tutorial.androidgametutorial.main.MainActivity;
import com.tutorial.androidgametutorial.R;
import com.tutorial.androidgametutorial.helpers.GameConstants;
import com.tutorial.androidgametutorial.helpers.interfaces.BitmapMethods;

/**
 * Enum representing different tile types in the game.
 */
public enum Tiles implements BitmapMethods {

    OUTSIDE(R.drawable.tileset_floor, 22, 26), // Represents outside tiles with a specified sprite sheet and dimensions
    INSIDE(R.drawable.floor_inside, 22, 22); // Represents inside tiles with a specified sprite sheet and dimensions

    private Bitmap[] sprites; // Array to hold the individual tile sprites

    /**
     * Constructs a Tiles enum with the given resource ID and dimensions.
     *
     * @param resID The resource ID of the tile sprite sheet.
     * @param tilesInWidth The number of tiles in the width of the sprite sheet.
     * @param tilesInHeight The number of tiles in the height of the sprite sheet.
     */
    Tiles(int resID, int tilesInWidth, int tilesInHeight) {
        options.inScaled = false; // Disable scaling options for the bitmap
        sprites = new Bitmap[tilesInHeight * tilesInWidth]; // Initialize the sprites array
        Bitmap spriteSheet = BitmapFactory.decodeResource(MainActivity.getGameContext().getResources(), resID, options);
        for (int j = 0; j < tilesInHeight; j++) // Loop through each row of tiles
            for (int i = 0; i < tilesInWidth; i++) { // Loop through each column of tiles
                int index = j * tilesInWidth + i; // Calculate the index for the sprites array
                sprites[index] = getScaledBitmap(Bitmap.createBitmap(spriteSheet, GameConstants.Sprite.DEFAULT_SIZE * i, GameConstants.Sprite.DEFAULT_SIZE * j, GameConstants.Sprite.DEFAULT_SIZE, GameConstants.Sprite.DEFAULT_SIZE)); // Extract and scale the individual tile sprite
            }

    }

    /**
     * Retrieves the sprite bitmap for the given sprite ID.
     *
     * @param id The ID of the sprite to retrieve.
     * @return The bitmap corresponding to the specified sprite ID.
     */
    public Bitmap getSprite(int id) {
        return sprites[id]; // Return the corresponding sprite bitmap
    }

}
