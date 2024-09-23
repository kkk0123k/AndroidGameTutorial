package com.tutorial.androidgametutorial.main;

import android.content.Context;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

/**
 * Class representing the game panel, which is a SurfaceView for rendering the game.
 */
public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {

    private final Game game; // Reference to the Game instance

    /**
     * Constructs a GamePanel instance with the given context.
     *
     * @param context The context of the application.
     */
    public GamePanel(Context context) {
        super(context); // Call the superclass constructor
        SurfaceHolder holder = getHolder(); // Get the SurfaceHolder for the SurfaceView
        holder.addCallback(this); // Register the SurfaceHolder.Callback
        game = new Game(holder); // Initialize the Game instance
    }

    /**
     * Handles touch events on the game panel.
     *
     * @param event The MotionEvent containing touch information.
     * @return true if the event was handled, false otherwise.
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return game.touchEvent(event); // Pass the touch event to the Game instance
    }

    /**
     * Called when the surface is created.
     *
     * @param surfaceHolder The SurfaceHolder for the surface.
     */
    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        game.startGameLoop(); // Start the game loop when the surface is created
    }

    /**
     * Called when the surface changes (size, format, etc.).
     *
     * @param surfaceHolder The SurfaceHolder for the surface.
     * @param format The new format of the surface.
     * @param width The new width of the surface.
     * @param height The new height of the surface.
     */
    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int format, int width, int height) {
        // This method can be used to handle surface changes, if necessary
    }

    /**
     * Called when the surface is destroyed.
     *
     * @param surfaceHolder The SurfaceHolder for the surface.
     */
    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
        // Cleanup can be handled here if necessary
    }
}
