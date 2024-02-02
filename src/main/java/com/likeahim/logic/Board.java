package com.likeahim.logic;

import com.likeahim.logic.pieces.None;
import com.likeahim.logic.pieces.Piece;
import com.likeahim.logic.pieces.TheKing;
import com.likeahim.logic.pieces.TheMan;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

public class Board {
    private final List<BoardRow> rows = new ArrayList<>();
    private PiecesSetUp piecesSetUp;
    private PiecesColor colorWithMove;
    private PiecesColor colorOnTop;
    private int movesIndex;
    private int whiteCast = 12;
    private int blackCast = 12;

    public Board() {
        for (int row = 0; row < 8; row++)
            rows.add(new BoardRow(8));
    }

    public void chooseSides(int sideNumber) {
        if (sideNumber == 1) {
            colorOnTop = PiecesColor.WHITE;
            colorWithMove = getColorOnBottom();
        } else {
            colorOnTop = PiecesColor.BLACK;
            colorWithMove = getColorOnBottom();
        }
    }

    public Piece getChecker(int row, int col) {
        return rows.get(row).getCols().get(col);
    }

    public void setPiece(int row, int col, Piece piece) {
        rows.get(row).getCols().set(col, piece);
    }

    public void gameSet() {
        setPiecesOnBottom();
        setPiecesOnTop();
    }

    private void setPiecesOnBottom() {
        setPiece(7, 0, new TheMan(getColorOnBottom()));
        setPiece(7, 2, new TheMan(getColorOnBottom()));
        setPiece(7, 4, new TheMan(getColorOnBottom()));
        setPiece(7, 6, new TheMan(getColorOnBottom()));
        setPiece(6, 1, new TheMan(getColorOnBottom()));
        setPiece(6, 3, new TheMan(getColorOnBottom()));
        setPiece(6, 5, new TheMan(getColorOnBottom()));
        setPiece(6, 7, new TheMan(getColorOnBottom()));
        setPiece(5, 0, new TheMan(getColorOnBottom()));
        setPiece(5, 2, new TheMan(getColorOnBottom()));
        setPiece(5, 4, new TheMan(getColorOnBottom()));
        setPiece(5, 6, new TheMan(getColorOnBottom()));
    }

    private void setPiecesOnTop() {
        setPiece(0, 1, new TheMan(colorOnTop));
        setPiece(0, 3, new TheMan(colorOnTop));
        setPiece(0, 5, new TheMan(colorOnTop));
        setPiece(0, 7, new TheMan(colorOnTop));
        setPiece(1, 0, new TheMan(colorOnTop));
        setPiece(1, 2, new TheMan(colorOnTop));
        setPiece(1, 4, new TheMan(colorOnTop));
        setPiece(1, 6, new TheMan(colorOnTop));
        setPiece(2, 1, new TheMan(colorOnTop));
        setPiece(2, 3, new TheMan(colorOnTop));
        setPiece(2, 5, new TheMan(colorOnTop));
        setPiece(2, 7, new TheMan(colorOnTop));
    }

    @Override
    public String toString() {
        String board = printActivCast(this.colorOnTop) + "\n  ||__|__|__|__|__|__|__|__||\n";

        for (int row = 0; row < 8; row++) {
            board += row + " " + rows.get(row).toString();
        }
        board += "  ||¯¯|¯¯|¯¯|¯¯|¯¯|¯¯|¯¯|¯¯||\n";
        board += "    0  1  2  3  4  5  6  7\n";
        return board + printActivCast(getColorOnBottom());
    }

    private String printActivCast(PiecesColor color) {
        String cast = "";
        if (color == PiecesColor.WHITE) {
            for (int i = 0; i < whiteCast; i++) {
                cast += "[W]";
            }
            cast += " {" + whiteCast + "}";
        } else {
            for (int i = 0; i < blackCast; i++) {
                cast += "[B]";
            }
            cast += " {" + blackCast + "}";
        }
        return cast;
    }

    public boolean move(Move move) {
        Piece toMove = getChecker(move.getCurrentRow(), move.getCurrentCol());
        boolean identityFlag = isManPiece(toMove);
        boolean colorFlag = isColorWithMove(toMove.getColor()); //works also when player moves opponents piece
        boolean validMoveFlag = isValidMove(move);
        boolean captureCancelledFlag = isCaptureCancelled(move);
        if (identityFlag && colorFlag && validMoveFlag) {
            if(hasCapture(move) && isBigMove(move)) {
                moveWithCapture(move, toMove);
            } else {
                makeThisMove(move, toMove);
            }
            return true;
        } else {
            if (isBigMove(move) && hasCapture(move)) {
                moveWithCapture(move, toMove);
            } else if (hasCapture(move))
                System.out.println("you need to capture, try again");
            else
                System.out.println("unvalid move, try again");
            return false;
        }
    }

    private void makeThisMove(Move move, Piece toMove) {
        System.out.println((movesIndex + 1) + ") " + colorWithMove + " moved -->");
        rows.get(move.getCurrentRow()).getCols().set(move.getCurrentCol(), new None());
        rows.get(move.getNewRow()).getCols().set(move.getNewCol(), toMove);
        printInfoAfterMove();
    }

