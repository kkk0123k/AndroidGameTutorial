package com.tutorial.androidgametutorial.gamestates;

import static com.tutorial.androidgametutorial.main.MainActivity.getGameContext;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;

import com.tutorial.androidgametutorial.User.SessionHandler;
import com.tutorial.androidgametutorial.User.User;
import com.tutorial.androidgametutorial.environments.Stages;
import com.tutorial.androidgametutorial.helpers.interfaces.GameStateInterface;
import com.tutorial.androidgametutorial.main.Game;
import com.tutorial.androidgametutorial.main.MainActivity;
import com.tutorial.androidgametutorial.ui.ButtonImages;
import com.tutorial.androidgametutorial.ui.CustomButton;
import com.tutorial.androidgametutorial.ui.GameImages;

/**
 * Represents the Death Screen state of the game, providing options to replay or return to the main menu.
 */
public class VictoryScreen extends BaseState implements GameStateInterface {

    private static final String PREF_NAME = "UserSession";
    private static final String KEY_PROGRESSION = "progression";

    private final CustomButton btnReplay;
    private final CustomButton btnMainMenu;
    private final CustomButton btnNext; // Buttons for replaying and returning to the main menu

    private final int menuX = (int) (MainActivity.GAME_WIDTH / 2.5); // X-coordinate for menu background
    private final int menuY = 150; // Y-coordinate for menu background

    // X and Y coordinates for the buttons based on the menu background size
    private final int buttonsX = menuX + GameImages.VICTORY_MENU_MENUBG.getImage().getWidth() / 2 - ButtonImages.MENU_START.getWidth() / 2;
    private final int btnReplayY = menuY + 220;
    private final int btnMainMenuY = btnReplayY + 180;
    private final int btnNextY = btnMainMenuY + 180; // Y-coordinates for the buttons

    /**
     * Constructs a DeathScreen with the specified Game instance.
     *
     * @param game The Game instance to associate with this state.
     */
    public VictoryScreen(Game game) {
        super(game); // Calls the superclass constructor
        btnReplay = new CustomButton(buttonsX, btnReplayY, ButtonImages.MENU_REPLAY.getWidth(), ButtonImages.MENU_REPLAY.getHeight()); // Initializes replay button
        btnMainMenu = new CustomButton(buttonsX, btnMainMenuY, ButtonImages.MENU_MENU.getWidth(), ButtonImages.MENU_MENU.getHeight()); // Initializes main menu button
        btnNext = new CustomButton(buttonsX, btnNextY, ButtonImages.VICTORY_NEXT.getWidth(), ButtonImages.VICTORY_NEXT.getHeight()); // Initializes main menu button
    }

    /**
     * Renders the Death Screen, including background and buttons.
     *
     * @param c The Canvas on which to draw the screen.
     */
    @Override
    public void render(Canvas c) {
        drawBackground(c); // Draws the background image
        drawButtons(c); // Draws the buttons
    }

    /**
     * Draws the buttons on the screen.
     *
     * @param c The Canvas on which to draw the buttons.
     */
    private void drawButtons(Canvas c) {
        // Draws the replay button image based on its pressed state
        c.drawBitmap(ButtonImages.MENU_REPLAY.getBtnImg(btnReplay.isPushed()),
                btnReplay.getHitbox().left,
                btnReplay.getHitbox().top,
                null);

        // Draws the main menu button image based on its pressed state
        c.drawBitmap(ButtonImages.MENU_MENU.getBtnImg(btnMainMenu.isPushed()),
                btnMainMenu.getHitbox().left,
                btnMainMenu.getHitbox().top,
                null);
        // Draws the continue button image based on its pressed state
        c.drawBitmap(ButtonImages.VICTORY_NEXT.getBtnImg(btnNext.isPushed()),
                btnNext.getHitbox().left,
                btnNext.getHitbox().top,
                null);
    }

    /**
     * Draws the background of the Death Screen.
     *
     * @param c The Canvas on which to draw the background.
     */
    private void drawBackground(Canvas c) {
        // Draws the death menu background image
        c.drawBitmap(GameImages.VICTORY_MENU_MENUBG.getImage(),
                menuX, menuY, null);
    }

    /**
     * Handles touch events for the buttons on the Death Screen.
     *
     * @param event The MotionEvent representing the touch action.
     */
    @Override
    public void touchEvents(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            // Checks if the replay button is pressed
            if (isIn(event, btnReplay))
                btnReplay.setPushed(true); // Sets the button as pushed
                // Checks if the main menu button is pressed
            else if (isIn(event, btnMainMenu))
                btnMainMenu.setPushed(true); // Sets the button as pushed
                // Checks if the continue button is pressed
            else if (isIn(event, btnNext))
                btnNext.setPushed(true); // Sets the button as pushed

        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            // Checks if the replay button was released
            if (isIn(event, btnReplay)) {
                if (btnReplay.isPushed()) {
                    game.setCurrentGameState(Game.GameState.PLAYING); // Sets the game state to playing
                }
                game.getPlaying().reactive(); // Call reactivate() on Playing state

                // Checks if the main menu button was released
            } else if (isIn(event, btnMainMenu)) {
                if (btnMainMenu.isPushed()) {
                    game.setCurrentGameState(Game.GameState.MENU); // Sets the game state to menu
                    game.getPlaying().reactive(); // Call reactivate() on Playing state
                }
                // Checks if the next button was released
            } else if (isIn(event, btnNext)) {
                if (btnNext.isPushed()) {

                    SessionHandler sessionHandler = new SessionHandler(getGameContext());
                    User user = sessionHandler.getUserDetails();
                    String username = user.getUsername();
                    String progression = user.getProgression();
                    Stages stage = Stages.valueOf(progression); // Get the stage based on progression
                    Log.d("MainActivity", "Username: " + username);
                    user.setProgression(stage.getNextStage()); // Update the progression

                    // Update Shared Preferences immediately
                    SharedPreferences sharedPreferences = getGameContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(KEY_PROGRESSION, stage.getNextStage());
                    editor.apply(); // Use apply() for asynchronous update

                    // Update progression using the provided method
                    sessionHandler.updateProgression(username, stage.getNextStage());
                    game.resetPlaying(user.getProgression());
                }
            }

            // Resets the pushed state of all buttons
            btnReplay.setPushed(false);
            btnMainMenu.setPushed(false);
            btnNext.setPushed(false);
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
