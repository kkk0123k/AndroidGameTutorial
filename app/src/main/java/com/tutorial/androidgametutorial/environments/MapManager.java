package com.tutorial.androidgametutorial.environments;

import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.RectF;

import com.tutorial.androidgametutorial.entities.Building;
import com.tutorial.androidgametutorial.entities.GameObject;
import com.tutorial.androidgametutorial.gamestates.Playing;
import com.tutorial.androidgametutorial.helpers.GameConstants;
import com.tutorial.androidgametutorial.helpers.HelpMethods;
import com.tutorial.androidgametutorial.main.MainActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class MapManager {

    private GameMap currentMap;
    private float cameraX, cameraY;
    private final Playing playing;
    private final String progression;
    /**
     * Constructs a MapManager to manage the game's maps and camera.
     *
     * @param playing The Playing state of the game.
     */
    public MapManager(Playing playing, String progression) {
        this.playing = playing;
        this.progression = progression;
        initStage(progression);
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

        return !(x >= getMaxWidthCurrentMap()) && !(y >= getMaxHeightCurrentMap());
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
    private void initStage(String progression) {
        Stages stage = Stages.valueOf(progression); // Get the stage based on progression

        int[][] outsideArray = stage.getOutsideArray();
        ArrayList<int[][]> insideMaps = stage.getInsideMaps();
        ArrayList<Building> buildingArrayList = stage.getBuildings();
        ArrayList<GameObject> gameObjectArrayList = stage.getGameObjects();

        int NumberOfEnemyOutside = stage.getNumOfOutsideEnemy();
        int NumberOfEnemyInside = stage.getNumOfInsideEnemy();

        // Check if there are inside maps
        if (insideMaps == null || insideMaps.isEmpty()) {
            // If no inside maps, clear the building list
            buildingArrayList.clear();
        }

        // Create the outside map with the associated buildings, game objects, and enemies
        GameMap outsideMap = new GameMap(outsideArray, Tiles.OUTSIDE,
                buildingArrayList.isEmpty() ? null : buildingArrayList,
                gameObjectArrayList,
                HelpMethods.GetSkeletonsRandomized(NumberOfEnemyOutside, outsideArray));

        // Check if inside maps are available
        if (insideMaps != null && !insideMaps.isEmpty()) {
            Random random = new Random();

            // Create a list to hold the inside maps
            ArrayList<GameMap> insideGameMaps = new ArrayList<>();
            int totalEnemiesAssigned = 0; // Track total enemies assigned

            // Calculate the average number of enemies per map
            int averageEnemiesPerMap = NumberOfEnemyInside / insideMaps.size();
            int remainingEnemies = NumberOfEnemyInside % insideMaps.size();

            // Create an array to hold the number of enemies for each map
            int[] enemiesPerMap = new int[insideMaps.size()];
            Arrays.fill(enemiesPerMap, averageEnemiesPerMap);

            // Randomly distribute the remaining enemies
            for (int i = 0; i < remainingEnemies; i++) {
                enemiesPerMap[random.nextInt(insideMaps.size())]++;
            }

            // Loop through each inside map and create a GameMap for each
            for (int i = 0; i < insideMaps.size(); i++) {
                int[][] currentInsideArray = insideMaps.get(i);
                int numEnemies = enemiesPerMap[i];

                totalEnemiesAssigned += numEnemies;

                // Create a new GameMap for each inside map
                GameMap insideMap = new GameMap(
                        currentInsideArray,
                        Tiles.INSIDE,
                        null,
                        null,
                        HelpMethods.GetSkeletonsRandomized(numEnemies, currentInsideArray)
                );

                // Add the inside map to the list of inside maps
                insideGameMaps.add(insideMap);

                // Connect the current inside map to the outside map
                HelpMethods.ConnectTwoDoorways(
                        outsideMap,
                        HelpMethods.CreatePointForDoorway(outsideMap, i),
                        insideMap,
                        HelpMethods.CreatePointForDoorway(3, 6)
                );
            }
        }

        // Set the current map to the outside map
        currentMap = outsideMap;
    }

}
