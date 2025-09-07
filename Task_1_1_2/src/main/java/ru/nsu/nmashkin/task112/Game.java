package ru.nsu.nmashkin.task112;

import java.util.Scanner;

/**
 * Main game class.
 */
public class Game {
    private static int roundNo = 1;
    private static int playerWinCount = 0;
    private static int dealerWinCount = 0;
    private static final Scanner input = new Scanner(System.in);

    /**
     * Entry point.
     *
     * @param args useless
     */
    public static void main(String[] args) {
        System.out.println("Welcome to The BlackJack!");
        while (true) {
            System.out.println("\nRound " + roundNo);

            int result  = round();
            if (result > 0) {
                playerWinCount++;
            } else if (result < 0) {
                dealerWinCount++;
            }
            roundNo++;
            System.out.println("\nThe score is:");
            System.out.println("You - " + playerWinCount);
            System.out.println("Dealer - " + dealerWinCount);

            System.out.println("\nIf you want to continue type 1, else - 0");
            String answer = input.nextLine();
            while (!answer.equals("0") && !answer.equals("1")) {
                System.out.println("Please provide a valid answer (1 to continue, 0 to quit)");
                answer = input.nextLine();
            }
            if (answer.equals("0")) {
                break;
            }
        }
        System.out.println("Goodbye!");
        input.close();
    }

    /**
     * Play one round of the game.
     *
     * @return 1 if the player won, -1 if the dealer won, 0 if draw
     */
    public static int round() {
        Deck deck = new Deck();
        Hand player = new Hand(false);
        Hand dealer = new Hand(true);

        player.addCard(deck.draw());
        player.addCard(deck.draw());
        dealer.addCard(deck.draw());
        dealer.addCard(deck.draw());

        System.out.println("Your cards: " + player + " ==> " + player.sum());
        System.out.println("Dealer's cards: " + dealer);

        if (player.sum() == 21) {
            System.out.println("You got a BlackJack!");
            dealer.enableDealer();
            System.out.println("Dealer's hidden card was: " + dealer.getLast());

            if (dealer.sum() == 21) {
                System.out.println("Dealer got a BlackJack too!");
                System.out.println("Draw");
                return 0;
            }
            System.out.println("You win :)");
            return 1;
        }

        System.out.println("Your turn:");
        System.out.println("Type 1 to draw a card, type 0 to stop");
        String answer = input.nextLine();
        while (!answer.equals("0")) {
            if (!answer.equals("1")) {
                System.out.println("Please, provide a valid answer (1 to draw, 0 to stop)");
                answer = input.nextLine();
                continue;
            }

            player.addCard(deck.draw());
            System.out.println("The drawn card is: " + player.getLast());
            if (player.sum() > 21) {
                if (player.rebalance()) {
                    System.out.println("You lost :(");
                    return -1;
                }
            }
            System.out.println("Your cards: " + player + " ==> " + player.sum());
            System.out.println("Dealer's cards: " + dealer);

            if (player.sum() == 21) {
                System.out.println("You got a BlackJack!");
                dealer.enableDealer();
                System.out.println("Dealer's hidden card was: " + dealer.getLast());

                if (dealer.sum() == 21) {
                    System.out.println("Dealer got a BlackJack too!");
                    System.out.println("Draw");
                    return 0;
                }
                System.out.println("You win :)");
                return 1;
            }

            answer = input.nextLine();
        }

        System.out.println("\nDealer's turn");
        dealer.enableDealer();
        System.out.println("The hidden card is: " + dealer.getLast());
        System.out.println("Dealer's cards: " + dealer + " ==> " + dealer.sum() + "\n");

        if (dealer.sum() == 21) {
            System.out.println("Dealer got a BlackJack!");
            System.out.println("Dealer wins :(");
            return -1;
        }

        while (dealer.sum() < 17) {
            dealer.addCard(deck.draw());

            System.out.println("The drawn card is: " + dealer.getLast());
            if (dealer.sum() > 21) {
                if (dealer.rebalance()) {
                    System.out.println("Dealer lost :)");
                    return 1;
                }
            }
            System.out.println("Your cards: " + player + " ==> " + player.sum());
            System.out.println("Dealer's cards: " + dealer + " ==> " + dealer.sum());

            if (dealer.sum() == 21) {
                System.out.println("Dealer got a BlackJack!");
                System.out.println("Dealer wins :(");
                return -1;
            }
        }

        System.out.println("\nFinal count:");
        System.out.println("You - " + player.sum());
        System.out.println("Dealer - " + dealer.sum());

        if (player.sum() > dealer.sum()) {
            System.out.println("You win :)");
        } else if (player.sum() < dealer.sum()) {
            System.out.println("You lose :(");
        } else {
            System.out.println("Draw");
        }
        return player.sum() - dealer.sum();
    }
}
