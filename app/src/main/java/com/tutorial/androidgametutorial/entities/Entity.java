package com.tutorial.androidgametutorial.entities;

import android.graphics.PointF;
import android.graphics.RectF;

public abstract class Entity implements Comparable<Entity> {

    protected RectF hitbox;
    protected boolean active = true;
    protected float lastCameraYValue = 0;

    /**
     * Constructs an Entity with a specified position and dimensions.
     *
     * @param pos The position of the entity in the game world.
     * @param width The width of the entity's hitbox.
     * @param height The height of the entity's hitbox.
     */
    public Entity(PointF pos, float width, float height) {
        this.hitbox = new RectF(pos.x, pos.y, pos.x + width, pos.y + height);
    }

    /**
     * Sets the active state of the entity.
     *
     * @param active The new active state to be assigned to the entity.
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Checks if the entity is currently active.
     *
     * @return True if the entity is active, false otherwise.
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Returns the hitbox of the entity for collision detection.
     *
     * @return The hitbox as a RectF object.
     */
    public RectF getHitbox() {
        return hitbox;
    }


    /**
     * Sets the last Y value of the camera for positioning.
     *
     * @param lastCameraYValue The Y value of the camera to be stored.
     */
    public void setLastCameraYValue(float lastCameraYValue) {
        this.lastCameraYValue = lastCameraYValue;
    }

    /**
     * Compares this entity with another entity based on their hitbox positions relative to the camera.
     *
     * @param other The other entity to compare against.
     * @return A negative integer, zero, or a positive integer as this entity is less than, equal to, or greater than the specified entity.
     */
    @Override
    public int compareTo(Entity other) {
        return Float.compare(hitbox.top - lastCameraYValue, other.hitbox.top - other.lastCameraYValue);
    }
}
