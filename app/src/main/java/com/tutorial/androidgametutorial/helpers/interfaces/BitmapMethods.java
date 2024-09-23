package com.tutorial.androidgametutorial.helpers.interfaces;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.tutorial.androidgametutorial.helpers.GameConstants;

public interface BitmapMethods {

     BitmapFactory.Options options = new BitmapFactory.Options();

     /**
      * Scales the provided bitmap by a constant multiplier defined in GameConstants.
      * The width and height of the bitmap are both multiplied by the SCALE_MULTIPLIER
      * from GameConstants.Sprite, maintaining the aspect ratio of the original image.
      *
      * @param bitmap The original Bitmap to be scaled.
      * @return A new Bitmap that has been scaled by the specified multiplier.
      */

     default Bitmap getScaledBitmap(Bitmap bitmap){
          return Bitmap.createScaledBitmap(bitmap,bitmap.getWidth() * GameConstants.Sprite.SCALE_MULTIPLIER,bitmap.getHeight()*GameConstants.Sprite.SCALE_MULTIPLIER,false);
     }
}
