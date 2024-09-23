package com.tutorial.androidgametutorial.environments;


import com.tutorial.androidgametutorial.entities.Building;
import com.tutorial.androidgametutorial.entities.Entity;
import com.tutorial.androidgametutorial.entities.GameObject;
import com.tutorial.androidgametutorial.entities.enemies.Skeleton;
import com.tutorial.androidgametutorial.helpers.GameConstants;


import java.util.ArrayList;

public class GameMap {

    private int[][] spriteIds;
    private Tiles tilesType;
    private ArrayList<Building> buildingArrayList;
    private ArrayList<Doorway> doorwayArrayList;
    private ArrayList<GameObject> gameObjectArrayList;
    private ArrayList<Skeleton> skeletonArrayList;

    /**
     * Constructs a GameMap with specified sprite IDs, tile types, and various game entities.
     *
     * @param spriteIds A 2D array representing the sprite IDs for the map.
     * @param tilesType The type of tiles used for the floor.
     * @param buildingArrayList A list of buildings on the map.
     * @param gameObjectArrayList A list of game objects on the map.
     * @param skeletonArrayList A list of skeleton enemies on the map.
     */
    public GameMap(int[][] spriteIds, Tiles tilesType, ArrayList<Building> buildingArrayList, ArrayList<GameObject> gameObjectArrayList, ArrayList<Skeleton> skeletonArrayList) {
        this.spriteIds = spriteIds;
        this.tilesType = tilesType;
        this.buildingArrayList = buildingArrayList;
        this.gameObjectArrayList = gameObjectArrayList;
        this.skeletonArrayList = skeletonArrayList;
        this.doorwayArrayList = new ArrayList<>();
    }

    /**
     * Retrieves an array of drawable entities (buildings, skeletons, game objects) present on the map.
     *
     * @return An array of drawable entities.
     */
    public Entity[] getDrawableList() {
        Entity[] list = new Entity[getDrawableAmount()];
        int i = 0;

        if (buildingArrayList != null)
            for (Building b : buildingArrayList)
                list[i++] = b;
        if (skeletonArrayList != null)
            for (Skeleton s : skeletonArrayList)
                list[i++] = s;
        if (gameObjectArrayList != null)
            for (GameObject go : gameObjectArrayList)
                list[i++] = go;

        return list;
    }

    /**
     * Calculates the total number of drawable entities on the map.
     *
     * @return The total number of drawable entities.
     */
    private int getDrawableAmount() {
        int amount = 0;
        if (buildingArrayList != null)
            amount += buildingArrayList.size();
        if (gameObjectArrayList != null)
            amount += gameObjectArrayList.size();
        if (skeletonArrayList != null)
            amount += skeletonArrayList.size();
        amount++; //Player

        return amount;
    }

    /**
     * Adds a doorway to the map.
     *
     * @param doorway The doorway to be added.
     */
    public void addDoorway(Doorway doorway) {
        this.doorwayArrayList.add(doorway);
    }

    /**
     * Retrieves the list of doorways present on the map.
     *
     * @return An ArrayList of Doorway objects.
     */
    public ArrayList<Doorway> getDoorwayArrayList() {
        return doorwayArrayList;
    }

    /**
     * Retrieves the list of buildings present on the map.
     *
     * @return An ArrayList of Building objects.
     */
    public ArrayList<Building> getBuildingArrayList() {
        return buildingArrayList;
    }

    /**
     * Retrieves the list of game objects present on the map.
     *
     * @return An ArrayList of GameObject objects.
     */
    public ArrayList<GameObject> getGameObjectArrayList() {
        return gameObjectArrayList;
    }

    /**
     * Retrieves the list of skeletons present on the map.
     *
     * @return An ArrayList of Skeleton objects.
     */
    public ArrayList<Skeleton> getSkeletonArrayList() {
        return skeletonArrayList;
    }

    /**
     * Retrieves the type of floor tiles used in the game map.
     *
     * @return The floor tile type.
     */
    public Tiles getFloorType() {
        return tilesType;
    }

    /**
     * Retrieves the sprite ID at a specific map coordinate.
     *
     * @param xIndex The x-coordinate of the sprite.
     * @param yIndex The y-coordinate of the sprite.
     * @return The sprite ID at the given coordinate.
     */
    public int getSpriteID(int xIndex, int yIndex) {
        return spriteIds[yIndex][xIndex];
    }

    /**
     * Retrieves the width of the sprite array.
     *
     * @return The width of the sprite array.
     */
    public int getArrayWidth() {
        return spriteIds[0].length;
    }

    /**
     * Retrieves the height of the sprite array.
     *
     * @return The height of the sprite array.
     */
    public int getArrayHeight() {
        return spriteIds.length;
    }

    /**
     * Retrieves the total width of the game map in pixels.
     *
     * @return The map width in pixels.
     */
    public int getMapWidth() {
        return getArrayWidth() * GameConstants.Sprite.SIZE;
    }

    /**
     * Retrieves the total height of the game map in pixels.
     *
     * @return The map height in pixels.
     */
    public int getMapHeight() {
        return getArrayHeight() * GameConstants.Sprite.SIZE;
    }

}
