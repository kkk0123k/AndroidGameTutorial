package com.tutorial.androidgametutorial.gamestates;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;

import androidx.core.content.res.ResourcesCompat;

import com.tutorial.androidgametutorial.Music.SoundManager;
import com.tutorial.androidgametutorial.R;
import com.tutorial.androidgametutorial.helpers.interfaces.GameStateInterface;
import com.tutorial.androidgametutorial.main.Game;
import com.tutorial.androidgametutorial.main.MainActivity;
import com.tutorial.androidgametutorial.ui.ButtonImages;
import com.tutorial.androidgametutorial.ui.CustomButton;

/**
 * Represents the Setting Screen state of the game, providing options to adjust volumes and return to the main menu.
 */
public class SettingScreen extends BaseState implements GameStateInterface {
    private final CustomButton btnBack;
    private final int menuX = (int) (MainActivity.GAME_WIDTH * 0.1);
    private final int menuY = (int) (MainActivity.GAME_HEIGHT * 0.15);

    // Coordinates for the back button
    private final int btnBackX = MainActivity.GAME_WIDTH / 2 - ButtonImages.MENU_MENU.getWidth() / 2; // Center horizontally
    private final int btnBackY = (int) (MainActivity.GAME_HEIGHT * 0.9) - ButtonImages.MENU_MENU.getHeight(); // 10% from bottom

    // Coordinates for the volume sliders and labels
    private final int sliderX = menuX; // X position of the sliders and labels
    private final int bgmSliderY = menuY; // Y position for BGM slider
    private final int sfxSliderY = bgmSliderY + 200; // Y position for SFX slider

    // Simulated volume levels (0-100 range)
    private int bgmVolume = 40; // Default BGM volume
    private int sfxVolume = 40; // Default SFX volume

    // Text positions
    private final int bgmTextX = sliderX; // Align BGM text with slider
    private final int sfxTextX = sliderX; // Align SFX text with slider

    // Slider attributes
    private final int sliderWidth = 500;
    private final int sliderHeight = 6;
    private final int sliderKnobRadius = 40;

    private boolean isSliderTouched = false;
    private float currentTouchX = 0f;
    private String touchedSliderType = "";

    private final SoundManager soundManager;
    private final Paint textPaint; // Paint object for drawing the text
    private Bitmap cachedBackground;

    /**
     * Constructs a SettingScreen with the specified Game instance.
     *
     * @param game The Game instance to associate with this state.
     */
    public SettingScreen(Game game, SoundManager soundManager) {
        super(game);
        this.soundManager = soundManager;
        btnBack = new CustomButton(btnBackX, btnBackY, ButtonImages.SETTING_BACK.getWidth(), ButtonImages.SETTING_BACK.getHeight()); // Initializes back button
        // Initialize Paint for custom font
        textPaint = new Paint();
        Typeface customFont = ResourcesCompat.getFont(MainActivity.getGameContext(), R.font.minimal_pixel_font);
        textPaint.setTypeface(customFont);
        textPaint.setColor(Color.WHITE); // Text color
        textPaint.setTextSize(120); // Text size
        textPaint.setAntiAlias(true); // Smooth edges for text
    }

    /**
     * Renders the Setting Screen, including background, buttons, and volume sliders.
     *
     * @param c The Canvas on which to draw the screen.
     */
    @Override
    public void render(Canvas c) {
        drawCachedBackground(c);
        // Draw dynamic elements
        drawButtons(c); // Draw buttons
        drawSliderKnob(c, sliderX + 150, bgmSliderY - 10, bgmVolume); // Draw BGM knob
        drawSliderKnob(c, sliderX + 150, sfxSliderY - 10, sfxVolume); // Draw SFX knob
    }

    /**
     * Draws the buttons on the screen.
     *
     * @param c The Canvas on which to draw the buttons.
     */
    private void drawButtons(Canvas c) {
        c.drawBitmap(ButtonImages.SETTING_BACK.getBtnImg(btnBack.isPushed()),
                btnBack.getHitbox().left,
                btnBack.getHitbox().top,
                null);
    }

    /**
     * Draws the volume sliders and their labels ("BGM" and "SFX").
     *
     * @param c The Canvas on which to draw the sliders and labels.
     */
    private void drawVolumeSliders(Canvas c) {
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);

        // Draw "BGM" text aligned next to the BGM slider
        c.drawText("BGM", bgmTextX, bgmSliderY + sliderHeight + 20, textPaint);  // Adjust Y position to align with slider

        // Draw the BGM slider
        drawSlider(c, sliderX + 150, bgmSliderY - 10, bgmVolume);

