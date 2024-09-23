package com.tutorial.androidgametutorial.helpers;

/**
 * A final class that holds constant values used throughout the game.
 * This class cannot be instantiated, and all members are static.
 */
public final class GameConstants {

    /**
     * A final class that defines constants for character facing directions.
     */
    public static final class Face_Dir {
        public static final int DOWN = 0;    // Direction constant for facing down
        public static final int UP = 1;      // Direction constant for facing up
        public static final int LEFT = 2;    // Direction constant for facing left
        public static final int RIGHT = 3;   // Direction constant for facing right
    }

    /**
     * A final class that defines constants related to sprite dimensions and scaling.
     */
    public static final class Sprite {
        public static final int DEFAULT_SIZE = 16;                       // Default size of the sprite
        public static final int SCALE_MULTIPLIER = 6;                   // Multiplier for scaling the sprite size
        public static final int SIZE = DEFAULT_SIZE * SCALE_MULTIPLIER; // Actual size of the sprite after scaling
        public static final int HITBOX_SIZE = 12 * SCALE_MULTIPLIER;    // Size of the hitbox for collision detection
        public static final int X_DRAW_OFFSET = 2 * SCALE_MULTIPLIER;   // X-axis offset for drawing the sprite
        public static final int Y_DRAW_OFFSET = 4 * SCALE_MULTIPLIER;   // Y-axis offset for drawing the sprite
    }

    /**
     * A final class that defines constants for animation settings.
     */
    public static final class Animation {
        public static final int SPEED = 10; // Speed of the animation frames
        public static final int AMOUNT = 4;  // Number of animation frames
    }
}
