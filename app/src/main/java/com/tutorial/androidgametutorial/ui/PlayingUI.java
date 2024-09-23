package com.tutorial.androidgametutorial.ui;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.view.MotionEvent;

import com.tutorial.androidgametutorial.entities.Character;
import com.tutorial.androidgametutorial.entities.Player;
import com.tutorial.androidgametutorial.gamestates.Playing;
import com.tutorial.androidgametutorial.main.Game;

public class PlayingUI {

    // For UI element positions and properties
    private final PointF joystickCenterPos = new PointF(250, 800); // Center position of the joystick
    private final PointF attackBtnCenterPos = new PointF(1700, 800); // Center position of the attack button
    private final float radius = 150; // Radius for both joystick and attack button UI
    private final Paint circlePaint; // Paint object for drawing circles (joystick/attack buttons)

    // For handling multitouch
    private int joystickPointerId = -1; // Pointer ID for the joystick interaction
    private int attackBtnPointerId = -1; // Pointer ID for the attack button interaction
    private boolean touchDown; // Flag to track if joystick is being touched

    private CustomButton btnMenu; // Custom button for in-game menu

    private final Playing playing; // Reference to the Playing class, controlling the game state

    // Health icon positions
    private final int healthIconX = 150, healthIconY = 25; // X and Y coordinates for drawing health icons
//    private int maxPlayerHealth = 600;
//    private int currentPlayerHealth = 275;

    /**
     * Constructor for the PlayingUI class.
     * Initializes the UI elements, sets up button and joystick properties, and assigns game state control.
     *
     * @param playing The Playing class instance, controlling the game's main logic and player interaction.
     */
    public PlayingUI(Playing playing) {
        this.playing = playing;

        // Set up paint properties for drawing circles (joystick and attack button)
        circlePaint = new Paint();
        circlePaint.setColor(Color.RED); // Red outline
        circlePaint.setStrokeWidth(5); // Stroke width of the circle
        circlePaint.setStyle(Paint.Style.STROKE); // Draw only the stroke, not the fill

        // Initialize the menu button with the appropriate dimensions
        btnMenu = new CustomButton(5, 5, ButtonImages.PLAYING_MENU.getWidth(), ButtonImages.PLAYING_MENU.getHeight());
    }

    /**
     * Draws the entire game UI including joystick, attack button, menu button, and health icons.
     *
     * @param c The Canvas object to draw the UI elements onto.
     */
    public void draw(Canvas c) {
        drawUI(c); // Draws the joystick, attack button, and menu button
    }

    /**
     * Draws the UI elements like joystick, attack button, and health icons on the canvas.
     *
     * @param c The Canvas object to draw the UI elements.
     */
    private void drawUI(Canvas c) {
        // Draw the joystick and attack button as circles
        c.drawCircle(joystickCenterPos.x, joystickCenterPos.y, radius, circlePaint);
        c.drawCircle(attackBtnCenterPos.x, attackBtnCenterPos.y, radius, circlePaint);

        // Draw the menu button
        c.drawBitmap(
                ButtonImages.PLAYING_MENU.getBtnImg(btnMenu.isPushed(btnMenu.getPointerId())),
                btnMenu.getHitbox().left,
                btnMenu.getHitbox().top,
                null);

        // Draw the player's health icons
        drawHealth(c);
    }

    /**
     * Draws the player's health using heart icons.
     * Displays the appropriate heart icon (full, 3/4, half, 1/4, or empty) based on the player's current health.
     *
     * @param c The Canvas object to draw the health icons.
     */
    private void drawHealth(Canvas c) {
        Player player = playing.getPlayer(); // Get the player object
        // Iterate through the player's max health, divided into units of 100
        for (int i = 0; i < player.getMaxHealth() / 100; i++) {
            int x = healthIconX + 100 * i; // Set X position for each heart
            int heartValue = player.getCurrentHealth() - 100 * i; // Determine heart value based on current health

            // Draw the appropriate heart icon based on the player's health
            if (heartValue < 100) {
                if (heartValue <= 0)
                    c.drawBitmap(HealthIcons.HEART_EMPTY.getIcon(), x, healthIconY, null);
                else if (heartValue == 25)
                    c.drawBitmap(HealthIcons.HEART_1Q.getIcon(), x, healthIconY, null);
                else if (heartValue == 50)
                    c.drawBitmap(HealthIcons.HEART_HALF.getIcon(), x, healthIconY, null);
                else
                    c.drawBitmap(HealthIcons.HEART_3Q.getIcon(), x, healthIconY, null);
            } else
                c.drawBitmap(HealthIcons.HEART_FULL.getIcon(), x, healthIconY, null);
        }
    }

