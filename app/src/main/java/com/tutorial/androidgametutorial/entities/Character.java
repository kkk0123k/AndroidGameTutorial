package com.tutorial.androidgametutorial.entities;

import static com.tutorial.androidgametutorial.helpers.GameConstants.Sprite.HITBOX_SIZE;
import static com.tutorial.androidgametutorial.helpers.GameConstants.Sprite.X_DRAW_OFFSET;
import static com.tutorial.androidgametutorial.helpers.GameConstants.Sprite.Y_DRAW_OFFSET;

import android.graphics.PointF;
import android.graphics.RectF;

import com.tutorial.androidgametutorial.helpers.GameConstants;

public abstract class Character extends Entity {
    protected int aniTick, aniIndex;
    protected int faceDir = GameConstants.Face_Dir.DOWN;
    protected final GameCharacters gameCharType;
    protected boolean attacking, attackChecked;
    private RectF attackBox = null;
    private int attackDamage;

    private int maxHealth;
    private int currentHealth;

    /**
     * Constructs a Character entity with a specified position and character type.
     *
     * @param pos The position of the character in the game world.
     * @param gameCharType The type of game character (e.g., skeleton, player).
     */
    public Character(PointF pos, GameCharacters gameCharType) {
        super(pos, HITBOX_SIZE, HITBOX_SIZE);
        this.gameCharType = gameCharType;
        attackDamage = setAttackDamage();
        updateWepHitbox();

    }

    /**
     * Sets the starting health for the character.
     *
     * @param health The initial health to be set for the character.
     */
    protected void setStartHealth(int health) {
        maxHealth = health;
        currentHealth = maxHealth;
    }

    /**
     * Resets the character's health to its maximum value.
     */
    public void resetCharacterHealth() {
        currentHealth = maxHealth;
    }

    /**
     * Damages the character by reducing its current health.
     *
     * @param damage The amount of damage to apply to the character's health.
     */
    public void damageCharacter(int damage) {
        this.currentHealth -= damage;
    }

    /**
     * Sets the attack damage based on the character type.
     *
     * @return The calculated attack damage value for the character.
     */
    private int setAttackDamage() {
        return switch (gameCharType) {
            case PLAYER -> 10;
//            case PLAYER -> 50;
            case SKELETON -> 25;
        };
    }

