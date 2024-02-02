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
        do {
            boolean move = true;
            while (move) {
                Move moveToMake;
                System.out.print(board.getColorWithMove());
                moveToMake = UserInput.makeAMove(scanner);
                move = board.move(moveToMake);
            }
        } while (board.getWhiteCast() != 0 || board.getBlackCast() != 0);
        board.printGamesResult();
        scanner.close();
    }
}
