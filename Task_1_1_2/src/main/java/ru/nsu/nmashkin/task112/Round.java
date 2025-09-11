package ru.nsu.nmashkin.task112;

import java.util.Scanner;

/**
 * Class for playing out one round of the game.
 */
public class Round {
    /**
     * All possible round results.
     */
    public enum RoundResult {
        PlayerWin,
        DealerWin,
        Draw,
        Undetermined;

        private static RoundResult getResult(int playerPoints, int dealerPoints) {
            if (playerPoints > dealerPoints) {
                return PlayerWin;
            }
            if (playerPoints < dealerPoints) {
                return DealerWin;
            }
            return Draw;
        }
    }

    private final Scanner in;
    private final Deck deck;
    private final Hand player;
    private final Hand dealer;
    private static final int BlackJack = 21;

    /**
     * Primary constructor, initializes a random deck.
     *
     * @param input where to read user input from
     */
    public Round(Scanner input) {
        in = input;
        deck = new Deck();
        player = new Hand(false);
        dealer = new Hand(true);
    }

    /**
     * Secondary constructor for testing.
     *
     * @param input where to read the input from
     * @param d custom deck
     */
    public Round(Scanner input, Deck d) {
        in = input;
        deck = d;
        player = new Hand(false);
        dealer = new Hand(true);
    }

    /**
     * Show player's and dealer's cards as well as player's total points.
     *
     * @param showDealerSum if true show dealer's total points
     */
    private void printState(boolean showDealerSum) {
        System.out.println("Your cards: " + player + " ==> " + player.sum());
        System.out.println("Dealer's cards: " + dealer
                            + (showDealerSum ? " ==> " + dealer.sum() : ""));
    }

    /**
     * Play one round of the game.
     *
     * @return result of the round
     */
    public RoundResult round() {
        RoundResult result = initialDraw();
        if (result != RoundResult.Undetermined) {
            return result;
        }

        result = playerTurn();
        if (result != RoundResult.Undetermined) {
            return result;
        }

        dealer.enableDealer();
        result = dealerTurn();
        if (result != RoundResult.Undetermined) {
            return result;
        }

        System.out.println("\nFinal count:");
        System.out.println("You - " + player.sum());
        System.out.println("Dealer - " + dealer.sum());

        result = RoundResult.getResult(player.sum(), dealer.sum());
        switch (result) {
            case PlayerWin -> System.out.println("You win :)");
            case DealerWin -> System.out.println("You lose :(");
            default -> System.out.println("Draw");
        }
        return result;
    }

    /**
     * Performs initial card draw sequence.
     *
     * @return Undetermined if round continues, else some RoundResult
     */
    private RoundResult initialDraw() {
        player.addCard(deck.draw());
        player.addCard(deck.draw());
        dealer.addCard(deck.draw());
        dealer.addCard(deck.draw());

        printState(false);

        if (player.sum() == BlackJack) {
            System.out.println("You got a BlackJack!");
            dealer.enableDealer();
            System.out.println("Dealer's hidden card was: " + dealer.getLast());

            if (dealer.sum() == BlackJack) {
                System.out.println("Dealer got a BlackJack too!");
                System.out.println("Draw");
                return RoundResult.Draw;
            }
            System.out.println("You win :)");
            return RoundResult.PlayerWin;
        }

        return RoundResult.Undetermined;
    }

    /**
     * Handle the player's turn part.
     *
     * @return Undetermined if round continues, else some RoundResult
     */
    private RoundResult playerTurn() {
        System.out.println("\nYour turn:");
        System.out.println("Type 1 to draw a card, type 0 to stop");

        String answer = in.nextLine();
        while (!answer.equals("0")) {
            if (!answer.equals("1")) {
                System.out.println("Please, provide a valid answer (1 to draw, 0 to stop)");
                answer = in.nextLine();
                continue;
            }

            player.addCard(deck.draw());
            System.out.println("The drawn card is: " + player.getLast());
            if (player.sum() > BlackJack) {
                if (player.rebalance()) {
                    System.out.println("You lost :(");
                    return RoundResult.DealerWin;
                }
            }

            printState(false);

            if (player.sum() == BlackJack) {
                System.out.println("You got a BlackJack!");
                dealer.enableDealer();
                System.out.println("Dealer's hidden card was: " + dealer.getLast());

                if (dealer.sum() == BlackJack) {
                    System.out.println("Dealer got a BlackJack too!");
                    System.out.println("Draw");
                    return RoundResult.Draw;
                }
                System.out.println("You win :)");
                return RoundResult.PlayerWin;
            }

            answer = in.nextLine();
        }

        return RoundResult.Undetermined;
    }

    /**
     * Handle the dealer's turn.
     *
     * @return Undetermined if round continues, else some RoundResult
     */
    private RoundResult dealerTurn() {
        System.out.println("\nDealer's turn");
        System.out.println("The hidden card is: " + dealer.getLast());

        printState(true);

        if (dealer.sum() == BlackJack) {
            System.out.println("Dealer got a BlackJack!");
            System.out.println("Dealer wins :(");
            return RoundResult.DealerWin;
        }

        while (dealer.sum() < 17) {
            dealer.addCard(deck.draw());
            System.out.println("The drawn card is: " + dealer.getLast());

            if (dealer.sum() > BlackJack) {
                if (dealer.rebalance()) {
                    System.out.println("Dealer lost :)");
                    return RoundResult.PlayerWin;
                }
            }

            printState(true);

            if (dealer.sum() == BlackJack) {
                System.out.println("Dealer got a BlackJack!");
                System.out.println("Dealer wins :(");
                return RoundResult.DealerWin;
            }
        }

        return RoundResult.Undetermined;
    }
}
