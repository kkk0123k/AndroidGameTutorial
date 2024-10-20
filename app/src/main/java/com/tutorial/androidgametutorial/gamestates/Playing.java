package com.tutorial.androidgametutorial.gamestates;

import static com.tutorial.androidgametutorial.helpers.GameConstants.Sprite.X_DRAW_OFFSET;
import static com.tutorial.androidgametutorial.main.MainActivity.GAME_HEIGHT;
import static com.tutorial.androidgametutorial.main.MainActivity.GAME_WIDTH;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.view.MotionEvent;

import com.tutorial.androidgametutorial.Music.SoundManager;
import com.tutorial.androidgametutorial.entities.Building;
import com.tutorial.androidgametutorial.entities.Character;
import com.tutorial.androidgametutorial.entities.Entity;
import com.tutorial.androidgametutorial.entities.GameObject;
import com.tutorial.androidgametutorial.entities.Player;
import com.tutorial.androidgametutorial.entities.Weapons;
import com.tutorial.androidgametutorial.entities.enemies.Skeleton;
import com.tutorial.androidgametutorial.environments.Doorway;
import com.tutorial.androidgametutorial.environments.GameMap;
import com.tutorial.androidgametutorial.environments.MapManager;
import com.tutorial.androidgametutorial.environments.Stages;
import com.tutorial.androidgametutorial.helpers.GameConstants;
import com.tutorial.androidgametutorial.helpers.HelpMethods;
import com.tutorial.androidgametutorial.helpers.interfaces.GameStateInterface;
import com.tutorial.androidgametutorial.main.Game;
import com.tutorial.androidgametutorial.ui.PlayingUI;

import java.util.Arrays;

public class Playing extends BaseState implements GameStateInterface {

    /**
     * Represents the playing state of the game, where the main gameplay occurs.
     */

    private float cameraX, cameraY;
    private boolean movePlayer;
    private PointF lastTouchDiff;
    private final MapManager mapManager;
    private final Player player;
    private final PlayingUI playingUI;
    private final Paint redPaint, healthBarRed, healthBarBlack;
    private final PauseState pauseState; // Add this line to hold the PauseState instance
    private Stages stage;

    private boolean doorwayJustPassed;
    private Entity[] listOfDrawables;
    private boolean listOfEntitiesMade;

    private final SoundManager soundManager; // SoundManager instance

    /**
     * Initializes the Playing state with the game instance.
     *
     * @param game The game instance being played.
     */
    public Playing(Game game, String progression, SoundManager soundManager) {
        super(game);
        mapManager = new MapManager(this, progression);
        this.soundManager = soundManager;
        calcStartCameraValues();
        this.pauseState = game.getPauseState();
        player = new Player();
        stage = Stages.valueOf(progression);

        playingUI = new PlayingUI(this, soundManager);

        redPaint = new Paint();
        redPaint.setStrokeWidth(1);
        redPaint.setStyle(Paint.Style.STROKE);
        redPaint.setColor(Color.RED);

        healthBarRed = new Paint();
        healthBarBlack = new Paint();
        initHealthBars();
    }

    /**
     * Initializes health bar paints for visual representation.
     */
    private void initHealthBars() {
        healthBarRed.setStrokeWidth(10);
        healthBarRed.setStyle(Paint.Style.STROKE);
        healthBarRed.setColor(Color.RED);
        healthBarBlack.setStrokeWidth(14);
        healthBarBlack.setStyle(Paint.Style.STROKE);
        healthBarBlack.setColor(Color.BLACK);

    }

    /**
     * Calculates initial camera values based on the game map size.
     */
    private void calcStartCameraValues() {
        cameraX = GAME_WIDTH / 2f - mapManager.getMaxWidthCurrentMap() / 2f;
        cameraY = GAME_HEIGHT / 2f - mapManager.getMaxHeightCurrentMap() / 2f;
    }

    /**
     * Updates the game state.
     *
     * @param delta The time difference since the last update.
     */
    @Override
    public void update(double delta) {
        buildEntityList();
        updatePlayerMove(delta);
        player.update(delta, movePlayer);
        mapManager.setCameraValues(cameraX, cameraY);
        checkForDoorway();

        if (player.isAttacking()) if (!player.isAttackChecked()) checkPlayerAttack();

        if (mapManager.getCurrentMap().getSkeletonArrayList() != null)
            for (Skeleton skeleton : mapManager.getCurrentMap().getSkeletonArrayList())
                if (skeleton.isActive()) {
                    skeleton.update(delta, mapManager.getCurrentMap());
                    if (skeleton.isAttacking()) {
                        if (!skeleton.isAttackChecked()) {
                            checkEnemyAttack(skeleton);
                        }
                    } else if (!skeleton.isPreparingAttack()) {
                        if (HelpMethods.IsPlayerCloseForAttack(skeleton, player, cameraY, cameraX)) {
                            skeleton.prepareAttack(player, cameraX, cameraY);
                        }
                    }
                }

        sortArray();

    }

