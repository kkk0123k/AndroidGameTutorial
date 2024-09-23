package com.tutorial.androidgametutorial.helpers.interfaces;

import android.graphics.Canvas;
import android.view.MotionEvent;

public interface GameStateInterface {
    /**
     * Updates the game state based on the time elapsed since the last update.
     *
     * @param delta The time in seconds since the last update, used for smooth animations
     *              and physics calculations.
     */
    void update(double delta);

    /**
     * Renders the current game state onto the provided Canvas.
     *
     * @param c The Canvas object onto which the game elements should be drawn.
     */
    void render(Canvas c);

    /**
     * Handles touch events from the user.
     *
     * @param event The MotionEvent object containing details about the touch event,
     *              such as action type (press, release, move) and coordinates.
     */
    void touchEvents(MotionEvent event);

}
