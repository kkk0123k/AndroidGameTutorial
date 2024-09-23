package com.tutorial.androidgametutorial.entities;

import android.graphics.PointF;

public class GameObject extends Entity {
    private GameObjects objectType;

    /**
     * Constructs a GameObject with a specified position and object type.
     *
     * @param pos The position of the game object in the game world.
     * @param objectType The type of the game object.
     */
    public GameObject(PointF pos, GameObjects objectType) {
        super(new PointF(pos.x,pos.y + objectType.hitboxRoof),
                objectType.getHitboxWidth(),
                objectType.getHitboxHeight()
        );
        this.objectType = objectType;
    }

    /**
     * Retrieves the type of the game object.
     *
     * @return The GameObjects enum representing the type of the game object.
     */
    public GameObjects getObjectType() {
        return objectType;
    }
}
