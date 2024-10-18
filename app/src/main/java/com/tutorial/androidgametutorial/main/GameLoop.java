package com.tutorial.androidgametutorial.main;

/**
 * Class responsible for managing the game loop.
 */
public class GameLoop implements Runnable {

    private final Thread gameThread; // Thread that runs the game loop
    private final Game game; // Reference to the Game instance

    /**
     * Constructs a GameLoop instance for the specified Game.
     *
     * @param game The Game instance to manage.
     */
    public GameLoop(Game game) {
        this.game = game; // Assign the game instance
        gameThread = new Thread(this); // Create a new thread for the game loop
    }

    /**
     * The main method that runs the game loop.
     */
    @Override
    public void run() {

        long lastFPSCheck = System.currentTimeMillis(); // Time for FPS check
        int fps = 0; // Frames per second counter

        long lastDelta = System.nanoTime(); // Last update time
        long nanoSec = 1_000_000_000; // Conversion factor from nanoseconds to seconds

        while (true) {
            long nowDelta = System.nanoTime(); // Current time in nanoseconds
            double timeSinceLastDelta = nowDelta - lastDelta; // Time since last update
            double delta = timeSinceLastDelta / nanoSec; // Convert to seconds

            game.update(delta); // Update the game state
            game.render(); // Render the game on the canvas
            lastDelta = nowDelta; // Update the last update time

            // FPS calculation (commented out)
            // fps++;
            // long now = System.currentTimeMillis();
            // if (now - lastFPSCheck >= 1000) {
            //     System.out.println("FPS: " + fps); // Print the current FPS
            //     fps = 0; // Reset the FPS counter
            //     lastFPSCheck += 1000; // Update the FPS check time
            // }
        }
    }

    /**
     * Starts the game loop thread.
     */
    public void startGameLoop() {
        gameThread.start(); // Starts the game loop thread
    }
}
