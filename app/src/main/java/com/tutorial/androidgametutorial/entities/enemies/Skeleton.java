package com.tutorial.androidgametutorial.entities.enemies;

import static com.tutorial.androidgametutorial.main.MainActivity.GAME_HEIGHT;
import static com.tutorial.androidgametutorial.main.MainActivity.GAME_WIDTH;

import android.graphics.PointF;

import com.tutorial.androidgametutorial.entities.Character;
import com.tutorial.androidgametutorial.entities.GameCharacters;
import com.tutorial.androidgametutorial.entities.Player;
import com.tutorial.androidgametutorial.environments.GameMap;
import com.tutorial.androidgametutorial.helpers.GameConstants;
import com.tutorial.androidgametutorial.helpers.HelpMethods;
import com.tutorial.androidgametutorial.main.Game;

import java.util.Random;

public class Skeleton extends Character {
    private long lastDirChange = System.currentTimeMillis();
    private Random rand = new Random();
    private boolean moving = true, preparingAttack;
    private long timerBeforeAttack, timerAttackDuration;
    private long timeToAttack = 500, timeForAttackDuration = 250;

    /**
     * Constructs a Skeleton entity with a specified position.
     *
     * @param pos The position of the skeleton in the game world.
     */
    public Skeleton(PointF pos) {
        super(pos, GameCharacters.SKELETON);
        setStartHealth(100);
    }

    /**
     * Updates the skeleton's state, including movement and attack preparation.
     *
     * @param delta The time delta for frame updates.
     * @param gameMap The current game map.
     */
    public void update(double delta, GameMap gameMap) {
        if (moving) {
            updateMove(delta, gameMap);
            updateAnimation();
        }
        if (preparingAttack) {
            checkTimeToAttackTimer();
        }
        if (attacking) {
            updateAttackTimer();
        }
    }

    /**
     * Prepares the skeleton to attack the player.
     *
     * @param player The player object.
     * @param cameraX The camera's X position.
     * @param cameraY The camera's Y position.
     */
    public void prepareAttack(Player player, float cameraX, float cameraY) {
        timerBeforeAttack = System.currentTimeMillis();
        preparingAttack = true;
        moving = false;
        turnTowardsPlayer(player, cameraX, cameraY);
    }

    /**
     * Adjusts the skeleton's facing direction towards the player.
     *
     * @param player The player object.
     * @param cameraX The camera's X position.
     * @param cameraY The camera's Y position.
     */
    private void turnTowardsPlayer(Player player, float cameraX, float cameraY) {
        float xDelta = hitbox.left - (player.getHitbox().left - cameraX);
        float yDelta = hitbox.top - (player.getHitbox().top - cameraY);

        if (Math.abs(xDelta) > Math.abs(yDelta)) {
            if (hitbox.left < (player.getHitbox().left - cameraX))
                faceDir = GameConstants.Face_Dir.RIGHT;
            else faceDir = GameConstants.Face_Dir.LEFT;
        } else {
            if (hitbox.top < (player.getHitbox().top - cameraY))
                faceDir = GameConstants.Face_Dir.DOWN;
            else faceDir = GameConstants.Face_Dir.UP;
        }
    }


    /**
     * Updates the attack timer to manage attack duration.
     */
    private void updateAttackTimer() {
        if (timerAttackDuration + timeForAttackDuration < System.currentTimeMillis()) {
            setAttacking(false);
            resetAnimation();
            moving = true;
        }
    }

    /**
     * Checks if the skeleton can initiate an attack based on the timer.
     */
    private void checkTimeToAttackTimer() {
        if (timerBeforeAttack + timeToAttack < System.currentTimeMillis()) {
            setAttacking(true);
            preparingAttack = false;
            timerAttackDuration = System.currentTimeMillis();
        }
    }

    /**
     * Updates the skeleton's movement based on its current direction and map.
     *
     * @param delta The time delta for frame updates.
     * @param gameMap The current game map.
     */
    private void updateMove(double delta, GameMap gameMap) {
        if (System.currentTimeMillis() - lastDirChange >= 3000) {
            faceDir = rand.nextInt(4);
            lastDirChange = System.currentTimeMillis();
        }

        float deltaChange = (float) (delta * 300);

        switch (faceDir) {
            case GameConstants.Face_Dir.DOWN:
                if (HelpMethods.CanWalkHere(hitbox, 0, deltaChange, gameMap)) {
                    hitbox.top += deltaChange;
                    hitbox.bottom += deltaChange;
                } else
                    faceDir = GameConstants.Face_Dir.UP;
                break;

            case GameConstants.Face_Dir.UP:
                if (HelpMethods.CanWalkHere(hitbox, 0, -deltaChange, gameMap)) {
                    hitbox.top -= deltaChange;
                    hitbox.bottom -= deltaChange;
                } else
                    faceDir = GameConstants.Face_Dir.DOWN;
                break;

            case GameConstants.Face_Dir.RIGHT:
                if (HelpMethods.CanWalkHere(hitbox, deltaChange, 0, gameMap)) {
                    hitbox.left += deltaChange;
                    hitbox.right += deltaChange;
                } else
                    faceDir = GameConstants.Face_Dir.LEFT;
                break;

            case GameConstants.Face_Dir.LEFT:
                if (HelpMethods.CanWalkHere(hitbox, -deltaChange, 0, gameMap)) {
                    hitbox.left -= deltaChange;
                    hitbox.right -= deltaChange;
                } else
                    faceDir = GameConstants.Face_Dir.RIGHT;
                break;
        }
    }

    /**
     * Checks if the skeleton is currently preparing an attack.
     *
     * @return True if preparing to attack, false otherwise.
     */
    public boolean isPreparingAttack() {
        return preparingAttack;
    }

    /**
     * Deactivates the skeleton, making it inactive in the game.
     */
    public void setSkeletonInactive() {
        active = false;
    }
}
