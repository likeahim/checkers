package com.likeahim.logic.pieces;

import com.likeahim.logic.Move;
import com.likeahim.logic.PiecesColor;

public class TheKing implements Piece{
    private final PiecesColor color;

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

}
