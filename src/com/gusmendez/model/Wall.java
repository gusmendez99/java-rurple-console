package com.gusmendez.model;

public class Wall {
    private int [] position;

    public Wall(int row, int column) {
        this.position = new int[2];
        this.position[0] = row;
        this.position[1] = column;
    }

    public int[] getPosition(){
        return this.position;
    }

    public int getRow(){
        return this.position[0];
    }

    public int getColumn(){
        return this.position[1];
    }

    @Override
    public String toString() {
        return "Wall: row: " + getRow() + ", column: " + getColumn();
    }

}
