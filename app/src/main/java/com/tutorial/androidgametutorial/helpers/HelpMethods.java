package com.tutorial.androidgametutorial.helpers;

import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RectF;

import com.tutorial.androidgametutorial.entities.Building;
import com.tutorial.androidgametutorial.entities.Character;
import com.tutorial.androidgametutorial.entities.GameObject;
import com.tutorial.androidgametutorial.entities.Player;
import com.tutorial.androidgametutorial.entities.enemies.Skeleton;
import com.tutorial.androidgametutorial.environments.Doorway;
import com.tutorial.androidgametutorial.environments.GameMap;
import com.tutorial.androidgametutorial.environments.Tiles;


import java.util.ArrayList;

public class HelpMethods {

    /**
     * Creates a PointF representing the doorway location for a specified building
     * in the provided game map.
     *
     * @param gameMapLocatedIn The GameMap object containing the buildings.
     * @param buildingIndex The index of the building in the list.
     * @return A PointF representing the position of the doorway.
     */
    public static PointF CreatePointForDoorway(GameMap gameMapLocatedIn, int buildingIndex) {
        Building building = gameMapLocatedIn.getBuildingArrayList().get(buildingIndex);

        float x = building.getPos().x;
        float y = building.getPos().y;
        PointF point = gameMapLocatedIn.getBuildingArrayList().get(buildingIndex).getBuildingType().getDoorwayPoint();

        return new PointF(point.x + x, point.y + y - building.getBuildingType().getHitboxRoof());

    }

    /**
     * Creates a PointF based on tile coordinates for doorway placement.
     *
     * @param xTile The X-coordinate of the tile.
     * @param yTile The Y-coordinate of the tile.
     * @return A PointF representing the center of the specified tile.
     */
    public static PointF CreatePointForDoorway(int xTile, int yTile) {
//        float x = xTile * GameConstants.Sprite.SIZE;
//        float y = yTile * GameConstants.Sprite.SIZE;
//
//        return new PointF(x + 1, y + 1);

        float x = xTile * GameConstants.Sprite.SIZE + GameConstants.Sprite.SIZE / 2f;
        float y = yTile * GameConstants.Sprite.SIZE + GameConstants.Sprite.SIZE / 2f;

        return new PointF(x, y);
    }

    /**
     * Connects two doorways between two game maps.
     *
     * @param gameMapOne The first GameMap object.
     * @param pointOne The PointF representing the position of the first doorway.
     * @param gameMapTwo The second GameMap object.
     * @param pointTwo The PointF representing the position of the second doorway.
     */
    public static void ConnectTwoDoorways(GameMap gameMapOne, PointF pointOne, GameMap gameMapTwo, PointF pointTwo) {

        Doorway doorwayOne = new Doorway(pointOne, gameMapOne);
        Doorway doorwayTwo = new Doorway(pointTwo, gameMapTwo);

        doorwayOne.connectDoorway(doorwayTwo);
        doorwayTwo.connectDoorway(doorwayOne);
    }

    /**
     * Generates a list of random Skeleton enemies in the game map.
     *
     * @param amount The number of Skeletons to create.
     * @param gameMapArray A 2D array representing the game map layout.
     * @return An ArrayList of randomly positioned Skeletons.
     */
    public static ArrayList<Skeleton> GetSkeletonsRandomized(int amount, int[][] gameMapArray) {

        int width = (gameMapArray[0].length - 1) * GameConstants.Sprite.SIZE;
        int height = (gameMapArray.length - 1) * GameConstants.Sprite.SIZE;

        ArrayList<Skeleton> skeletonArrayList = new ArrayList<>();

        for (int i = 0; i < amount; i++) {
            float x = (float) (Math.random() * width - GameConstants.Sprite.SIZE);
            float y = (float) (Math.random() * height - GameConstants.Sprite.SIZE);
            skeletonArrayList.add(new Skeleton(new PointF(x, y)));
        }

        return skeletonArrayList;
    }

    /**
     * Adjusts the Y-coordinate of a hitbox to align with the nearest tile while moving vertically.
     *
     * @param hitbox The RectF representing the hitbox of the object.
     * @param cameraY The current Y-position of the camera.
     * @param deltaY The change in Y-position.
     * @return The adjusted Y-position after movement.
     */
    public static float MoveNextToTileUpDown(RectF hitbox, float cameraY, float deltaY) {
        int currentTile;
        int playerPosY;
        float cameraYReturn;

        if (deltaY > 0) {
            playerPosY = (int) (hitbox.top - cameraY);
            currentTile = playerPosY / GameConstants.Sprite.SIZE;
            cameraYReturn = hitbox.top - (currentTile * GameConstants.Sprite.SIZE);
        } else {
            playerPosY = (int) (hitbox.bottom - cameraY);
            currentTile = playerPosY / GameConstants.Sprite.SIZE;
            cameraYReturn = hitbox.bottom - (currentTile * GameConstants.Sprite.SIZE) - (GameConstants.Sprite.SIZE - 1);
        }

        return cameraYReturn;
    }