        // Draw "SFX" text aligned next to the SFX slider
        c.drawText("SFX", sfxTextX, sfxSliderY + sliderHeight + 20, textPaint);  // Adjust Y position to align with slider

        // Draw the SFX slider
        drawSlider(c, sliderX + 150, sfxSliderY - 10, sfxVolume);
    }

    /**
     * Draws a custom slider on the canvas.
     *
     * @param c           The Canvas on which to draw the slider.
     * @param x           The x-coordinate of the slider.
     * @param y           The y-coordinate of the slider.
     * @param volumeLevel The current volume level to represent the slider knob position (0-100).
     */
    private void drawSlider(Canvas c, int x, int y, int volumeLevel) {
        Paint paint = new Paint();
        paint.setColor(Color.GRAY);
        c.drawRect(x, y, x + sliderWidth, y + sliderHeight, paint); // Draw the slider track
    }

    private void drawSliderKnob(Canvas c, int x, int y, int volumeLevel) {
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        int knobX = x + (volumeLevel * sliderWidth / 100);
        c.drawCircle(knobX, y + sliderHeight / 2, sliderKnobRadius, paint); // Draw the knob
    }

    private void drawCachedBackground(Canvas c) {
        if (cachedBackground == null || cachedBackground.isRecycled()) {
            cachedBackground = Bitmap.createBitmap(MainActivity.GAME_WIDTH, MainActivity.GAME_HEIGHT, Bitmap.Config.ARGB_8888);
            Canvas cacheCanvas = new Canvas(cachedBackground);

            // Draw static elements onto cacheCanvas
            cacheCanvas.drawColor(Color.parseColor("#3a4466")); // Background color
            drawVolumeSliders(cacheCanvas); // Draw slider tracks and text
        }

        // Draw the cached background onto the main canvas
        c.drawBitmap(cachedBackground, 0, 0, null);
    }

    /**
     * Handles touch events for the buttons and sliders on the Setting Screen.
     *
     * @param event The MotionEvent representing the touch action.
     */
    @Override
    public void touchEvents(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            // Check if the back button is pressed
            if (isIn(event, btnBack)) {
                btnBack.setPushed(true);
            }

            // Check if BGM slider is touched
            if (isInSliderBounds((int) event.getX(), (int) event.getY(), sliderX + 150, bgmSliderY - 40)) {
                isSliderTouched = true;
                currentTouchX = event.getX();
                touchedSliderType = "BGM";
                updateSlider(currentTouchX, touchedSliderType); // Update slider immediately on touch down
            }

            // Check if SFX slider is touched
            if (isInSliderBounds((int) event.getX(), (int) event.getY(), sliderX + 150, sfxSliderY - 40)) {
                isSliderTouched = true;
                currentTouchX = event.getX();
                touchedSliderType = "SFX";
                updateSlider(currentTouchX, touchedSliderType); // Update slider immediately on touch down
            }

        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            // Handle button and slider release
            if (isIn(event, btnBack)) {
                if (btnBack.isPushed()) {
                    game.setCurrentGameState(game.getPreviousGameState());
                    game.enterCurrentState();
                }
            }
            btnBack.setPushed(false);
            isSliderTouched = false;

        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            if (isSliderTouched) {
                currentTouchX = event.getX();
                updateSlider(currentTouchX, touchedSliderType); // Continuously update slider as it moves
            }
        }
    }

    /**
     * Checks if the touch event is within the slider bounds.
     */
    private boolean isInSliderBounds(int touchX, int touchY, int sliderX, int sliderY) {
        int padding = sliderKnobRadius; // Adjust padding as needed

        return touchX >= sliderX - padding &&
                touchX <= (sliderX + sliderWidth + padding) &&
                touchY >= sliderY - padding &&
                touchY <= (sliderY + sliderHeight + padding);
    }

    /**
     * Updates the slider value based on the touch position.
     *
     * @param touchX     The x-coordinate of the touch event.
     * @param sliderType Either "BGM" or "SFX" to update the corresponding volume.
     */
    private void updateSlider(float touchX, String sliderType) {
        int volume = (int) ((touchX - (sliderX + 150)) * 100 / sliderWidth); // Calculate volume (0-100)

        if (volume < 0) volume = 0;
        if (volume > 100) volume = 100;

        if (sliderType.equals("BGM")) {
            bgmVolume = volume;
            soundManager.setBackgroundMusicVolume(bgmVolume / 100f); // Update BGM volume
        } else if (sliderType.equals("SFX")) {
            sfxVolume = volume;
            soundManager.setActionMusicVolume(sfxVolume / 100f); // Update SFX volume
        }
    }

    @Override
    public void enter() {
    }

    @Override
    public void exit() {
    }

    @Override
    public void update(double delta) {

    }
}
