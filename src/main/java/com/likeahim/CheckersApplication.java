package com.likeahim;

import com.likeahim.logic.Board;
import com.likeahim.logic.Move;
import com.likeahim.logic.PiecesColor;
import com.likeahim.logic.PiecesSetUp;
import com.likeahim.logic.ui.UserInput;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class CheckersApplication {
    public static void main(String[] args) {
        Board board = new Board();
        board.chooseSides();
        board.gameSet();
        board.gameSet();
        System.out.println(board);
        Scanner scanner = new Scanner(System.in);
        while(board.getWhiteCast() != 0 || board.getBlackCast() != 0) {
            Move moveToMake;
            System.out.print(board.getColorWithMove());
            moveToMake = UserInput.makeAMove(scanner);
            board.move(moveToMake);
        }
        scanner.close();
    }
}
