package ru.nsu.nmashkin.task112;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeckTest {

    @Test
    void draw() {
        Deck deck = new Deck();
        int sum = 0;
        for (int i = 0; i < 52; i++) {
            sum += deck.draw().getWorth();
        }
        assert sum == 380;
    }
}