    /**
     * Updates the character's animation frame based on the animation speed.
     */
    protected void updateAnimation() {
        aniTick++;
        if (aniTick >= GameConstants.Animation.SPEED) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= GameConstants.Animation.AMOUNT)
                aniIndex = 0;
        }
    }

    /**
     * Resets the animation to the initial state.
     */
    public void resetAnimation() {
        aniTick = 0;
        aniIndex = 0;
    }

    /**
     * Returns the current animation index, adjusted for attacking state.
     *
     * @return The current animation index for the character.
     */
    public int getAniIndex() {
        if (attacking) return 4;
        return aniIndex;
    }

    /**
     * Returns the current facing direction of the character.
     *
     * @return The facing direction of the character as an integer.
     */
    public int getFaceDir() {
        return faceDir;
    }

    /**
     * Sets the facing direction of the character.
     *
     * @param faceDir The new facing direction to be assigned to the character.
     */
    public void setFaceDir(int faceDir) {
        this.faceDir = faceDir;
    }


    /**
     * Returns the type of game character.
     *
     * @return The character type as a GameCharacters enum.
     */
    public GameCharacters getGameCharType() {
        return gameCharType;
    }


    /**
     * Updates the weapon hitbox based on the character's position and direction.
     */
    public void updateWepHitbox() {
        PointF pos = getWepPos();
        float w = getWepWidth();
        float h = getWepHeight();

        attackBox = new RectF(pos.x, pos.y, pos.x + w, pos.y + h);
    }

    /**
     * Calculates the width of the weapon based on the character's facing direction.
     *
     * @return The calculated width of the weapon.
     */
    public float getWepWidth() {
        //Must keep in mind, there is a rotation active
        return switch (getFaceDir()) {

            case GameConstants.Face_Dir.LEFT, GameConstants.Face_Dir.RIGHT ->
                    Weapons.BIG_SWORD.getHeight();

            case GameConstants.Face_Dir.UP, GameConstants.Face_Dir.DOWN ->
                    Weapons.BIG_SWORD.getWidth();

            default -> throw new IllegalStateException("Unexpected value: " + getFaceDir());
        };
    }

    /**
     * Calculates the height of the weapon based on the character's facing direction.
     *
     * @return The calculated height of the weapon.
     */
    public float getWepHeight() {
        //Must keep in mind, there is a rotation active
        return switch (getFaceDir()) {

            case GameConstants.Face_Dir.UP, GameConstants.Face_Dir.DOWN ->
                    Weapons.BIG_SWORD.getHeight();

            case GameConstants.Face_Dir.LEFT, GameConstants.Face_Dir.RIGHT ->
                    Weapons.BIG_SWORD.getWidth();

            default -> throw new IllegalStateException("Unexpected value: " + getFaceDir());
        };
    }

    /**
     * Calculates the position of the weapon based on the character's facing direction.
     *
     * @return The calculated weapon position as a PointF object.
     */
    public PointF getWepPos() {
        //Must keep in mind, there is a rotation active
        return switch (getFaceDir()) {

            case GameConstants.Face_Dir.UP ->
                    new PointF(getHitbox().left - 0.5f * GameConstants.Sprite.SCALE_MULTIPLIER, getHitbox().top - Weapons.BIG_SWORD.getHeight() - Y_DRAW_OFFSET);

            case GameConstants.Face_Dir.DOWN ->
                    new PointF(getHitbox().left + 0.75f * GameConstants.Sprite.SCALE_MULTIPLIER, getHitbox().bottom);

            case GameConstants.Face_Dir.LEFT ->
                    new PointF(getHitbox().left - Weapons.BIG_SWORD.getHeight() - X_DRAW_OFFSET, getHitbox().bottom - Weapons.BIG_SWORD.getWidth() - 0.75f * GameConstants.Sprite.SCALE_MULTIPLIER);

            case GameConstants.Face_Dir.RIGHT ->
                    new PointF(getHitbox().right + X_DRAW_OFFSET, getHitbox().bottom - Weapons.BIG_SWORD.getWidth() - 0.75f * GameConstants.Sprite.SCALE_MULTIPLIER);

            default -> throw new IllegalStateException("Unexpected value: " + getFaceDir());
        };

    }

    /**
     * Adjusts the top position of the weapon for rotation based on facing direction.
     *
     * @return The adjustment value for the top position of the weapon.
     */
    public float wepRotAdjustTop() {
        return switch (getFaceDir()) {
            case GameConstants.Face_Dir.LEFT, GameConstants.Face_Dir.UP ->
                    -Weapons.BIG_SWORD.getHeight();
            default -> 0;
        };
    }

    /**
     * Adjusts the left position of the weapon for rotation based on facing direction.
     *
     * @return The adjustment value for the left position of the weapon.
     */
    public float wepRotAdjustLeft() {
        return switch (getFaceDir()) {
            case GameConstants.Face_Dir.UP, GameConstants.Face_Dir.RIGHT ->
                    -Weapons.BIG_SWORD.getWidth();
            default -> 0;
        };
    }

    /**
     * Returns the rotation angle of the weapon based on facing direction.
     *
     * @return The rotation angle of the weapon in degrees.
     */
    public float getWepRot() {
        return switch (getFaceDir()) {
            case GameConstants.Face_Dir.LEFT -> 90;
            case GameConstants.Face_Dir.UP -> 180;
            case GameConstants.Face_Dir.RIGHT -> 270;
            default -> 0;
        };

    }

    /**
     * Returns the attack hitbox for collision detection.
     *
     * @return The attack box as a RectF object for collision detection.
     */
    public RectF getAttackBox() {
        return attackBox;
    }

    /**
     * Sets the attacking state of the character.
     *
     * @param attacking The new attacking state to be assigned to the character.
     */
    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
        if (!attacking) attackChecked = false;
    }

    /**
     * Checks if the character is currently attacking.
     *
     * @return True if the character is attacking, false otherwise.
     */
    public boolean isAttacking() {
        return attacking;
    }

    /**
     * Checks if the attack has been verified.
     *
     * @return True if the attack has been checked, false otherwise.
     */
    public boolean isAttackChecked() {
        return attackChecked;
    }

    /**
     * Sets the attack checked state.
     *
     * @param attackChecked The new attack checked state to be assigned to the character.
     */
    public void setAttackChecked(boolean attackChecked) {
        this.attackChecked = attackChecked;
    }

    /**
     * Returns the damage dealt by the character's attack.
     *
     * @return The attack damage value for the character.
     */
    public int getDamage() {
        return attackDamage;
    }

    /**
     * Returns the maximum health of the character.
     *
     * @return The maximum health value for the character.
     */
    public int getMaxHealth() {
        return maxHealth;
    }

    /**
     * Returns the current health of the character.
     *
     * @return The current health value for the character.
     */
    public int getCurrentHealth() {
        return currentHealth;
    }
}

