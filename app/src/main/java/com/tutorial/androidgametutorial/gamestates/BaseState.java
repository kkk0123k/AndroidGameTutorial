package com.tutorial.androidgametutorial.gamestates;

import android.view.MotionEvent;

import com.tutorial.androidgametutorial.main.Game;
import com.tutorial.androidgametutorial.ui.CustomButton;

/**
 * An abstract base class for game states, providing common functionality
 * for all specific game states (e.g., menu, gameplay, pause).
 */
public abstract class BaseState {
    protected Game game; // The Game instance associated with this state

    /**
     * Constructs a BaseState with the specified Game instance.
     *
     * @param game The Game instance to associate with this state.
     */
    public BaseState(Game game) {
        this.game = game; // Initializes the game instance
    }

    /**
     * Retrieves the Game instance associated with this state.
     *
     * @return The Game instance.
     */
    public Game getGame() {
        return game; // Returns the Game instance
    }

    /**
     * Checks if a MotionEvent is within the bounds of a CustomButton.
     *
     * @param e The MotionEvent to check.
     * @param b The CustomButton to check against.
     * @return True if the MotionEvent is within the button's hitbox, otherwise false.
     */
    public boolean isIn(MotionEvent e, CustomButton b) {
        return b.getHitbox().contains(e.getX(), e.getY()); // Returns whether the event is inside the button's hitbox
    }
}
