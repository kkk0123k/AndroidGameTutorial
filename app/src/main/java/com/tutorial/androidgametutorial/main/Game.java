package com.tutorial.androidgametutorial.main;

import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import com.tutorial.androidgametutorial.gamestates.DeathScreen;
import com.tutorial.androidgametutorial.gamestates.Menu;
import com.tutorial.androidgametutorial.gamestates.Playing;

/**
 * Main class for managing the game loop and game states.
 */
public class Game {

    private SurfaceHolder holder; // Surface holder for drawing on the canvas
    private Menu menu; // Menu game state
    private Playing playing; // Playing game state
    private DeathScreen deathScreen; // Death screen game state
    private GameLoop gameLoop; // Manages the game loop
    private GameState currentGameState = GameState.MENU; // Current game state

    /**
     * Constructs the Game instance with the specified SurfaceHolder.
     *
     * @param holder The SurfaceHolder for drawing.
     */
    public Game(SurfaceHolder holder) {
        this.holder = holder; // Assign surface holder
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
        }
    }

    /**
     * Renders the current game state onto the canvas.
     */
    public void render() {
        Canvas c = holder.lockCanvas(); // Locks the canvas for drawing
        c.drawColor(Color.BLACK); // Clears the canvas with black color

        // Draw the current game state
        switch (currentGameState) {
            case MENU -> menu.render(c); // Render menu
            case PLAYING -> playing.render(c); // Render playing state
            case DEATH_SCREEN -> deathScreen.render(c); // Render death screen
        }

        holder.unlockCanvasAndPost(c); // Unlocks and posts the canvas for display
    }

    /**
     * Initializes the game states.
     */
    private void initGameStates() {
        menu = new Menu(this); // Initializes the menu state
        playing = new Playing(this); // Initializes the playing state
        deathScreen = new DeathScreen(this); // Initializes the death screen state
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
        MENU, PLAYING, DEATH_SCREEN; // Different game states
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

    /**
     * Gets the DeathScreen instance.
     *
     * @return The DeathScreen instance.
     */
    public DeathScreen getDeathScreen() {
        return deathScreen; // Returns the death screen instance
    }
}
