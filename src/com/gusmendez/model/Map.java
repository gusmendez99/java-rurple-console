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
                if(canRobotMove()){
                    this.robot.move();
                    return false;
                }
            case ROTATE:
                this.robot.rotate();
                return false;
            case PICK:
                if(canRobotPickCoins()){
                    robot.incrementCarryCoins(1);
                } else {
                    System.out.println("No hay monedas por recoger en esta posicion...");
                }
                return false;
        }
        return true;
    }

    private boolean canRobotMove() {
        //Set the possibilities to move for the robot
        int desiredRow = this.robot.getRow();
        int desiredColumn = this.robot.getColumn();
        switch(this.robot.getDirection()){
            case Robot.LOOKING_UP:
                desiredColumn = this.robot.getColumn() - 1;
            case Robot.LOOKING_RIGHT:
                desiredRow = this.robot.getRow() + 1;
            case Robot.LOOKING_DOWN:
                desiredColumn = this.robot.getColumn() + 1;
            case Robot.LOOKING_LEFT:
                desiredRow = this.robot.getRow() - 1;
        }

        //Iterate all walls for check if the robot position is valid in the desired row/column

        if(desiredRow == height || desiredRow == -1 || desiredColumn == width || desiredColumn == -1) return false;

        for(Wall wall: walls){
            if(wall.getRow() == desiredRow && wall.getColumn() == desiredColumn){
                return false;
            }
        }

        return true;
    }

    private boolean canRobotPickCoins() {
        PileCoin pileCoin = getPileCoinsInPosition(robot.getRow(), robot.getColumn());
        if(pileCoin != null){
            for(int x = 0; x < pileCoins.size(); x++){
                if(pileCoins.get(x).getPosition() == pileCoin.getPosition()){
                    pileCoins.get(x).decrementCoins(1);
                    if(pileCoins.get(x).getCoinsCount() == 0){
                        pileCoins.remove(x);
                    }
                }
            }
            return true;
        }
        return false;
    }

    public static boolean isValidInstruction(String line) {
        return line.equals(MOVE) || line.equals(ROTATE) || line.equals(PICK);
    }

    private PileCoin getPileCoinsInPosition(int row, int column) {
        return pileCoins.stream()
                .filter(x -> x.getRow() == row && x.getColumn() == column)
                .findAny()
                .orElse(null);
    }

    private boolean isRobotInPosition(int row, int column) {
        return robot.getColumn() == column && robot.getRow() == row;
    }

    private boolean isWallInPosition(int row, int column) {
        return walls.stream()
                .filter(x -> x.getRow() == row && x.getColumn() == column)
                .count() > 0;
    }

    @Override
    public String toString() {
        String currentMap = "";

        for(int row = 0; row < height; row++){
            for(int column = 0; column < width; column++){
                boolean isWallInPosition = isWallInPosition(row, column);
                boolean isRobotInPosition = isRobotInPosition(row, column);
                PileCoin pileCoinsInPosition = getPileCoinsInPosition(row, column);

                if(isWallInPosition){
                    currentMap += "*";
                } else if(isRobotInPosition){
                    currentMap += robot;
                } else if(pileCoinsInPosition != null){
                    currentMap += String.valueOf(pileCoinsInPosition.getCoinsCount());
                } else {
                    currentMap += " ";
                }
            }
            currentMap += "\n";
        }
        return currentMap;
    }


}