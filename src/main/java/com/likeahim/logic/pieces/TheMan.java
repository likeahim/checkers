package com.likeahim.logic.pieces;

import com.likeahim.logic.Move;
import com.likeahim.logic.PiecesColor;

public class TheMan implements Piece {
    private PiecesColor color;

    public TheMan(PiecesColor color) {
        this.color = color;
    }

    @Override
    public PiecesColor getColor() {
        return color;
    }

    @Override
    public boolean isOpponent(Piece tempPiece) {
        return color != tempPiece.getColor();
    }

    @Override
    public void setColor(PiecesColor color) {
        this.color = color;
    }

    @Override
    public String toString() {
        if(color == PiecesColor.WHITE)
            return "Wm";
        else
            return "Bm";
    }
}