    /**
     * Builds a list of drawable entities for the current map.
     */
    private void buildEntityList() {
        listOfDrawables = mapManager.getCurrentMap().getDrawableList();
        listOfDrawables[listOfDrawables.length - 1] = player;
        listOfEntitiesMade = true;
    }

    /**
     * Sorts the drawable entities based on their Y-coordinate.
     */
    private void sortArray() {
        player.setLastCameraYValue(cameraY);
        Arrays.sort(listOfDrawables);
    }

    /**
     * Sets the camera values based on the provided position.
     *
     * @param cameraPos The new camera position.
     */
    public void setCameraValues(PointF cameraPos) {
        this.cameraX = cameraPos.x;
        this.cameraY = cameraPos.y;
    }

    /**
     * Checks if the player is on a doorway and changes maps if necessary.
     */
    private void checkForDoorway() {
        Doorway doorwayPlayerIsOn = mapManager.isPlayerOnDoorway(player.getHitbox());

        if (doorwayPlayerIsOn != null) {
            if (!doorwayJustPassed) mapManager.changeMap(doorwayPlayerIsOn.getDoorwayConnectedTo());
        } else doorwayJustPassed = false;

    }

    /**
     * Sets the flag indicating if a doorway was just passed.
     *
     * @param doorwayJustPassed Indicates if a doorway was just passed.
     */
    public void setDoorwayJustPassed(boolean doorwayJustPassed) {
        this.doorwayJustPassed = doorwayJustPassed;
    }

    /**
     * Checks if the enemy character is attacking the player.
     *
     * @param character The enemy character checking for an attack.
     */
    private void checkEnemyAttack(Character character) {
        character.updateWepHitbox();
        RectF playerHitBox = new RectF(player.getHitbox());
        playerHitBox.left -= cameraX;
        playerHitBox.top -= cameraY;
        playerHitBox.right -= cameraX;
        playerHitBox.bottom -= cameraY;
        if (RectF.intersects(character.getAttackBox(), playerHitBox)) {
            System.out.println("Enemy Hit Player!");
            player.damageCharacter(character.getDamage());
            checkPlayerDead();
        } else {
            System.out.println("Enemy Missed Player!");
        }
        character.setAttackChecked(true);
    }

    /**
     * Checks if the player is dead and transitions to the death screen if necessary.
     */
    private void checkPlayerDead() {
        if (player.getCurrentHealth() > 0)
            return;

        game.setCurrentGameState(Game.GameState.DEATH_SCREEN);

        player.resetCharacterHealth();

    }

    /**
     * Checks if the player is attacking and processes damage to enemies.
     */
    private void checkPlayerAttack() {

        RectF attackBoxWithoutCamera = new RectF(player.getAttackBox());
        attackBoxWithoutCamera.left -= cameraX;
        attackBoxWithoutCamera.top -= cameraY;
        attackBoxWithoutCamera.right -= cameraX;
        attackBoxWithoutCamera.bottom -= cameraY;

        if (mapManager.getCurrentMap().getSkeletonArrayList() != null)
            for (Skeleton s : mapManager.getCurrentMap().getSkeletonArrayList())
                if (attackBoxWithoutCamera.intersects(s.getHitbox().left, s.getHitbox().top, s.getHitbox().right, s.getHitbox().bottom)) {
                    s.damageCharacter(player.getDamage());

                    if (s.getCurrentHealth() <= 0) {
                        s.setSkeletonInactive();
                        checkActiveSkeletons();
                    }
//                    s.setActive(false);
                }
//

        player.setAttackChecked(true);
    }

    /**
     * Checks if there are any active skeletons remaining.
     * If none remain, transitions to the death screen.
     */
    private void checkActiveSkeletons() {
        boolean allSkeletonsInactive = true;

        for (GameMap map : mapManager.getAllMaps()) { // Iterate through all maps
            if (map.getSkeletonArrayList() != null) {
                for (Skeleton skeleton : map.getSkeletonArrayList()) {
                    if (skeleton.isActive()) {
                        allSkeletonsInactive = false;
                        break; // Exit early if at least one skeleton is active
                    }
                }
                if (!allSkeletonsInactive) {
                    break; // Exit outer loop if any skeleton is active
                }
            }
        }

        if (allSkeletonsInactive) {
            game.setCurrentGameState(Game.GameState.VICTORY_SCREEN); // Transition to victory screen
            player.resetCharacterHealth();
        }
    }


