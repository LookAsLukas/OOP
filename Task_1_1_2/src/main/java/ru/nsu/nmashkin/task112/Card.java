package ru.nsu.nmashkin.task112;

/**
 * Card class used to abstract from card numbers.
 */
public class Card {
    private boolean hidden = false;
    private final String name;
    private int worth;

    /**
     * Constructor to determine the card's worth and suit.
     *
     * @param num card number (form 0 to 51)
     */
    public Card(int num) {
        if (num < 0 || num > 52) {
            name = null;
            worth = 0;
            return;
        }

        worth = Math.min(10, num % 13 + 2) + (num % 13 == 12 ? 1 : 0);

        String[] names = new String[] {
            "Two",
            "Three",
            "Four",
            "Five",
            "Six",
            "Seven",
            "Eight",
            "Nine",
            "Ten",
            "Jack",
            "Queen",
            "King",
            "Ace"
        };
        String[] suits = new String[] {
            "Hearts",
            "Spades",
            "Clubs",
            "Diamonds"
        };
        name = names[num % 13] + " of " + suits[num / 13];
    }

    /**
     * Make the card appear hidden.
     */
    public void hide() {
        hidden = true;
    }

    /**
     * Make the card stop appearing hidden.
     */
    public void reveal() {
        hidden = false;
    }

    /**
     * Get the card's value.
     *
     * @return how much is the card worth
     */
    public int getWorth() {
        return worth;
    }

    /**
     * If the card is an ace, downgrade it's worth.
     *
     * @return was the downgrade successful
     */
    public boolean downgrade() {
        if (worth != 11) {
            return false;
        }
        worth = 1;
        return true;
    }

    /**
     * Determine how the card is printed.
     *
     * @return string representation
     */
    @Override
    public String toString() {
        if (hidden) {
            return "<hidden card>";
        }
        return name + " (" + worth + ")";
    }
}
