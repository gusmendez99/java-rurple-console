package com.gusmendez.model;

public class PileCoin {
    private int [] position;
    private int numberCoins;

    public PileCoin(int row, int column, int numberCoins) {
        this.position = new int[2];
        this.position[0] = row;
        this.position[1] = column;
        this.numberCoins = numberCoins;
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

    public int getCoinsCount() {
        return numberCoins;
    }

    public void decrementCoins(int numberCoins) {
        this.numberCoins--;
    }

    @Override
    public String toString() {
        return "PileCoins: row: " + getRow() + ", column: " + getColumn() + ", coins: " + getCoinsCount();
    }
}
