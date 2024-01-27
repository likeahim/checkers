package com.likeahim.logic.ui;

import com.likeahim.logic.Move;

import java.util.Scanner;

public class UserInput {


    public static Move makeAMove(Scanner scanner) {
        System.out.println(" moves now. Give your current and next position:");
        return getMove(scanner);
    }

    private static Move getMove(Scanner scanner) {
        String input = scanner.nextLine();
        int currentRow = Integer.parseInt(input.substring(0, 1));
        int currentCol = Integer.parseInt(input.substring(1, 2));
        int newRow = Integer.parseInt(input.substring(2, 3));
        int newCol = Integer.parseInt(input.substring(3, 4));
        Move move = new Move(currentRow, currentCol, newRow, newCol);
        return move;
    }

    public static int sidesChoosingNumbers() {
        Scanner scanner = new Scanner(System.in);
        int number = 0;
        do {
            System.out.println("Bottom color has first move!\n Who starts on top?\n  1 - WHITES\n   2 - BLACK");
            number = scanner.nextInt();
        } while ((number < 1 || number > 2));
        return number;
    }

    public static Move makeAMoveWithCapture() {
        Scanner scanner = new Scanner(System.in);
        return makeAMove(scanner);
    }
}
