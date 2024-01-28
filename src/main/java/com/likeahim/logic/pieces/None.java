package com.likeahim.logic.pieces;

import com.likeahim.logic.Move;
import com.likeahim.logic.PiecesColor;

public class None implements Piece {

    private PiecesColor color = PiecesColor.NONE;
    @Override
    public PiecesColor getColor() {
        return this.color;
    }

    @Override
    public boolean isOpponent(Piece tempPiece) {
        return false;
    }

    @Override
    public void setColor(PiecesColor color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "  ";
    }
}