    /**
     * Adjusts the X-coordinate of a hitbox to align with the nearest tile while moving horizontally.
     *
     * @param hitbox The RectF representing the hitbox of the object.
     * @param cameraX The current X-position of the camera.
     * @param deltaX The change in X-position.
     * @return The adjusted X-position after movement.
     */
    public static float MoveNextToTileLeftRight(RectF hitbox, float cameraX, float deltaX) {
        int currentTile;
        int playerPosX;
        float cameraXReturn;

        if (deltaX > 0) {
            playerPosX = (int) (hitbox.left - cameraX);
            currentTile = playerPosX / GameConstants.Sprite.SIZE;
            cameraXReturn = hitbox.left - (currentTile * GameConstants.Sprite.SIZE);
        } else {
            playerPosX = (int) (hitbox.right - cameraX);
            currentTile = playerPosX / GameConstants.Sprite.SIZE;
            cameraXReturn = hitbox.right - (currentTile * GameConstants.Sprite.SIZE) - (GameConstants.Sprite.SIZE - 1);
        }

        return cameraXReturn;
    }

    /**
     * Checks if a given position is walkable in the game map.
     *
     * @param x The X-coordinate to check.
     * @param y The Y-coordinate to check.
     * @param gameMap The GameMap object to check against.
     * @return True if the position is walkable, false otherwise.
     */
    public static boolean CanWalkHere(float x, float y, GameMap gameMap) {
        if (x < 0 || y < 0)
            return false;

        if (x >= gameMap.getMapWidth() || y >= gameMap.getMapHeight())
            return false;

        int tileX = (int) (x / GameConstants.Sprite.SIZE);
        int tileY = (int) (y / GameConstants.Sprite.SIZE);

        int tileId = gameMap.getSpriteID(tileX, tileY);

        return IsTileWalkable(tileId, gameMap.getFloorType());
    }

    /**
     * Checks if a hitbox can move vertically without colliding with obstacles.
     *
     * @param hitbox The RectF representing the hitbox of the object.
     * @param deltaY The change in Y-position.
     * @param currentCameraX The current X-position of the camera.
     * @param gameMap The GameMap object to check against.
     * @return True if the movement is possible, false otherwise.
     */
    public static boolean CanWalkHereUpDown(RectF hitbox, float deltaY, float currentCameraX, GameMap gameMap) {
        if (hitbox.top + deltaY < 0) return false;
        else if (hitbox.bottom + deltaY >= gameMap.getMapHeight()) return false;

        if (gameMap.getGameObjectArrayList() != null) {
            RectF tempHitbox = new RectF(hitbox.left + currentCameraX, hitbox.top + deltaY, hitbox.right + currentCameraX, hitbox.bottom + deltaY);
            for (GameObject go : gameMap.getGameObjectArrayList()) {
                if (RectF.intersects(go.getHitbox(), tempHitbox))
                    return false;
            }
        }

        //Buildings
        if (gameMap.getBuildingArrayList() != null) {
            RectF tempHitbox = new RectF(hitbox.left + currentCameraX, hitbox.top + deltaY, hitbox.right + currentCameraX, hitbox.bottom + deltaY);
            for (Building b : gameMap.getBuildingArrayList())
                if (RectF.intersects(b.getHitbox(), tempHitbox))
                    return false;
        }

        Point[] tileCords = GetTileCords(hitbox, currentCameraX, deltaY);
        int[] tileIds = GetTileIds(tileCords, gameMap);

        return IsTilesWalkable(tileIds, gameMap.getFloorType());
    }


    /**
     * Checks if a hitbox can move horizontally without colliding with obstacles.
     *
     * @param hitbox The RectF representing the hitbox of the object.
     * @param deltaX The change in X-position.
     * @param currentCameraY The current Y-position of the camera.
     * @param gameMap The GameMap object to check against.
     * @return True if the movement is possible, false otherwise.
     */
    public static boolean CanWalkHereLeftRight(RectF hitbox, float deltaX, float currentCameraY, GameMap gameMap) {
        if (hitbox.left + deltaX < 0) return false;
        else if (hitbox.right + deltaX >= gameMap.getMapWidth()) return false;

        if (gameMap.getGameObjectArrayList() != null) {
            RectF tempHitbox = new RectF(hitbox.left + deltaX, hitbox.top + currentCameraY, hitbox.right + deltaX, hitbox.bottom + currentCameraY);
            for (GameObject go : gameMap.getGameObjectArrayList()) {
                if (RectF.intersects(go.getHitbox(), tempHitbox))
                    return false;
            }
        }

        //Buildings
        if (gameMap.getBuildingArrayList() != null) {
            RectF tempHitbox = new RectF(hitbox.left + deltaX, hitbox.top + currentCameraY, hitbox.right + deltaX, hitbox.bottom + currentCameraY);
            for (Building b : gameMap.getBuildingArrayList())
                if (RectF.intersects(b.getHitbox(), tempHitbox))
                    return false;
        }

        Point[] tileCords = GetTileCords(hitbox, deltaX, currentCameraY);
        int[] tileIds = GetTileIds(tileCords, gameMap);

        return IsTilesWalkable(tileIds, gameMap.getFloorType());
    }