    /**
     * Renders the game state on the canvas.
     *
     * @param c The canvas on which to draw the game state.
     */
    @Override
    public void render(Canvas c) {
        mapManager.drawTiles(c);
        if (listOfEntitiesMade)
            drawSortedEntities(c);

        playingUI.draw(c);
    }

    /**
     * Draws the sorted entities on the canvas.
     *
     * @param c The canvas for rendering entities.
     */
    private void drawSortedEntities(Canvas c) {
        for (Entity e : listOfDrawables) {
            if (e instanceof Skeleton skeleton) {
                if (skeleton.isActive()) drawCharacter(c, skeleton);
            } else if (e instanceof GameObject gameObject) {
                mapManager.drawObject(c, gameObject);
            } else if (e instanceof Building building) {
                mapManager.drawBuilding(c, building);
            } else if (e instanceof Player) {
                drawPlayer(c);
            }
        }
    }

    /**
     * Draws the player character on the canvas.
     *
     * @param c The canvas for rendering the player character.
     */
    private void drawPlayer(Canvas c) {
        c.drawBitmap(Weapons.SHADOW.getWeaponImg(), player.getHitbox().left, player.getHitbox().bottom - 5 * GameConstants.Sprite.SCALE_MULTIPLIER, null);
        c.drawBitmap(player.getGameCharType().getSprite(player.getAniIndex(), player.getFaceDir()), player.getHitbox().left - X_DRAW_OFFSET, player.getHitbox().top - GameConstants.Sprite.Y_DRAW_OFFSET, null);
        if (player.isAttacking()) drawWeapon(c, player);
    }

    /**
     * Draws the weapon of the specified character.
     *
     * @param c        The canvas for rendering the weapon.
     * @param character The character whose weapon is being drawn.
     */
    private void drawWeapon(Canvas c, Character character) {
        c.rotate(character.getWepRot(), character.getAttackBox().left, character.getAttackBox().top);
        c.drawBitmap(Weapons.BIG_SWORD.getWeaponImg(), character.getAttackBox().left + character.wepRotAdjustLeft(), character.getAttackBox().top + character.wepRotAdjustTop(), null);
        c.rotate(character.getWepRot() * -1, character.getAttackBox().left, character.getAttackBox().top);
    }


    /**
     * Draws the weapon of the specified enemy character.
     *
     * @param c        The canvas for rendering the enemy weapon.
     * @param character The character whose weapon is being drawn.
     */
    private void drawEnemyWeapon(Canvas c, Character character) {
        c.rotate(character.getWepRot(), character.getAttackBox().left + cameraX, character.getAttackBox().top + cameraY);
        c.drawBitmap(Weapons.BIG_SWORD.getWeaponImg(), character.getAttackBox().left + cameraX + character.wepRotAdjustLeft(), character.getAttackBox().top + cameraY + character.wepRotAdjustTop(), null);
        c.rotate(character.getWepRot() * -1, character.getAttackBox().left + cameraX, character.getAttackBox().top + cameraY);
//        c.drawRect(character.getAttackBox(), redPaint);
    }

    /**
     * Draws the specified character on the canvas.
     *
     * @param canvas The canvas for rendering the character.
     * @param c      The character to be drawn.
     */
    public void drawCharacter(Canvas canvas, Character c) {
        canvas.drawBitmap(Weapons.SHADOW.getWeaponImg(), c.getHitbox().left + cameraX, c.getHitbox().bottom - 5 * GameConstants.Sprite.SCALE_MULTIPLIER + cameraY, null);
        canvas.drawBitmap(c.getGameCharType().getSprite(c.getAniIndex(), c.getFaceDir()), c.getHitbox().left + cameraX - X_DRAW_OFFSET, c.getHitbox().top + cameraY - GameConstants.Sprite.Y_DRAW_OFFSET, null);
//        canvas.drawRect(c.getHitbox().left + cameraX, c.getHitbox().top + cameraY, c.getHitbox().right + cameraX, c.getHitbox().bottom + cameraY, redPaint);
        if (c.isAttacking())
            drawEnemyWeapon(canvas, c);

        if (c.getCurrentHealth() < c.getMaxHealth())
            drawHealthBar(canvas, c);
    }

