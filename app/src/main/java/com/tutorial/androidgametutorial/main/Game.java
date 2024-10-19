package com.tutorial.androidgametutorial.main;

import static com.tutorial.androidgametutorial.main.MainActivity.getGameContext;

import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import com.tutorial.androidgametutorial.Music.SoundManager;
import com.tutorial.androidgametutorial.User.SessionHandler;
import com.tutorial.androidgametutorial.User.User;
import com.tutorial.androidgametutorial.gamestates.DeathScreen;
import com.tutorial.androidgametutorial.gamestates.PauseState;
import com.tutorial.androidgametutorial.gamestates.SettingScreen;
import com.tutorial.androidgametutorial.gamestates.VictoryScreen;
import com.tutorial.androidgametutorial.gamestates.Menu;
import com.tutorial.androidgametutorial.gamestates.Playing;

/**
 * Main class for managing the game loop and game states.
 */
public class Game {

    private final SurfaceHolder holder; // Surface holder for drawing on the canvas
    private Menu menu; // Menu game state
    private Playing playing; // Playing game state
    private PauseState pauseState; // Pause state
    private DeathScreen deathScreen; // Death screen game state
    private VictoryScreen victoryScreen;
    private SettingScreen settingScreen;
    private final GameLoop gameLoop; // Manages the game loop
    private GameState currentGameState = GameState.MENU; // Current game state
    public final SoundManager soundManager;
    SessionHandler sessionHandler = new SessionHandler(getGameContext());
    User user = sessionHandler.getUserDetails();
    String progression = user.getProgression();

    /**
     * Constructs the Game instance with the specified SurfaceHolder.
     *
     * @param holder The SurfaceHolder for drawing.
     */
    public Game(SurfaceHolder holder) {
        this.holder = holder; // Assign surface holder
        this.soundManager = new SoundManager(getGameContext()); // Initialize SoundManager
        gameLoop = new GameLoop(this); // Initialize game loop
        initGameStates(); // Initialize game states
    }

    /**
     * Updates the current game state.
     *
     * @param delta The time since the last update.
     */
    public void update(double delta) {
        switch (currentGameState) {
            case MENU -> menu.update(delta); // Update menu state
            case PLAYING -> playing.update(delta); // Update playing state
            case DEATH_SCREEN -> deathScreen.update(delta); // Update death screen state
            case VICTORY_SCREEN -> victoryScreen.update(delta);
            case PAUSE -> pauseState.update(delta);
            case SETTINGS -> settingScreen.update(delta);
        }
    }

    /**
     * Renders the current game state onto the canvas.
     */
    public void render() {
        Canvas c = holder.lockCanvas(); // Locks the canvas for drawing
        if (c != null) {
            c.drawColor(Color.BLACK);
        }
        // Draw the current game state
        switch (currentGameState) {
            case MENU -> menu.render(c); // Render menu
            case PLAYING -> playing.render(c); // Render playing state
            case DEATH_SCREEN -> deathScreen.render(c); // Render death screen
            case VICTORY_SCREEN -> victoryScreen.render(c);
            case PAUSE -> {
                playing.render(c);
                pauseState.render(c);
            }
            case SETTINGS -> {
                settingScreen.render(c);
            }
        }

        holder.unlockCanvasAndPost(c); // Unlocks and posts the canvas for display
    }
    /**
     * Initializes the game states.
     */
    private void initGameStates() {
        menu = new Menu(this, soundManager);; // Initializes the menu state
        playing = new Playing(this, progression, soundManager); // Initializes the playing state
        deathScreen = new DeathScreen(this); // Initializes the death screen state
        victoryScreen = new VictoryScreen(this);
        pauseState = new PauseState(this);
        settingScreen = new SettingScreen(this);
        enterCurrentState(); // Call enterCurrentState() after initialization
    }


    public void enterCurrentState() {
        switch (currentGameState) {
            case MENU:
                menu.enter();
                break;
            case PLAYING:
                playing.enter();
                break;
            case DEATH_SCREEN:
                deathScreen.enter();
                break;
            case VICTORY_SCREEN:
                victoryScreen.enter();
                break;
            case PAUSE:
                pauseState.enter();
                break;
            case SETTINGS:
                settingScreen.enter();
                break;
        }
    }
    /**
     * Sets the Playing instance.
     *
     * @param playing The new Playing instance to set.
     */
    public void setPlaying(Playing playing) {
        this.playing = playing;
    }

    /**
     * Creates a new Playing instance to reset the game.
     */
    public void resetPlaying(String progression) {
        playing = new Playing(this, progression, soundManager); // Create a new playing instance
        this.currentGameState = GameState.PLAYING; // Set the game state to PLAYING
        enterCurrentState(); // Call enterCurrentState() after changing state
    }

    /**
     * Handles touch events based on the current game state.
     *
     * @param event The motion event.
     * @return True if the event was handled, false otherwise.
     */
    public boolean touchEvent(MotionEvent event) {
        switch (currentGameState) {
            case MENU -> menu.touchEvents(event); // Handle touch events for menu
            case PLAYING -> playing.touchEvents(event); // Handle touch events for playing state
            case DEATH_SCREEN -> deathScreen.touchEvents(event); // Handle touch events for death screen
            case VICTORY_SCREEN -> victoryScreen.touchEvents(event);
            case PAUSE -> pauseState.touchEvents(event);
        }

        return true; // Event handled
    }

    /**
     * Starts the game loop.
     */
    public void startGameLoop() {
        gameLoop.startGameLoop(); // Starts the game loop
    }

    /**
     * Enum representing the possible game states.
     */
    public enum GameState {
        MENU, PLAYING, DEATH_SCREEN, VICTORY_SCREEN, PAUSE, SETTINGS // Different game states
    }

    /**
     * Gets the current game state.
     *
     * @return The current game state.
     */
    public GameState getCurrentGameState() {
        return currentGameState; // Returns current game state
    }

    /**
     * Sets the current game state.
     *
     * @param currentGameState The new game state to set.
     */
    public void setCurrentGameState(GameState currentGameState) {
        this.currentGameState = currentGameState; // Sets current game state
    }

    /**
     * Gets the Menu instance.
     *
     * @return The Menu instance.
     */
    public Menu getMenu() {
        return menu; // Returns the menu instance
    }

    /**
     * Gets the Playing instance.
     *
     * @return The Playing instance.
     */
    public Playing getPlaying() {
        return playing; // Returns the playing instance
    }

    public SettingScreen getSettingScreen() {
        return settingScreen;
    }

    /**
     * Gets the DeathScreen instance.
     *
     * @return The DeathScreen instance.
     */
    public DeathScreen getDeathScreen() {
        return deathScreen; // Returns the death screen instance
    }

    public VictoryScreen getVictoryScreen(){
        return victoryScreen;
    }

    public PauseState getPauseState() {
        return pauseState;
    }
}