    private boolean isBigMove(Move move) {
        boolean result = false;
        int deltaRow = move.getCurrentRow() - move.getNewRow();
        int deltaCol = move.getCurrentCol() - move.getNewCol();
        if((deltaRow == 2 || deltaRow == -2)  && (deltaCol == -2 || deltaCol == 2) && isCaptureMoveValid(move))
            result = true;
        return result;
    }

    private boolean isCaptureMoveValid(Move move) {
        int capturedRow = calculateRemovedRow(move);
        int capturedCol = calculateRemovedCol(move);
        Piece pieceToCapture = getChecker(capturedRow, capturedCol);
        return (isManPiece(pieceToCapture) && pieceToCapture.getColor() != colorWithMove);
    }

    private void moveWithCapture(Move moveWithCapture, Piece toMove) {
        int removedRow = calculateRemovedRow(moveWithCapture);
        int removedCol = calculateRemovedCol(moveWithCapture);
        rows.get(moveWithCapture.getCurrentRow()).getCols().set(moveWithCapture.getCurrentCol(), new None());
        toMove.setColor(colorWithMove);
        rows.get(moveWithCapture.getNewRow()).getCols().set(moveWithCapture.getNewCol(), toMove);
        rows.get(removedRow).getCols().set(removedCol, new None());
        uptadeCast();
        printInfoAfterMove();
    }

    private void uptadeCast() {
        if (colorWithMove == PiecesColor.WHITE)
            blackCast--;
        else
            whiteCast--;
    }

    private int calculateRemovedCol(Move moveWithCapture) {
        int removedCol = 0;
        int currentCol = moveWithCapture.getCurrentCol();
        int newCol = moveWithCapture.getNewCol();
        if (currentCol > newCol)
            removedCol = currentCol - 1;
        else
            removedCol = currentCol + 1;

        return removedCol;
    }

    private int calculateRemovedRow(Move moveWithCapture) {
        int removedRow = 0;
        int currentRow = moveWithCapture.getCurrentRow();
        int newRow = moveWithCapture.getNewRow();
        if (currentRow > newRow)
            removedRow = currentRow - 1;
        else
            removedRow = currentRow + 1;

        return removedRow;
    }

    private void printInfoAfterMove() {
        System.out.println(this);
        colorWithMove = changeColorWithMove(); //another check if after capture is another capture, if not, than change color
        System.out.println("Next move: " + colorWithMove);
        movesIndex++;
    }

    private boolean isValidMove(Move move) {
        boolean result = false;
        Piece movingPiece = rows.get(move.getCurrentRow()).getCols().get(move.getCurrentCol());
        int deltaRow = move.getCurrentRow() - move.getNewRow();
        int deltaCol = move.getCurrentCol() - move.getNewCol();
        if (rows.get(move.getNewRow()).getCols().get(move.getNewCol()).isOpponent(movingPiece)){
            return false;
        } else if (movingPiece.getColor() == getColorOnBottom()) {//it redundant, color already checked
            if (deltaRow > 0 && ((deltaCol < 0 && move.getNewCol() <= 7) || (deltaCol > 0 && move.getNewCol() >= 0)))
                result = true;
        } else if (deltaRow < 0 && ((deltaCol < 0 && move.getNewCol() <= 7) || (deltaCol > 0 && move.getNewCol() >= 0))) {
            result = true;
        }
        result = result && (abs(deltaRow) == abs(deltaCol));
        return result;
    }

    private boolean hasCapture(Move move) {
        Piece movingPiece = rows.get(move.getCurrentRow()).getCols().get(move.getCurrentCol());
        boolean result = false;
        boolean isBoardCapture = boardCaputre(move, result, movingPiece);
        int currentRow = move.getCurrentRow();
        int currentCol = move.getCurrentCol();
        int nextUpRow = currentRow - 1;
        int nextDownRow = currentRow + 1;
        int nextLeftCol = currentCol - 1;
        int nextRightCol = currentCol + 1;
        if (isBoardCapture)
            result = true;
        else if ( //can give false answer
                ((getNextColor(nextUpRow, nextLeftCol) == getWaitingColor()) && isFreeCaptureSpace(nextUpRow-1, nextLeftCol-1))
                        || ((getNextColor(nextUpRow, nextRightCol) == getWaitingColor()) && isFreeCaptureSpace(nextUpRow-1, nextRightCol+1))
                        || ((getNextColor(nextDownRow, nextLeftCol) == getWaitingColor()) && isFreeCaptureSpace(nextDownRow+1, nextLeftCol-1))
                        || ((getNextColor(nextDownRow, nextRightCol) == getWaitingColor()) && isFreeCaptureSpace(nextDownRow+1, nextRightCol+1))) {
            result = true;
        }
        return result;
    }

