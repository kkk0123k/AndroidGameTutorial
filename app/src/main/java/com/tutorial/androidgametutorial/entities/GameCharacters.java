package com.tutorial.androidgametutorial.entities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;


import com.tutorial.androidgametutorial.main.MainActivity;
import com.tutorial.androidgametutorial.R;
import com.tutorial.androidgametutorial.helpers.GameConstants;
import com.tutorial.androidgametutorial.helpers.interfaces.BitmapMethods;

public enum GameCharacters implements BitmapMethods {

    PLAYER(R.drawable.player_spritesheet),
    SKELETON(R.drawable.skeleton_spritesheet);

    private final Bitmap spriteSheet;
    private final Bitmap[][] sprites = new Bitmap[7][4];

    /**
     * Constructs a GameCharacter with a specified resource ID for the sprite sheet.
     *
     * @param resID The resource ID of the sprite sheet image.
     */
    GameCharacters(int resID) {
        options.inScaled = false;
        spriteSheet = BitmapFactory.decodeResource(MainActivity.getGameContext().getResources(), resID, options);
        for (int j = 0; j < sprites.length; j++)
            for (int i = 0; i < sprites[j].length; i++)
                sprites[j][i] = getScaledBitmap(Bitmap.createBitmap(spriteSheet, GameConstants.Sprite.DEFAULT_SIZE * i, GameConstants.Sprite.DEFAULT_SIZE * j, GameConstants.Sprite.DEFAULT_SIZE, GameConstants.Sprite.DEFAULT_SIZE));
    }

    /**
     * Retrieves the sprite sheet of the game character.
     *
     * @return The Bitmap representing the character's sprite sheet.
     */
    public Bitmap getSpriteSheet() {
        return spriteSheet;
    }

    /**
     * Retrieves a specific sprite from the sprite sheet based on the row and column positions.
     *
     * @param yPos The row index of the sprite in the sheet.
     * @param xPos The column index of the sprite in the sheet.
     * @return The Bitmap representing the specified sprite.
     */
    public Bitmap getSprite(int yPos, int xPos) {
        return sprites[yPos][xPos];
    }

}