    /**
     * Checks if a hitbox can move in both directions without colliding with obstacles.
     *
     * @param hitbox The RectF representing the hitbox of the object.
     * @param deltaX The change in X-position.
     * @param deltaY The change in Y-position.
     * @param gameMap The GameMap object to check against.
     * @return True if the movement is possible, false otherwise.
     */
    public static boolean CanWalkHere(RectF hitbox, float deltaX, float deltaY, GameMap gameMap) {
        if (hitbox.left + deltaX < 0 || hitbox.top + deltaY < 0) return false;
        else if (hitbox.right + deltaX >= gameMap.getMapWidth()) return false;
        else if (hitbox.bottom + deltaY >= gameMap.getMapHeight()) return false;

        //Objects
        if (gameMap.getGameObjectArrayList() != null) {
            RectF tempHitbox = new RectF(hitbox.left + deltaX, hitbox.top + deltaY, hitbox.right + deltaX, hitbox.bottom + deltaY);
            for (GameObject go : gameMap.getGameObjectArrayList()) {
                if (RectF.intersects(go.getHitbox(), tempHitbox))
                    return false;
            }
        }

        //Buildings
        if (gameMap.getBuildingArrayList() != null) {
            RectF tempHitbox = new RectF(hitbox.left + deltaX, hitbox.top + deltaY, hitbox.right + deltaX, hitbox.bottom + deltaY);
            for (Building b : gameMap.getBuildingArrayList())
                if (RectF.intersects(b.getHitbox(), tempHitbox))
                    return false;
        }

        Point[] tileCords = GetTileCords(hitbox, deltaX, deltaY);
        int[] tileIds = GetTileIds(tileCords, gameMap);

        return IsTilesWalkable(tileIds, gameMap.getFloorType());
    }


    /**
     * Retrieves the tile IDs for the specified coordinates.
     *
     * @param tileCords An array of Point objects representing tile coordinates.
     * @param gameMap The GameMap object to retrieve tile IDs from.
     * @return An array of tile IDs.
     */
    private static int[] GetTileIds(Point[] tileCords, GameMap gameMap) {
        int[] tileIds = new int[4];

        for (int i = 0; i < tileCords.length; i++)
            tileIds[i] = gameMap.getSpriteID(tileCords[i].x, tileCords[i].y);

        return tileIds;
    }

    /**
     * Retrieves the tile coordinates based on the hitbox and movement deltas.
     *
     * @param hitbox The RectF representing the hitbox of the object.
     * @param deltaX The change in X-position.
     * @param deltaY The change in Y-position.
     * @return An array of Point objects representing the tile coordinates.
     */
    private static Point[] GetTileCords(RectF hitbox, float deltaX, float deltaY) {
        Point[] tileCords = new Point[4];

        int left = (int) ((hitbox.left + deltaX) / GameConstants.Sprite.SIZE);
        int right = (int) ((hitbox.right + deltaX) / GameConstants.Sprite.SIZE);
        int top = (int) ((hitbox.top + deltaY) / GameConstants.Sprite.SIZE);
        int bottom = (int) ((hitbox.bottom + deltaY) / GameConstants.Sprite.SIZE);

        tileCords[0] = new Point(left, top);
        tileCords[1] = new Point(right, top);
        tileCords[2] = new Point(left, bottom);
        tileCords[3] = new Point(right, bottom);

        return tileCords;

    }

    /**
     * Checks if all specified tiles are walkable.
     *
     * @param tileIds An array of tile IDs to check.
     * @param tilesType The type of tiles to check against.
     * @return True if all tiles are walkable, false otherwise.
     */
    public static boolean IsTilesWalkable(int[] tileIds, Tiles tilesType) {
        for (int i : tileIds)
            if (!(IsTileWalkable(i, tilesType)))
                return false;
        return true;
    }

    /**
     * Determines if a specific tile is walkable based on its ID and type.
     *
     * @param tileId The ID of the tile to check.
     * @param tilesType The type of tiles to check against.
     * @return True if the tile is walkable, false otherwise.
     */
    public static boolean IsTileWalkable(int tileId, Tiles tilesType) {
        if (tilesType == Tiles.INSIDE)
            return (tileId == 394 || tileId < 374);

        return true;
    }


    /**
     * Checks if a character is close enough to the player to initiate an attack.
     *
     * @param character The Character object to check against.
     * @param player The Player object.
     * @param cameraY The current Y-position of the camera.
     * @param cameraX The current X-position of the camera.
     * @return True if the character is close enough for an attack, false otherwise.
     */
    public static boolean IsPlayerCloseForAttack(Character character, Player player, float cameraY, float cameraX) {
        float xDelta = character.getHitbox().left - (player.getHitbox().left - cameraX);
        float yDelta = character.getHitbox().top - (player.getHitbox().top - cameraY);

        float distance = (float) Math.hypot(xDelta, yDelta);

        return distance < GameConstants.Sprite.SIZE * 1.5f;
    }
}
