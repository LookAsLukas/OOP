package ru.nsu.nmashkin.task112;

import java.util.Scanner;

/**
 * Main game class.
 */
public class Game {
    private static int roundNo = 1;
    private static int playerWinCount = 0;
    private static int dealerWinCount = 0;

    /**
     * Entry point.
     *
     * @param args useless
     */
    public static void main(String[] args) {
        System.out.println("Welcome to The BlackJack!");
        Scanner in = new Scanner(System.in);
        while (true) {
            System.out.println("\nRound " + roundNo);

            Round round = new Round(in);
            switch (round.round()) {
                case PlayerWin -> playerWinCount++;
                case DealerWin -> dealerWinCount++;
                default -> {}
            }
            roundNo++;

            System.out.println("\nThe score is:");
            System.out.println("You - " + playerWinCount);
            System.out.println("Dealer - " + dealerWinCount);

            System.out.println("\nIf you want to continue type 1, else - 0");
            String answer = in.nextLine();
            while (!answer.equals("0") && !answer.equals("1")) {
                System.out.println("Please provide a valid answer (1 to continue, 0 to quit)");
                answer = in.nextLine();
            }
            if (answer.equals("0")) {
                break;
            }
        }
        System.out.println("Goodbye!");
        in.close();
    }
}
