package com.tutorial.androidgametutorial.environments;

import android.graphics.PointF;

import com.tutorial.androidgametutorial.entities.Building;
import com.tutorial.androidgametutorial.entities.GameObject;
import com.tutorial.androidgametutorial.entities.Buildings;
import com.tutorial.androidgametutorial.entities.GameObjects;

import java.util.ArrayList;

public enum Stages {

    STAGE_ONE(
            new int[][]{
                    {188, 189, 279, 275, 187, 189, 279, 275, 279, 276, 275, 279, 275, 275, 279, 275, 278, 276, 275, 278, 275, 279, 275},
                    {188, 189, 275, 279, 187, 189, 276, 275, 279, 275, 277, 275, 275, 277, 276, 275, 279, 278, 278, 275, 275, 279, 275},
                    {188, 189, 275, 276, 187, 189, 276, 279, 275, 278, 279, 279, 275, 275, 278, 278, 275, 275, 275, 276, 275, 279, 275},
                    {254, 189, 275, 279, 187, 214, 166, 166, 166, 166, 166, 166, 166, 167, 275, 276, 275, 276, 279, 277, 275, 279, 275},
                    {188, 189, 275, 275, 209, 210, 210, 210, 210, 195, 210, 210, 193, 189, 275, 277, 168, 275, 278, 275, 275, 276, 275},
                    {188, 189, 279, 276, 279, 275, 276, 275, 277, 190, 275, 279, 187, 189, 275, 279, 190, 275, 279, 275, 275, 279, 275},
                    {188, 189, 275, 275, 275, 279, 278, 275, 275, 190, 276, 277, 187, 258, 232, 232, 239, 232, 232, 232, 232, 233, 275},
                    {188, 189, 275, 279, 275, 275, 231, 232, 232, 238, 275, 275, 187, 189, 275, 275, 275, 275, 275, 275, 275, 275, 275},
                    {188, 189, 276, 279, 278, 275, 276, 275, 275, 275, 275, 276, 187, 189, 276, 275, 277, 275, 279, 275, 279, 275, 276},
                    {188, 189, 275, 275, 279, 275, 279, 275, 276, 275, 275, 277, 187, 189, 279, 275, 275, 275, 275, 275, 275, 275, 275},
                    {188, 214, 167, 276, 275, 277, 275, 275, 278, 275, 276, 275, 187, 189, 275, 275, 278, 275, 275, 276, 275, 277, 275},
                    {254, 188, 214, 167, 275, 278, 275, 275, 275, 275, 279, 275, 187, 189, 275, 275, 275, 168, 275, 275, 275, 275, 278},
                    {188, 188, 188, 214, 167, 279, 275, 277, 275, 277, 276, 275, 187, 258, 232, 232, 232, 238, 275, 279, 275, 275, 279},
                    {188, 188, 188, 253, 214, 167, 275, 277, 168, 275, 275, 275, 187, 189, 275, 275, 275, 275, 275, 279, 275, 275, 275},
                    {253, 188, 188, 188, 256, 214, 167, 275, 235, 232, 232, 232, 259, 189, 279, 275, 275, 277, 275, 275, 275, 279, 275},
                    {188, 188, 188, 254, 188, 256, 214, 167, 275, 275, 277, 275, 187, 189, 275, 278, 275, 275, 279, 275, 279, 278, 275}
            },
            new ArrayList<>() ,
            new ArrayList<>() ,
            new ArrayList<>() {{
                add(new GameObject(new PointF(190, 70), GameObjects.STATUE_ANGRY_YELLOW));
                add(new GameObject(new PointF(580, 70), GameObjects.STATUE_ANGRY_YELLOW));
                add(new GameObject(new PointF(1000, 550), GameObjects.BASKET_FULL_RED_FRUIT));
                add(new GameObject(new PointF(620, 520), GameObjects.OVEN_SNOW_YELLOW));
            }},
            5,
            0,
            "STAGE_TWO"
    ),
    STAGE_TWO(
            new int[][]{
                    {188, 189, 279, 275, 187, 189, 279, 275, 279, 276, 275, 279, 275, 275, 279, 275, 278, 276, 275, 278, 275, 279, 275},
                    {188, 189, 275, 279, 187, 189, 276, 275, 279, 275, 277, 275, 275, 277, 276, 275, 279, 278, 278, 275, 275, 279, 275},
                    {188, 189, 275, 276, 187, 189, 276, 279, 275, 278, 279, 279, 275, 275, 278, 278, 275, 275, 275, 276, 275, 279, 275},
                    {254, 189, 275, 279, 187, 214, 166, 166, 166, 166, 166, 166, 166, 167, 275, 276, 275, 276, 279, 277, 275, 279, 275},
                    {188, 189, 275, 275, 209, 210, 210, 210, 210, 195, 210, 210, 193, 189, 275, 277, 168, 275, 278, 275, 275, 276, 275},
                    {188, 189, 279, 276, 279, 275, 276, 275, 277, 190, 275, 279, 187, 189, 275, 279, 190, 275, 279, 275, 275, 279, 275},
                    {188, 189, 275, 275, 275, 279, 278, 275, 275, 190, 276, 277, 187, 258, 232, 232, 239, 232, 232, 232, 232, 233, 275},
                    {188, 189, 275, 279, 275, 275, 231, 232, 232, 238, 275, 275, 187, 189, 275, 275, 275, 275, 275, 275, 275, 275, 275},
                    {188, 189, 276, 279, 278, 275, 276, 275, 275, 275, 275, 276, 187, 189, 276, 275, 277, 275, 279, 275, 279, 275, 276},
                    {188, 189, 275, 275, 279, 275, 279, 275, 276, 275, 275, 277, 187, 189, 279, 275, 275, 275, 275, 275, 275, 275, 275},
                    {188, 214, 167, 276, 275, 277, 275, 275, 278, 275, 276, 275, 187, 189, 275, 275, 278, 275, 275, 276, 275, 277, 275},
                    {254, 188, 214, 167, 275, 278, 275, 275, 275, 275, 279, 275, 187, 189, 275, 275, 275, 168, 275, 275, 275, 275, 278},
                    {188, 188, 188, 214, 167, 279, 275, 277, 275, 277, 276, 275, 187, 258, 232, 232, 232, 238, 275, 279, 275, 275, 279},
                    {188, 188, 188, 253, 214, 167, 275, 277, 168, 275, 275, 275, 187, 189, 275, 275, 275, 275, 275, 279, 275, 275, 275},
                    {253, 188, 188, 188, 256, 214, 167, 275, 235, 232, 232, 232, 259, 189, 279, 275, 275, 277, 275, 275, 275, 279, 275},
                    {188, 188, 188, 254, 188, 256, 214, 167, 275, 275, 277, 275, 187, 189, 275, 278, 275, 275, 279, 275, 279, 278, 275}
            },
            new ArrayList<>() {{
                add(new int[][]{
                        {374, 377, 377, 377, 377, 377, 378},
                        {396, 0, 1, 1, 1, 2, 400},
                        {396, 22, 23, 23, 23, 24, 400},
                        {396, 22, 23, 23, 23, 24, 400},
                        {396, 22, 23, 23, 23, 24, 400},
                        {396, 44, 45, 45, 45, 46, 400},
                        {462, 465, 463, 394, 464, 465, 466}
                });
                add(new int[][]{  // Add another inside map
                        {389, 392, 392, 392, 392, 392, 393},
                        {411, 143, 144, 144, 144, 145, 415},
                        {411, 165, 166, 166, 166, 167, 415},
                        {411, 165, 166, 166, 166, 167, 415},
                        {411, 165, 166, 166, 166, 167, 415},
                        {411, 187, 188, 188, 188, 189, 415},
                        {477, 480, 478, 394, 479, 480, 481}
                });
                add(new int[][]{  // Add another inside map
                        {384, 387, 387, 387, 387, 387, 388},
                        {406, 298, 298, 298, 298, 298, 410},
                        {406, 298, 298, 298, 298, 298, 410},
                        {406, 298, 298, 298, 298, 298, 410},
                        {406, 298, 298, 298, 298, 298, 410},
                        {406, 298, 298, 298, 298, 298, 410},
                        {472, 475, 473, 394, 474, 475, 476}
                });
            }},
            new ArrayList<>() {{
                add(new Building(new PointF(1440, 160), Buildings.HOUSE_ONE));
                add(new Building(new PointF(1540, 880), Buildings.HOUSE_TWO));
                add(new Building(new PointF(575, 1000), Buildings.HOUSE_SIX));
            }},
            new ArrayList<>() {{
                add(new GameObject(new PointF(190, 70), GameObjects.STATUE_ANGRY_YELLOW));
                add(new GameObject(new PointF(580, 70), GameObjects.STATUE_ANGRY_YELLOW));
                add(new GameObject(new PointF(1000, 550), GameObjects.BASKET_FULL_RED_FRUIT));
                add(new GameObject(new PointF(620, 520), GameObjects.OVEN_SNOW_YELLOW));
            }},
            5,
            5,
            "STAGE_THREE"
    );


