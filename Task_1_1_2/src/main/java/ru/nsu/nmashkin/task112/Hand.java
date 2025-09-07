package ru.nsu.nmashkin.task112;

import java.util.Vector;

/**
 * Hand class representing player's or dealer's hand
 */
public class Hand {
    private final Vector<Card> cards;
    private final boolean dealer;

    /**
     * Constructor initializing the cards vector
     *
     * @param isDealer is this a dealer's hand
     */
    public Hand(boolean isDealer) {
        cards = new Vector<>(2);
        dealer = isDealer;
    }

    /**
     * Add a card to the hand
     *
     * @param card the card to be added
     */
    public void addCard(Card card) {
        if (dealer && cards.size() == 1) {
            card.hide();
        }
        cards.add(card);
    }

    /**
     * Get the sum of cards' worths
     *
     * @return the sum of cards' worths
     */
    public int sum() {
        int result = 0;
        for (Card card : cards) {
            result += card.getWorth();
        }
        return result;
    }

    /**
     * Downgrade an ace if possible
     *
     * @return true if nothing to rebalance
     */
    public boolean rebalance() {
        for (Card card : cards) {
            if (card.downgrade()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Reveal dealer's second card
     */
    public void enableDealer() {
        if (!dealer) {
            return;
        }

        cards.get(cards.size() - 1).reveal();
    }

    /**
     * Get the last card in hand
     *
     * @return the last card in hand
     */
    public Card getLast() {
        return cards.get(cards.size() - 1);
    }

    /**
     * Determine how the hand is printed
     *
     * @return the string representation
     */
    @Override
    public String toString() {
        return cards.toString();
    }
}