    /**
     * Checks if a touch event is inside the radius of a circle (joystick or attack button).
     *
     * @param eventPos The touch event position.
     * @param circle   The center position of the circle.
     * @return True if the touch is inside the radius, false otherwise.
     */
    private boolean isInsideRadius(PointF eventPos, PointF circle) {
        float a = Math.abs(eventPos.x - circle.x);
        float b = Math.abs(eventPos.y - circle.y);
        float c = (float) Math.hypot(a, b);

        return c <= radius; // Return true if the distance is within the radius
    }

    /**
     * Checks if a touch event is inside the attack button.
     *
     * @param eventPos The touch event position.
     * @return True if the touch is inside the attack button's radius.
     */
    private boolean checkInsideAttackBtn(PointF eventPos) {
        return isInsideRadius(eventPos, attackBtnCenterPos); // Checks if inside attack button
    }

    /**
     * Checks if a touch event is inside the joystick.
     * If the touch is inside, the pointer ID is set and the touchDown flag is set to true.
     *
     * @param eventPos  The touch event position.
     * @param pointerId The ID of the pointer (touch event).
     * @return True if the touch is inside the joystick's radius, false otherwise.
     */
    private boolean checkInsideJoyStick(PointF eventPos, int pointerId) {
        if (isInsideRadius(eventPos, joystickCenterPos)) {
            touchDown = true; // Set the flag to true if the joystick is touched
            joystickPointerId = pointerId; // Store the pointer ID
            return true;
        }
        return false;
    }

    /**
     * Handles multitouch events like joystick movement, button presses, and player attacks.
     *
     * @param event The MotionEvent containing all the touch information.
     */
    public void touchEvents(MotionEvent event) {
        final int action = event.getActionMasked(); // Get the type of action (DOWN, MOVE, UP)
        final int actionIndex = event.getActionIndex(); // Index of the current action
        final int pointerId = event.getPointerId(actionIndex); // Get pointer ID for the action

        final PointF eventPos = new PointF(event.getX(actionIndex), event.getY(actionIndex)); // Get the touch position

        // Switch based on the type of action (touch down, move, or up)
        switch (action) {
            case MotionEvent.ACTION_DOWN, MotionEvent.ACTION_POINTER_DOWN -> {
                // Check if the joystick was touched
                if (checkInsideJoyStick(eventPos, pointerId))
                    touchDown = true;
                    // Check if the attack button was touched
                else if (checkInsideAttackBtn(eventPos)) {
                    if (attackBtnPointerId < 0) {
                        playing.getPlayer().setAttacking(true); // Set the player to attacking state
                        attackBtnPointerId = pointerId; // Store the pointer ID for the attack button
                    }
                }
                // Check if the menu button was touched
                else {
                    if (isIn(eventPos, btnMenu))
                        btnMenu.setPushed(true, pointerId); // Set the menu button to pushed state
                }
            }

            case MotionEvent.ACTION_MOVE -> {
                // Handle joystick movement
                if (touchDown)
                    for (int i = 0; i < event.getPointerCount(); i++) {
                        if (event.getPointerId(i) == joystickPointerId) {
                            // Calculate movement difference from the joystick's center
                            float xDiff = event.getX(i) - joystickCenterPos.x;
                            float yDiff = event.getY(i) - joystickCenterPos.y;
                            playing.setPlayerMoveTrue(new PointF(xDiff, yDiff)); // Set player movement
                        }
                    }
            }
            case MotionEvent.ACTION_UP, MotionEvent.ACTION_POINTER_UP -> {
                // Handle joystick release
                if (pointerId == joystickPointerId) {
                    resetJoystickButton(); // Reset the joystick state
                } else {
                    // Handle menu button release
                    if (isIn(eventPos, btnMenu))
                        if (btnMenu.isPushed(pointerId)) {
                            resetJoystickButton(); // Reset joystick state
                            playing.setGameStateToMenu(); // Switch game state to the menu
                        }
                    btnMenu.unPush(pointerId); // Reset the menu button state
                    // Handle attack button release
                    if (pointerId == attackBtnPointerId) {
                        playing.getPlayer().setAttacking(false); // Stop attacking
                        attackBtnPointerId = -1; // Reset attack button pointer ID
                    }
                }
            }
        }
    }

    /**
     * Resets the joystick's state when the touch is released.
     * Stops player movement and resets the pointer ID.
     */
    private void resetJoystickButton() {
        touchDown = false; // Reset the touchDown flag
        playing.setPlayerMoveFalse(); // Stop player movement
        joystickPointerId = -1; // Reset the joystick pointer ID
    }

    /**
     * Checks if a touch event occurred inside the bounds of a CustomButton.
     *
     * @param eventPos The touch event position.
     * @param b        The CustomButton to check.
     * @return True if the touch is inside the button's hitbox, false otherwise.
     */
    private boolean isIn(PointF eventPos, CustomButton b) {
        return b.getHitbox().contains(eventPos.x, eventPos.y); // Return true if touch is inside the button's hitbox
    }

//    public void resetPlayerHealth() {
//        this.currentPlayerHealth = maxPlayerHealth;
//    }
}
