package com.tutorial.androidgametutorial.entities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.tutorial.androidgametutorial.R;
import com.tutorial.androidgametutorial.helpers.interfaces.BitmapMethods;
import com.tutorial.androidgametutorial.main.MainActivity;

public enum Weapons implements BitmapMethods {

    BIG_SWORD(R.drawable.big_sword),
    SHADOW(R.drawable.shadow);

    final Bitmap weaponImg;

    /**
     * Enum for different types of weapons, each with an associated image resource.
     *
     * @param resId The resource ID of the weapon's image.
     */
    Weapons(int resId) {
        options.inScaled = false;
        weaponImg = getScaledBitmap(BitmapFactory.decodeResource(MainActivity.getGameContext().getResources(), resId, options));
    }

    /**
     * Retrieves the bitmap image of the weapon.
     *
     * @return The weapon's bitmap image.
     */
    public Bitmap getWeaponImg() {
        return weaponImg;
    }

    /**
     * Gets the width of the weapon image.
     *
     * @return The width of the weapon image in pixels.
     */
    public int getWidth() {
        return weaponImg.getWidth();
    }

    /**
     * Gets the height of the weapon image.
     *
     * @return The height of the weapon image in pixels.
     */
    public int getHeight() {
        return weaponImg.getHeight();
    }
}
