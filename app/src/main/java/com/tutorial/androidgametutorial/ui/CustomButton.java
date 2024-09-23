package com.tutorial.androidgametutorial.ui;

import android.graphics.RectF;

public class CustomButton {

    // RectF object representing the hitbox (interactive area) of the button.
    private final RectF hitbox;

    // Boolean flag to determine if the button is currently pushed (pressed).
    private boolean pushed;

    // Identifier for the pointer (e.g., finger touch) interacting with the button. Defaults to -1 (no pointer interaction).
    private int pointerId = -1;

    /**
     * Constructor for CustomButton.
     * Initializes the button's hitbox (interactive area) using the provided x, y coordinates and dimensions (width and height).
     *
     * @param x      The x-coordinate of the button's top-left corner.
     * @param y      The y-coordinate of the button's top-left corner.
     * @param width  The width of the button.
     * @param height The height of the button.
     */
    public CustomButton(float x, float y, float width, float height) {
        hitbox = new RectF(x, y, x + width, y + height); // Create the hitbox with specified dimensions
    }

    /**
     * Returns the hitbox of the button.
     *
     * @return RectF object representing the hitbox area of the button.
     */
    public RectF getHitbox() {
        return hitbox;
    }

    /**
     * Checks if the button is pushed and if the interaction is from the given pointer ID.
     *
     * @param pointerId The ID of the pointer (e.g., touch) interacting with the button.
     * @return True if the button is pushed by the given pointer, false otherwise.
     */
    public boolean isPushed(int pointerId) {
        if (this.pointerId != pointerId)
            return false;
        return pushed;
    }

    /**
     * Checks if the button is currently pushed (without considering pointer ID).
     *
     * @return True if the button is pushed, false otherwise.
     */
    public boolean isPushed() {
        return pushed;
    }

    /**
     * Releases the button's push state if the pointer ID matches the current pointer ID.
     *
     * @param pointerId The ID of the pointer (e.g., touch) that is releasing the button.
     */
    public void unPush(int pointerId) {
        if (this.pointerId != pointerId)
            return;
        this.pointerId = -1; // Reset pointer ID
        this.pushed = false; // Set pushed state to false (button is no longer pressed)
    }

    /**
     * Sets the button's pushed state to true and records the pointer ID that is interacting with the button.
     * If the button is already pushed, this method will not update the state.
     *
     * @param pushed    The state to set the button (true if pushed).
     * @param pointerId The ID of the pointer that is interacting with the button.
     */
    public void setPushed(boolean pushed, int pointerId) {
        if (this.pushed)
            return; // Ignore if the button is already pushed
        this.pushed = pushed;
        this.pointerId = pointerId; // Set the pointer ID of the interaction
    }

    /**
     * Sets the button's pushed state without considering a pointer ID.
     *
     * @param pushed The state to set the button (true if pushed, false if not).
     */
    public void setPushed(boolean pushed) {
        this.pushed = pushed;
    }

    /**
     * Retrieves the current pointer ID that is interacting with the button.
     *
     * @return The pointer ID (or -1 if no interaction is occurring).
     */
    public int getPointerId() {
        return pointerId;
    }
}

