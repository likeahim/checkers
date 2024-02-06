package com.likeahim;

import com.likeahim.logic.*;
import com.likeahim.logic.ui.UserInput;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class CheckersApplication {
    public static void main(String[] args) {
        Board board = new Board();
        board.chooseSides(UserInput.sidesChoosingNumbers());
        board.gameSet();
        System.out.println(board);
        Scanner scanner = new Scanner(System.in);
        playGame(board, scanner);
        board.printGamesResult();
        scanner.close();
    }

    private static void playGame(Board board, Scanner scanner) {
        while (
                (board.getWhiteCast() != 0 && board.getColorWithMove() == PiecesColor.WHITE) ||
                (board.getBlackCast() != 0 && board.getColorWithMove() == PiecesColor.BLACK)
        ) {
            boolean move = false;
            while (!move) {
                Move moveToMake;
                System.out.print(board.getColorWithMove());
                moveToMake = UserInput.makeAMove(scanner);
                boolean moveResult = board.isBigMove(moveToMake);
                move = board.move(moveToMake);
                if(board.checkDoubleCapture(moveToMake.getNewRow(), moveToMake.getNewCol(), moveResult, moveToMake)) {
                    move = false;
                }
            }
            board.changeColorWithMove();
        }
    }
}
