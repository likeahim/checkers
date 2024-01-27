package com.likeahim.logic.pieces;

import com.likeahim.logic.Move;
import com.likeahim.logic.PiecesColor;

public class None implements Piece {
    @Override
    public PiecesColor getColor() {
        return PiecesColor.NONE;
    }

    @Override
    public boolean isOpponent(Piece tempPiece) {
        return false;
    }

    @Override
    public String toString() {
        return "  ";
    }
}