    private PiecesColor getNextColor(int nextUpRow, int nextLeftCol) {
        PiecesColor temp = getColorWithMove();
        if ((nextUpRow > 7 || nextUpRow < 0) || (nextLeftCol > 7 || nextLeftCol < 0))
            return temp;
        else
            return rows.get(nextUpRow).getCols().get(nextLeftCol).getColor();
    }
    // exception
    private boolean isFreeCaptureSpace(int nextRow, int nextCol) {
        boolean isFree = false;
        try {
            if ((rows.get(nextRow).getCols().get(nextCol) instanceof None))
                isFree = true;
        } catch (Exception e) {
            isFree = true;
        }
        return isFree;
    }

    private boolean boardCaputre(Move move, boolean result, Piece movingPiece) {
        if (move.getCurrentRow() >= 6 && move.getCurrentCol() <= 1) {
            return captureLeftDown(move, movingPiece, result);
        } else if (move.getCurrentRow() <= 1 && move.getCurrentCol() >= 6) {
            return captureRightUp(move, movingPiece, result);
        } else if (move.getCurrentRow() >= 6 && move.getCurrentCol() >= 6) {
            return captureRightDown(move, movingPiece, result);
        } else if (move.getCurrentRow() <= 1 && move.getCurrentCol() <= 1) {
            return captureLeftUp(move, movingPiece, result);
        }
        return result;
    }

    private boolean captureLeftUp(Move move, Piece movingPiece, boolean result) {
        Piece tempPiece;
        Piece jumpPiece;
        tempPiece = rows.get(move.getCurrentRow() + 1).getCols().get(move.getCurrentCol() + 1);
        jumpPiece = rows.get(move.getCurrentRow() + 2).getCols().get(move.getCurrentCol() + 2);
        if (movingPiece.isOpponent(tempPiece) && jumpPiece instanceof None)
            result = true;
        return result;
    }

    private boolean captureRightDown(Move move, Piece movingPiece, boolean result) {
        Piece jumpPiece;
        Piece tempPiece;
        tempPiece = rows.get(move.getCurrentRow() - 1).getCols().get(move.getCurrentCol() - 1);
        jumpPiece = rows.get(move.getCurrentRow() - 2).getCols().get(move.getCurrentCol() - 2);
        if (movingPiece.isOpponent(tempPiece) && jumpPiece instanceof None)
            result = true;
        return result;
    }

    private boolean captureRightUp(Move move, Piece movingPiece, boolean result) {
        Piece jumpPiece;
        Piece tempPiece;
        tempPiece = rows.get(move.getCurrentRow() + 1).getCols().get(move.getCurrentCol() - 1);
        jumpPiece = rows.get(move.getCurrentRow() + 2).getCols().get(move.getCurrentCol() - 2);
        if (movingPiece.isOpponent(tempPiece) && jumpPiece instanceof None)
            result = true;
        return result;
    }

    private boolean captureLeftDown(Move move, Piece movingPiece, boolean result) {
        Piece jumpPiece;
        Piece tempPiece;
        tempPiece = rows.get(move.getCurrentRow() - 1).getCols().get(move.getCurrentCol() + 1);
        jumpPiece = rows.get(move.getCurrentRow() - 2).getCols().get(move.getCurrentCol() + 2);
        if (movingPiece.isOpponent(tempPiece) && jumpPiece instanceof None)
            result = true;
        return result;
    }

    private boolean isCaptureCancelled(Move move) {
        boolean result = false;
        int deltaRow = move.getCurrentRow() - move.getNewRow();
        int deltaCol = move.getCurrentCol() - move.getNewCol();
        if ((move.getCurrentCol() == 1 && deltaCol > 0) || (move.getCurrentCol() == 6 && deltaCol < 0))
            result = true;
        else if ((move.getCurrentRow() == 1 && deltaRow > 0) || (move.getCurrentRow() == 6 && deltaRow < 0))
            result = true;
        return result;
    }

    private boolean isColorWithMove(PiecesColor color) {
        return colorWithMove == color;
    }

    private PiecesColor changeColorWithMove() {
        return colorWithMove == PiecesColor.WHITE ? colorWithMove = PiecesColor.BLACK : PiecesColor.WHITE;
    }

    private boolean isManPiece(Piece toMove) {
        return !(toMove instanceof None || toMove instanceof TheKing);
    }

    private boolean isKing(Piece toMove) {
        return toMove instanceof TheKing;
    }

    public int getWhiteCast() {
        return whiteCast;
    }

    public int getBlackCast() {
        return blackCast;
    }

    public PiecesColor getColorWithMove() {
        return colorWithMove;
    }

    public PiecesColor getWaitingColor() {
        PiecesColor waitingColor = PiecesColor.NONE;
        if (getColorWithMove() == PiecesColor.WHITE)
            waitingColor = PiecesColor.BLACK;
        else
            waitingColor = PiecesColor.WHITE;
        return waitingColor;
    }

    public PiecesColor getColorOnBottom() {
        return colorOnTop == PiecesColor.WHITE ? PiecesColor.BLACK : PiecesColor.WHITE;
    }

    public void printGamesResult() {
        if(whiteCast > blackCast)
            System.out.println("WHITE wins " + whiteCast + " to " + blackCast);
        else if(blackCast > whiteCast)
            System.out.println("BLACK wins " + blackCast + " to " + whiteCast);
    }
}
