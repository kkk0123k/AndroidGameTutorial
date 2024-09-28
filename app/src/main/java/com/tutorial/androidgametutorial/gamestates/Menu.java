package com.tutorial.androidgametutorial.gamestates;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.MotionEvent;

import com.tutorial.androidgametutorial.helpers.interfaces.GameStateInterface;
import com.tutorial.androidgametutorial.main.Game;
import com.tutorial.androidgametutorial.main.MainActivity;
import com.tutorial.androidgametutorial.ui.ButtonImages;
import com.tutorial.androidgametutorial.ui.CustomButton;
import com.tutorial.androidgametutorial.ui.GameImages;

/**
 * Represents the main menu state of the game, providing an option to start the game.
 */
public class Menu extends BaseState implements GameStateInterface {

    private CustomButton btnStart; // Button for starting the game
    private CustomButton btnContinue;
    private Bitmap background; // Variable to hold the background image
    private Bitmap scaledBackground;

    private int menuX = MainActivity.GAME_WIDTH / 6; // X-coordinate for menu background
    private int menuY = 380; // Y-coordinate for menu background

    // X and Y coordinates for the start button based on the menu background size
    private int btnStartX = menuX + GameImages.MAINMENU_MENUBG.getImage().getWidth() / 2 - ButtonImages.MENU_START.getWidth() / 2;
    private int btnStartY = menuY + 100; // Y-coordinate for the start button

    // Position the continue button below the start button
    private int btnContinueX = menuX + GameImages.MAINMENU_MENUBG.getImage().getWidth() / 2 - ButtonImages.MENU_CONTINUE.getWidth() / 2;
    private int btnContinueY = btnStartY + ButtonImages.MENU_START.getHeight() + 40; // Y-coordinate for the continue button (20 pixels below the start button)

    /**
     * Constructs a Menu with the specified Game instance.
     *
     * @param game The Game instance to associate with this state.
     */
    public Menu(Game game) {
        super(game); // Calls the superclass constructor
        btnStart = new CustomButton(btnStartX, btnStartY, ButtonImages.MENU_START.getWidth(), ButtonImages.MENU_START.getHeight()); // Initializes the start button
        btnContinue = new CustomButton(btnContinueX, btnContinueY, ButtonImages.MENU_START.getWidth(), ButtonImages.MENU_CONTINUE.getHeight()); // Initializes the start button
        background = GameImages.MENU_BACKGROUND.getImage();
        scaledBackground = Bitmap.createScaledBitmap(background, MainActivity.GAME_WIDTH, MainActivity.GAME_HEIGHT, true);
    }

    /**
     * Updates the state of the Menu. Currently does nothing.
     *
     * @param delta The time since the last update.
     */
    @Override
    public void update(double delta) {
        // No updates needed for the Menu
    }

    /**
     * Renders the Menu, including the background and the start button.
     *
     * @param c The Canvas on which to draw the menu.
     */
    @Override
    public void render(Canvas c) {

        // Draws the main menu background image
        c.drawBitmap(scaledBackground, 0, 0, null);

        // Draws the main menu frame to store the button in it
        c.drawBitmap(
                GameImages.MAINMENU_MENUBG.getImage(),
                menuX,
                menuY,
                null);

        // Draws the start button image based on its pressed state
        c.drawBitmap(
                ButtonImages.MENU_START.getBtnImg(btnStart.isPushed()),
                btnStart.getHitbox().left,
                btnStart.getHitbox().top,
                null);

        // Draws the continue button image based on its pressed state
        c.drawBitmap(
                ButtonImages.MENU_CONTINUE.getBtnImg(btnContinue.isPushed()),
                btnContinue.getHitbox().left,
                btnContinue.getHitbox().top,
                null);
    }

    /**
     * Handles touch events for the start button in the Menu.
     *
     * @param event The MotionEvent representing the touch action.
     */
    @Override
    public void touchEvents(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            // Checks if the start button is pressed
            if (isIn(event, btnStart))
                btnStart.setPushed(true); // Sets the button as pushed
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            // Checks if the start button was released
            if (isIn(event, btnStart))
                if (btnStart.isPushed())
                    game.setCurrentGameState(Game.GameState.PLAYING); // Sets the game state to playing

            // Resets the pushed state of the start button
            btnStart.setPushed(false);
        }
    }
}
