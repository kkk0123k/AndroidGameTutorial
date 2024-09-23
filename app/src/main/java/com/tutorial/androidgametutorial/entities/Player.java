package com.tutorial.androidgametutorial.entities;

import static com.tutorial.androidgametutorial.main.MainActivity.GAME_HEIGHT;
import static com.tutorial.androidgametutorial.main.MainActivity.GAME_WIDTH;

import android.graphics.PointF;

public class Player extends Character {

    /**
     * Constructs a Player object with the initial position at the center of the game screen.
     * The player character is initialized with a default health value.
     */
    public Player() {
        super(new PointF(GAME_WIDTH / 2, GAME_HEIGHT / 2), GameCharacters.PLAYER);
        setStartHealth(600);
    }

    /**
     * Updates the player's state, including animation and weapon hitbox.
     *
     * @param delta The time since the last update, used for animation timing.
     * @param movePlayer A flag indicating whether the player is currently moving.
     */
    public void update(double delta, boolean movePlayer) {
        if (movePlayer)
            updateAnimation();
        updateWepHitbox();
    }


}