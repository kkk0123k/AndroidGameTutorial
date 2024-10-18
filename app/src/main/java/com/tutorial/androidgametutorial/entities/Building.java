package com.tutorial.androidgametutorial.entities;

import android.graphics.PointF;

public class Building extends Entity {

    private final Buildings buildingType;

    /**
     * Constructs a Building entity with a specified position and building type.
     *
     * @param pos The position of the building in the game world.
     * @param buildingType The type of the building, which includes its dimensions and properties.
     */
    public Building(PointF pos, Buildings buildingType) {
        // Calls the Entity constructor to define the hitbox position and size based on the building type.
        super(new PointF(pos.x, pos.y + buildingType.hitboxRoof),
                buildingType.hitboxWidth,
                buildingType.hitboxHeight
        );
        this.buildingType = buildingType; // Sets the building type.
    }

    /**
     * Returns the type of the building.
     *
     * @return The buildingType of the building.
     */
    public Buildings getBuildingType() {
        return buildingType;
    }

    /**
     * Returns the position of the building.
     *
     * @return A PointF representing the top-left corner of the building's hitbox.
     */
    public PointF getPos() {
        return new PointF(hitbox.left, hitbox.top); // Returns the current position of the building.
    }
}