    private final int[][] outsideArray;
    private final ArrayList<int[][]> insideMaps;
    private final ArrayList<Building> buildings;
    private final ArrayList<GameObject> gameObjects;
    int TotalNumOfEnemy, NumOfOutsideEnemy, NumOfInsideEnemy;
    String nextStage;

    // Constructor
    Stages(int[][] outsideArray, ArrayList<int[][]> insideMaps, ArrayList<Building> buildings, ArrayList<GameObject> gameObjects, int NumOfOutsideEnemy, int NumOfInsideEnemy, String nextStage) {
        this.outsideArray = outsideArray;
        this.insideMaps = insideMaps;
        this.buildings = buildings;
        this.gameObjects = gameObjects;
        this.NumOfOutsideEnemy = NumOfOutsideEnemy;
        this.NumOfInsideEnemy = NumOfInsideEnemy;
        this.TotalNumOfEnemy = NumOfInsideEnemy + NumOfOutsideEnemy;
        this.nextStage = nextStage;
    }

    // Getters
    public int[][] getOutsideArray() {
        return outsideArray;
    }

    public ArrayList<int[][]> getInsideMaps() {
        return insideMaps;
    }

    public ArrayList<Building> getBuildings() {
        return buildings;
    }

    public ArrayList<GameObject> getGameObjects() {
        return gameObjects;
    }

    public int getNumOfOutsideEnemy() {
        return NumOfOutsideEnemy;
    }

    public int getNumOfInsideEnemy() {
        return NumOfInsideEnemy;
    }

    public int getTotalNumOfEnemy() {
        return TotalNumOfEnemy;
    }

    public String getNextStage() {
        return nextStage;
    }

}


