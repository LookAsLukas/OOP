package ru.nsu.nmashkin.task112;

import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

class HandTest {

    @Test
    void addCard() {
        Hand hand = new Hand(false);
        hand.addCard(new Card(42));
        assert hand.toString().equals("[Five of Diamonds (5)]");
    }

    @Test
    void sum() {
        Hand hand = new Hand(false);
        hand.addCard(new Card(42));
        hand.addCard(new Card(51));
        assert hand.sum() == 16;
    }

    @Test
    void rebalance() {
        Hand hand = new Hand(false);
        hand.addCard(new Card(42));
        hand.addCard(new Card(51));
        hand.rebalance();
        assert hand.sum() == 6;
    }

    @Test
    void enableDealer() {
        Hand hand = new Hand(true);
        hand.addCard(new Card(42));
        hand.addCard(new Card(51));
        hand.enableDealer();
        assert !hand.getLast().toString().equals("<hidden card>");
    }

    @Test
    void getLast() {
        Hand hand = new Hand(false);
        hand.addCard(new Card(42));
        hand.addCard(new Card(51));
        assert hand.getLast().getWorth() == 11;
    }

    @Test
    void testToString() {
        Hand hand = new Hand(false);
        hand.addCard(new Card(42));
        hand.addCard(new Card(51));
        assert hand.toString().equals("[Five of Diamonds (5), Ace of Diamonds (11)]");
    }
}