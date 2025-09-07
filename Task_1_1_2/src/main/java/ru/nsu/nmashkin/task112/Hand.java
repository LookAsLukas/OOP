package ru.nsu.nmashkin.task112;

import java.util.Vector;

public class Hand {
    private final Vector<Card> cards;
    boolean dealer;

    public Hand(boolean isDealer) {
        cards = new Vector<>(2);
        dealer = isDealer;
    }

    public void addCard(Card card) {
        if (dealer && cards.size() == 1) {
            card.hide();
        }
        cards.add(card);
    }

    public int sum() {
        int result = 0;
        for (Card card : cards) {
            result += card.getWorth();
        }
        return result;
    }

    public boolean rebalance() {
        for (Card card : cards) {
            if (card.downgrade()) {
                return false;
            }
        }
        return true;
    }

    public void enableDealer() {
        if (!dealer) {
            return;
        }

        cards.get(cards.size() - 1).reveal();
    }

    public Card getLast() {
        return cards.get(cards.size() - 1);
    }

    @Override
    public String toString() {
        return cards.toString();
    }
}
