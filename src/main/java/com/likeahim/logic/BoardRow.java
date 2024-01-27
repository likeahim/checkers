package com.likeahim.logic;

import com.likeahim.logic.pieces.None;
import com.likeahim.logic.pieces.Piece;

import java.util.ArrayList;
import java.util.List;

public class BoardRow {
    private int numberOfCols;
    private final List<Piece> cols = new ArrayList<>();

    public BoardRow(int numberOfCols) {
        this.numberOfCols = numberOfCols;
        for (int col = 0; col < numberOfCols; col++) {
            cols.add(new None());
        }
    }

    public List<Piece> getCols() {
        return cols;
    }

    @Override
    public String toString() {
        String boardRow = "||";
        for (int col = 0; col < 8; col++) {
            boardRow += cols.get(col) + "|";
        }
        boardRow += "|\n";
        return boardRow;
    }
}
