package ru.nsu.nmashkin.task112;

import java.util.Collections;
import java.util.Stack;

public class Deck {
    private final Stack<Card> deck;

    public Deck() {
        deck = new Stack<>();
        for (int i = 0; i < 52; i++) {
            deck.push(new Card(i));
        }

        Collections.shuffle(deck);
    }

    public Card draw() {
        return deck.pop();
    }
}
