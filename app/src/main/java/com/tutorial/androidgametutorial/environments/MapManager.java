package com.tutorial.androidgametutorial.environments;

import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.RectF;

import com.tutorial.androidgametutorial.entities.Building;
import com.tutorial.androidgametutorial.entities.Buildings;
import com.tutorial.androidgametutorial.entities.GameObject;
import com.tutorial.androidgametutorial.entities.GameObjects;
import com.tutorial.androidgametutorial.gamestates.Playing;
import com.tutorial.androidgametutorial.helpers.GameConstants;
import com.tutorial.androidgametutorial.helpers.HelpMethods;
import com.tutorial.androidgametutorial.main.MainActivity;

import java.util.ArrayList;

public class MapManager {

    private GameMap currentMap;
    private float cameraX, cameraY;
    private Playing playing;

    /**
     * Constructs a MapManager to manage the game's maps and camera.
     *
     * @param playing The Playing state of the game.
     */
    public MapManager(Playing playing) {
        this.playing = playing;
        initTestMap();
    }

    /**
     * Sets the camera's position in the game world.
     *
     * @param cameraX The X coordinate of the camera.
     * @param cameraY The Y coordinate of the camera.
     */
    public void setCameraValues(float cameraX, float cameraY) {
        this.cameraX = cameraX;
        this.cameraY = cameraY;
    }

    /**
     * Checks if the specified coordinates are within the bounds of the current map.
     *
     * @param x The X coordinate to check.
     * @param y The Y coordinate to check.
     * @return True if movement is possible, otherwise false.
     */
    public boolean canMoveHere(float x, float y) {
        if (x < 0 || y < 0)
            return false;

        if (x >= getMaxWidthCurrentMap() || y >= getMaxHeightCurrentMap())
            return false;

        return true;
    }

    /**
     * Retrieves the maximum width of the current map based on its sprite array dimensions.
     *
     * @return The maximum width of the current map.
     */
    public int getMaxWidthCurrentMap() {
        return currentMap.getArrayWidth() * GameConstants.Sprite.SIZE;
    }

    /**
     * Retrieves the maximum height of the current map based on its sprite array dimensions.
     *
     * @return The maximum height of the current map.
     */
    public int getMaxHeightCurrentMap() {
        return currentMap.getArrayHeight() * GameConstants.Sprite.SIZE;
    }

    /**
     * Draws a game object on the specified canvas.
     *
     * @param c The Canvas on which to draw the object.
     * @param go The GameObject to be drawn.
     */
    public void drawObject(Canvas c, GameObject go) {
        c.drawBitmap(go.getObjectType().getObjectImg(), go.getHitbox().left + cameraX, go.getHitbox().top - go.getObjectType().getHitboxRoof() + cameraY, null);
    }

    /**
     * Draws a building on the specified canvas.
     *
     * @param c The Canvas on which to draw the building.
     * @param b The Building to be drawn.
     */
    public void drawBuilding(Canvas c, Building b) {
        c.drawBitmap(b.getBuildingType().getHouseImg(), b.getPos().x + cameraX, b.getPos().y - b.getBuildingType().getHitboxRoof() + cameraY, null);
    }

    /**
     * Draws the floor tiles of the current map on the specified canvas.
     *
     * @param c The Canvas on which to draw the tiles.
     */
    public void drawTiles(Canvas c) {
        for (int j = 0; j < currentMap.getArrayHeight(); j++)
            for (int i = 0; i < currentMap.getArrayWidth(); i++)
                c.drawBitmap(currentMap.getFloorType().getSprite(currentMap.getSpriteID(i, j)), i * GameConstants.Sprite.SIZE + cameraX, j * GameConstants.Sprite.SIZE + cameraY, null);
    }

    /**
     * Checks if the player is currently inside a doorway.
     *
     * @param playerHitbox The hitbox of the player.
     * @return The Doorway if the player is inside, otherwise null.
     */
    public Doorway isPlayerOnDoorway(RectF playerHitbox) {
        for (Doorway doorway : currentMap.getDoorwayArrayList())
            if (doorway.isPlayerInsideDoorway(playerHitbox, cameraX, cameraY))
                return doorway;

        return null;
    }

    /**
     * Changes the current map to the one associated with the specified doorway.
     *
     * @param doorwayTarget The Doorway leading to the new map.
     */
    public void changeMap(Doorway doorwayTarget) {
        this.currentMap = doorwayTarget.getGameMapLocatedIn();

        float cX = MainActivity.GAME_WIDTH / 2f - doorwayTarget.getPosOfDoorway().x + GameConstants.Sprite.HITBOX_SIZE / 2f;
        float cY = MainActivity.GAME_HEIGHT / 2f - doorwayTarget.getPosOfDoorway().y + GameConstants.Sprite.HITBOX_SIZE / 2f;

        playing.setCameraValues(new PointF(cX, cY));
        cameraX = cX;
        cameraY = cY;

        playing.setDoorwayJustPassed(true);
    }

    /**
     * Retrieves the current game map.
     *
     * @return The current GameMap object.
     */
    public GameMap getCurrentMap() {
        return currentMap;
    }

    /**
     * Initializes a test map with predefined tile layouts and entities.
     */
    private void initTestMap() {
        int[][] outsideArray = Stages.STAGE_ONE.getOutsideArray();

        ArrayList<int[][]> insideMaps = Stages.STAGE_ONE.getInsideMaps();
        int[][] insideArray = insideMaps.get(0); // Assuming the first map is the inside array
        int[][] insideFlatHouseArray = insideMaps.get(1); // Assuming the second map is the inside flat house array
        int[][] insideGreenRoofHouseArr = insideMaps.get(2); // Assuming the third map is the inside green roof house array

        ArrayList<Building> buildingArrayList = Stages.STAGE_ONE.getBuildings();
        ArrayList<GameObject> gameObjectArrayList = Stages.STAGE_ONE.getGameObjects();

        int NumberOfEnemyOutside = Stages.STAGE_ONE.getNumOfOutsideEnemy();
        int NumberOfEnemyInside = Stages.STAGE_ONE.getNumOfInsideEnemy();
        GameMap insideMap = new GameMap(insideArray, Tiles.INSIDE, null, null, HelpMethods.GetSkeletonsRandomized(NumberOfEnemyInside, insideArray));
        GameMap insideFlatRoofHouseMap = new GameMap(insideFlatHouseArray, Tiles.INSIDE, null, null, null);
        GameMap insideGreenRoofHouseMap = new GameMap(insideGreenRoofHouseArr, Tiles.INSIDE, null, null, null);

        GameMap outsideMap = new GameMap(outsideArray, Tiles.OUTSIDE, buildingArrayList, gameObjectArrayList, HelpMethods.GetSkeletonsRandomized(NumberOfEnemyOutside, outsideArray));

        HelpMethods.ConnectTwoDoorways(
                outsideMap,
                HelpMethods.CreatePointForDoorway(outsideMap, 0),
                insideMap,
                HelpMethods.CreatePointForDoorway(3, 6));

        HelpMethods.ConnectTwoDoorways(
                outsideMap,
                HelpMethods.CreatePointForDoorway(outsideMap, 1),
                insideFlatRoofHouseMap,
                HelpMethods.CreatePointForDoorway(3, 6));

        HelpMethods.ConnectTwoDoorways(
                outsideMap,
                HelpMethods.CreatePointForDoorway(outsideMap, 2),
                insideGreenRoofHouseMap,
                HelpMethods.CreatePointForDoorway(3, 6));

        currentMap = outsideMap;
    }

}
