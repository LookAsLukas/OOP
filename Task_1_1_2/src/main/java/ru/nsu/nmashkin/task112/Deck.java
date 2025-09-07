package ru.nsu.nmashkin.task112;

import java.util.Collections;
import java.util.Stack;

/**
 * Class for the representation of the deck of 52 cards
 */
public class Deck {
    private final Stack<Card> deck;

    /**
     * Constructor for initializing the deck
     */
    public Deck() {
        deck = new Stack<>();
        for (int i = 0; i < 52; i++) {
            deck.push(new Card(i));
        }

        Collections.shuffle(deck);
    }

    /**
     * Draw one card from the top of the deck
     *
     * @return the drawn card
     */
    public Card draw() {
        return deck.pop();
    }
}
