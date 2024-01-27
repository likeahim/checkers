package com.likeahim.logic;

public class Move {
    private final int currentCol;
    private final int currentRow;
    private final int newCol;
    private final int newRow;

    public Move(int currentRow, int currentCol, int newRow, int newCol) {
        this.currentCol = currentCol;
        this.currentRow = currentRow;
        this.newCol = newCol;
        this.newRow = newRow;
    }

    public int getCurrentCol() {
        return currentCol;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public int getNewCol() {
        return newCol;
    }

    public int getNewRow() {
        return newRow;
    }
}
