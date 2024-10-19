package com.tutorial.androidgametutorial.gamestates;

import android.graphics.Canvas;
import android.view.MotionEvent;

import com.tutorial.androidgametutorial.helpers.interfaces.GameStateInterface;
import com.tutorial.androidgametutorial.main.Game;
import com.tutorial.androidgametutorial.main.MainActivity;
import com.tutorial.androidgametutorial.ui.ButtonImages;
import com.tutorial.androidgametutorial.ui.CustomButton;
import com.tutorial.androidgametutorial.ui.GameImages;

/**
 * Represents the Pause Screen state of the game, providing options to resume or return to the main menu.
 */
public class SettingScreen extends BaseState implements GameStateInterface {

    private final CustomButton btnResume;
    private final CustomButton btnMainMenu; // Buttons for resuming and returning to the main menu
    private final int menuX = (int) (MainActivity.GAME_WIDTH / 2.5); // X-coordinate for menu background
    private final int menuY = 200; // Y-coordinate for menu background

    // X and Y coordinates for the buttons based on the menu background size
    private final int buttonsX = menuX + GameImages.PAUSE_MENU_MENUBG.getImage().getWidth() / 2 - ButtonImages.MENU_START.getWidth() / 2;
    private final int btnResumeY = menuY + 200;
    private final int btnMainMenuY = btnResumeY + 180; // Y-coordinates for the buttons

    /**
     * Constructs a PauseState with the specified Game instance.
     *
     * @param game The Game instance to associate with this state.
     */
    public SettingScreen(Game game) {
        super(game);
        btnResume = new CustomButton(buttonsX, btnResumeY, ButtonImages.PLAYING_RESUME.getWidth(), ButtonImages.PLAYING_RESUME.getHeight()); // Initializes resume button
        btnMainMenu = new CustomButton(buttonsX, btnMainMenuY, ButtonImages.MENU_MENU.getWidth(), ButtonImages.MENU_MENU.getHeight()); // Initializes main menu button
    }

    /**
     * Renders the Pause Screen, including background and buttons.
     *
     * @param c The Canvas on which to draw the screen.
     */
    @Override
    public void render(Canvas c) {
        drawBackground(c);
        drawButtons(c);
    }

    /**
     * Draws the buttons on the screen.
     *
     * @param c The Canvas on which to draw the buttons.
     */
    private void drawButtons(Canvas c) {
        c.drawBitmap(ButtonImages.PLAYING_RESUME.getBtnImg(btnResume.isPushed()),
                btnResume.getHitbox().left,
                btnResume.getHitbox().top,
                null);

        c.drawBitmap(ButtonImages.MENU_MENU.getBtnImg(btnMainMenu.isPushed()),
                btnMainMenu.getHitbox().left,
                btnMainMenu.getHitbox().top,
                null);
    }

    /**
     * Draws the background of the Pause Screen.
     *
     * @param c The Canvas on which to draw the background.
     */
    private void drawBackground(Canvas c) {
        c.drawBitmap(GameImages.PAUSE_MENU_MENUBG.getImage(), menuX, menuY, null);
    }

    /**
     * Handles touch events for the buttons on the Pause Screen.
     *
     * @param event The MotionEvent representing the touch action.
     */
    @Override
    public void touchEvents(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (isIn(event, btnResume))
                btnResume.setPushed(true);
            else if (isIn(event, btnMainMenu))
                btnMainMenu.setPushed(true);

        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            if (isIn(event, btnResume)) {
                if (btnResume.isPushed()) {
                    game.setCurrentGameState(Game.GameState.PLAYING);
                    game.enterCurrentState();
                }
            } else if (isIn(event, btnMainMenu)) {
                if (btnMainMenu.isPushed()) {
                    game.setCurrentGameState(Game.GameState.MENU);
                    game.enterCurrentState();
                    game.getPlaying().reactive(); // Call reset() on Playing state
                }
            }
            btnResume.setPushed(false);
            btnMainMenu.setPushed(false);
        }
    }

    @Override
    public void enter() {

    }

    @Override
    public void exit() {

    }

    /**
     * Updates the state of the Death Screen. Currently does nothing.
     *
     * @param delta The time since the last update.
     */
    @Override
    public void update(double delta) {
        // No updates needed for the Death Screen
    }
}