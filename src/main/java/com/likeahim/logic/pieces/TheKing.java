package com.likeahim.logic.pieces;

import com.likeahim.logic.Move;
import com.likeahim.logic.PiecesColor;

public class TheKing implements Piece{
    private PiecesColor color;

    public TheKing(PiecesColor color) {
        this.color = color;
    }

    @Override
    public PiecesColor getColor() {
        return color;
    }

    @Override
    public boolean isOpponent(Piece tempPiece) {
        return color == tempPiece.getColor();
    }

    @Override
    public void setColor(PiecesColor color) {
        this.color = color;
    }

}
