package com.tutorial.androidgametutorial.environments;

import android.graphics.PointF;
import android.graphics.RectF;

public class Doorway {


    private boolean active = true;
    private final GameMap gameMapLocatedIn;
    private Doorway doorwayConnectedTo;
    private PointF doorwayPoint;

    /**
     * Constructs a Doorway with a specified location and associated game map.
     *
     * @param doorwayPoint The position of the doorway in the game world.
     * @param gameMapLocatedIn The game map where the doorway is located.
     */
    public Doorway(PointF doorwayPoint, GameMap gameMapLocatedIn) {
        this.doorwayPoint = doorwayPoint;
        this.gameMapLocatedIn = gameMapLocatedIn;
        gameMapLocatedIn.addDoorway(this);
    }

    /**
     * Connects this doorway to another doorway, establishing a link between the two.
     *
     * @param destinationDoorway The doorway to connect to.
     */
    public void connectDoorway(Doorway destinationDoorway) {
        this.doorwayConnectedTo = destinationDoorway;
    }

    /**
     * Retrieves the doorway that is connected to this one, if any.
     *
     * @return The connected doorway or null if no connection exists.
     */
    public Doorway getDoorwayConnectedTo() {
        if (doorwayConnectedTo != null)
            return doorwayConnectedTo;
        return null;
    }

    /**
     * Checks if the player is inside the doorway based on their hitbox and camera position.
     *
     * @param playerHitbox The player's hitbox.
     * @param cameraX The X position of the camera.
     * @param cameraY The Y position of the camera.
     * @return True if the player is inside the doorway, false otherwise.
     */
    public boolean isPlayerInsideDoorway(RectF playerHitbox, float cameraX, float cameraY) {
        return playerHitbox.contains(doorwayPoint.x + cameraX, doorwayPoint.y + cameraY);
    }

    /**
     * Determines if the doorway is currently active.
     *
     * @return True if the doorway is active, false otherwise.
     */
    public boolean isDoorwayActive() {
        return active;
    }

    /**
     * Sets the active state of the doorway.
     *
     * @param active The new active state of the doorway.
     */
    public void setDoorwayActive(boolean active) {
        this.active = active;
    }

    /**
     * Retrieves the position of the doorway.
     *
     * @return The position of the doorway as a PointF.
     */
    public PointF getPosOfDoorway() {
        return doorwayPoint;
    }

    /**
     * Retrieves the game map that this doorway is located in.
     *
     * @return The game map associated with this doorway.
     */
    public GameMap getGameMapLocatedIn() {
        return gameMapLocatedIn;
    }
}
