package ru.nsu.nmashkin.task112;

import java.util.Objects;

/**
 * Card class used to abstract from card numbers.
 */
public class Card {
    private boolean hidden = false;
    private final String name;
    private int worth;

    private enum Suit {
        Hearts("Hearts"),
        Spades("Spades"),
        Clubs("Clubs"),
        Diamonds("Diamonds");

        private Suit(String val) {
        }
    }

    private enum Face {
        Two("Two"),
        Three("Three"),
        Four("Four"),
        Five("Five"),
        Six("Six"),
        Seven("Seven"),
        Eight("Eight"),
        Nine("Nine"),
        Ten("Ten"),
        Jack("Jack"),
        Queen("Queen"),
        King("King"),
        Ace("Ace");

        private Face(String val) {
        }
    }

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

        Face[] names = new Face[] {
            Face.Two,
            Face.Three,
            Face.Four,
            Face.Five,
            Face.Six,
            Face.Seven,
            Face.Eight,
            Face.Nine,
            Face.Ten,
            Face.Jack,
            Face.Queen,
            Face.King,
            Face.Ace
        };
        Suit[] suits = new Suit[] {
            Suit.Hearts,
            Suit.Spades,
            Suit.Clubs,
            Suit.Diamonds
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
     * Get the card's name.
     *
     * @return the card's name
     */
    public String getName() {
        return name;
    }

    /**
     * Is the card hidden.
     *
     * @return true if hidden
     */
    public boolean isHidden() {
        return hidden;
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
     * Compare with another card.
     *
     * @param o the card to be compared with
     * @return true if equal
     */
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Card card = (Card) o;
        return hidden == card.hidden && worth == card.worth && Objects.equals(name, card.name);
    }

    /**
     * Get hash of a card.
     *
     * @return hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(hidden, name, worth);
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