    /**
     * Draws the health bar for the specified character.
     *
     * @param canvas The canvas for rendering the health bar.
     * @param c      The character whose health bar is being drawn.
     */
    private void drawHealthBar(Canvas canvas, Character c) {
        canvas.drawLine(c.getHitbox().left + cameraX,
                c.getHitbox().top + cameraY - 5 * GameConstants.Sprite.SCALE_MULTIPLIER,
                c.getHitbox().right + cameraX,
                c.getHitbox().top + cameraY - 5 * GameConstants.Sprite.SCALE_MULTIPLIER, healthBarBlack);

        float fullBarWidth = c.getHitbox().width();
        float percentOfMaxHealth = (float) c.getCurrentHealth() / c.getMaxHealth();
        float barWidth = fullBarWidth * percentOfMaxHealth;
        float xDelta = (fullBarWidth - barWidth) / 2.0f;


        canvas.drawLine(c.getHitbox().left + cameraX + xDelta,
                c.getHitbox().top + cameraY - 5 * GameConstants.Sprite.SCALE_MULTIPLIER,
                c.getHitbox().left + cameraX + xDelta + barWidth,
                c.getHitbox().top + cameraY - 5 * GameConstants.Sprite.SCALE_MULTIPLIER, healthBarRed);
    }

    /**
     * Updates the player's movement based on input.
     *
     * @param delta The time difference since the last update.
     */
    private void updatePlayerMove(double delta) {
        if (!movePlayer) return;

        float baseSpeed = (float) (delta * 300);
        float ratio = Math.abs(lastTouchDiff.y) / Math.abs(lastTouchDiff.x);
        double angle = Math.atan(ratio);

        float xSpeed = (float) Math.cos(angle);
        float ySpeed = (float) Math.sin(angle);

        if (xSpeed > ySpeed) {
            if (lastTouchDiff.x > 0) player.setFaceDir(GameConstants.Face_Dir.RIGHT);
            else player.setFaceDir(GameConstants.Face_Dir.LEFT);
        } else {
            if (lastTouchDiff.y > 0) player.setFaceDir(GameConstants.Face_Dir.DOWN);
            else player.setFaceDir(GameConstants.Face_Dir.UP);
        }

        if (lastTouchDiff.x < 0) xSpeed *= -1;
        if (lastTouchDiff.y < 0) ySpeed *= -1;

        float deltaX = xSpeed * baseSpeed * -1;
        float deltaY = ySpeed * baseSpeed * -1;

        float deltaCameraX = cameraX * -1 + deltaX * -1;
        float deltaCameraY = cameraY * -1 + deltaY * -1;

        if (HelpMethods.CanWalkHere(player.getHitbox(), deltaCameraX, deltaCameraY, mapManager.getCurrentMap())) {
            cameraX += deltaX;
            cameraY += deltaY;
        } else {
            if (HelpMethods.CanWalkHereUpDown(player.getHitbox(), deltaCameraY, cameraX * -1, mapManager.getCurrentMap()))
                cameraY += deltaY;

            if (HelpMethods.CanWalkHereLeftRight(player.getHitbox(), deltaCameraX, cameraY * -1, mapManager.getCurrentMap()))
                cameraX += deltaX;
        }
    }

    /**
     * Transitions the game state to the menu.
     */
    public void setGameStateToMenu() {
        game.setCurrentGameState(Game.GameState.MENU);
    }

    public void setGameStateToPause() {
        exit();
        game.setCurrentGameState(Game.GameState.PAUSE);
    }

    /**
     * Sets the player's movement to true and records the touch difference.
     *
     * @param lastTouchDiff The difference in touch movement.
     */
    public void setPlayerMoveTrue(PointF lastTouchDiff) {
        movePlayer = true;
        this.lastTouchDiff = lastTouchDiff;
    }

    /**
     * Stops player movement and resets animation.
     */
    public void setPlayerMoveFalse() {
        movePlayer = false;
        player.resetAnimation();
    }

    /**
     * Processes touch events in the game.
     *
     * @param event The touch event occurring in the game.
     */
    @Override
    public void touchEvents(MotionEvent event) {
        playingUI.touchEvents(event);
    }

    /**
     * Returns the player instance.
     *
     * @return The player instance.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Returns the UI associated with the playing state.
     *
     * @return The playing UI instance.
     */
    public PlayingUI getPlayingUI() {
        return playingUI;
    }

    public void reactive() {
        // Reset player
        player.resetCharacterHealth();
        player.setPosition(GAME_WIDTH / 2, GAME_HEIGHT / 2);
        // Reset camera
        calcStartCameraValues();

        // Reset skeletons in all maps
        for (GameMap map : mapManager.getAllMaps()) {
            if (map.getSkeletonArrayList() != null) {
                for (Skeleton skeleton : map.getSkeletonArrayList()) {
                    skeleton.reactivate();
                    skeleton.setActive(true);
                }
            }
        }

        // Reset other game elements (if any)
        doorwayJustPassed = false;
        listOfEntitiesMade = false;
    }

    @Override
    public void enter() {
            soundManager.playBGPlayingMusic();
    }

    @Override
    public void exit() {
            soundManager.stopBGPlayingMusic();
    }

}
