package com.tutorial.androidgametutorial.entities;

import android.graphics.Canvas;
import android.graphics.PointF;

import java.util.ArrayList;

public class BuildingManager {

    private ArrayList<Building> buildingArrayList; // List to store buildings managed by this manager.
    private float cameraX, cameraY; // Camera offset values for rendering.

    /**
     * Constructs a BuildingManager and initializes the building list.
     * By default, adds a HOUSE_ONE building at a specific position.
     */
    public BuildingManager() {
        buildingArrayList = new ArrayList<>();
        buildingArrayList.add(new Building(new PointF(200, 200), Buildings.HOUSE_ONE)); // Adding a default building.
    }

    /**
     * Sets the camera's current X and Y values for rendering.
     *
     * @param cameraX The current X position of the camera.
     * @param cameraY The current Y position of the camera.
     */
    public void setCameraValues(float cameraX, float cameraY) {
        this.cameraX = cameraX; // Updates the camera's X position.
        this.cameraY = cameraY; // Updates the camera's Y position.
    }

    /**
     * Draws all buildings in the buildingArrayList onto the specified Canvas.
     *
     * @param c The Canvas on which to draw the buildings.
     */
    public void draw(Canvas c) {
        // Iterates through each building and draws it at its position adjusted for camera offsets.
        for (Building b : buildingArrayList)
            c.drawBitmap(b.getBuildingType().getHouseImg(), b.getPos().x + cameraX, b.getPos().y + cameraY, null);
    }
}
