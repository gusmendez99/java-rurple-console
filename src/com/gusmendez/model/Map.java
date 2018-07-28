package com.gusmendez.model;

import java.util.ArrayList;
import java.util.List;

public class Map {
    public final static String MOVE = "MOVE";
    public final static String ROTATE = "ROTATE";
    public final static String PICK = "PICK";

    private int width;
    private int height;
    private Robot robot;
    private List<Wall> walls;
    private List<PileCoin> pileCoins;

    public Map(){
        this.walls = new ArrayList<>();
        this.pileCoins = new ArrayList<>();
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Robot getRobot() {
        return this.robot;
    }

    public void setRobot(Robot robot) {
        this.robot = robot;
    }

    public List<Wall> getWalls() {
        return this.walls;
    }

    public void addWall(Wall wall) {
        this.walls.add(wall);
    }

    public List<PileCoin> getPileCoins() {
        return this.pileCoins;
    }

    public void addPileCoins(PileCoin pileCoin) {
        this.pileCoins.add(pileCoin);
    }

    public boolean hasRobotPickAllCoins() {
        return this.pileCoins.isEmpty();
    }

    public boolean placeInstruction(String instruction){
        switch(instruction){
            case MOVE:

                return true;
            case ROTATE:

                return true;
            case PICK:

                return true;
        }
        return false;
    }



    public static boolean isValidInstruction(String line) {
        return line.equals(MOVE) || line.equals(ROTATE) || line.equals(PICK);
    }

}