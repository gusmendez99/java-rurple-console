package com.gusmendez.model;

public class Robot {
    private int [] position;
    private int carryCoins;
    private int direction;

    public Robot(int row, int column, int direction) {
        this.position = new int[2];
        this.position[0] = row;
        this.position[1] = column;
        this.carryCoins = 0;
        this.direction = direction;
    }

    public int[] getPosition(){
        return this.position;
    }

    public int getRow(){
        return this.position[0];
    }

    public void setRow(int row){
        this.position[0] = row;
    }

    public int getColumn(){
        return this.position[1];
    }

    public void setColumn(int column){
        this.position[1] = column;
    }

    public int getCarryCoins() {
        return carryCoins;
    }

    public void incrementCarryCoins(int coins) {
        this.carryCoins += coins;
    }

    public int getDirection() {
        return direction;
    }

    public void rotate() {
        this.direction++;
        if(this.direction % 4 == 0){
            this.direction = 0;
        }
    }

    public void move(){
        switch (this.direction){
            case 0: // ^
                setRow(getRow() - 1);
                break;
            case 1: // >
                setColumn(getColumn() + 1);
                break;
            case 2: // v
                setRow(getRow() + 1);
                break;
            case 3:// <
                setColumn(getColumn() - 1);
                break;
        }
    }

    @Override
    public String toString() {
        switch (this.direction){
            case 0: // ^
                return "^";
            case 1: // >
                return ">";
            case 2: // v
                return "v";
            case 3:// <
                return "<";
        }
        return "";
    }
}
