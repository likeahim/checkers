package com.likeahim.logic.pieces;

import com.likeahim.logic.Move;
import com.likeahim.logic.PiecesColor;

public interface Piece {
    PiecesColor getColor();

    boolean isOpponent(Piece tempPiece);

    void setColor(PiecesColor color);
}
