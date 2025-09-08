package ru.nsu.nmashkin.task112;

import org.junit.jupiter.api.Test;

class HandTest {

    @Test
    void addCard() {
        Hand hand = new Hand(false);
        hand.addCard(new Card(42));
        assert hand.toString().equals("[Five of Diamonds (5)]");
    }

    @Test
    void addCard_dealer() {
        Hand hand = new Hand(true);
        hand.addCard(new Card(42));
        hand.addCard(new Card(42));
        hand.addCard(new Card(42));
        assert hand.toString().equals("[Five of Diamonds (5), <hidden card>, <hidden card>]");
    }

    @Test
    void sum() {
        Hand hand = new Hand(false);
        hand.addCard(new Card(42));
        hand.addCard(new Card(51));
        assert hand.sum() == 16;
    }

    @Test
    void sum_empty() {
        Hand hand = new Hand(false);
        assert hand.sum() == 0;
    }

    @Test
    void rebalance() {
        Hand hand = new Hand(false);
        hand.addCard(new Card(42));
        hand.addCard(new Card(51));
        assert !hand.rebalance();
        assert hand.sum() == 6;
    }

    @Test
    void rebalance_no_rebalance() {
        Hand hand = new Hand(false);
        hand.addCard(new Card(42));
        hand.addCard(new Card(50));
        assert hand.rebalance();
        assert hand.sum() == 15;
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
    void enableDealer_empty() {
        Hand hand = new Hand(true);
        hand.enableDealer();
        assert hand.toString().equals("[]");
    }

    @Test
    void getLast() {
        Hand hand = new Hand(false);
        hand.addCard(new Card(42));
        hand.addCard(new Card(51));
        assert hand.getLast().getWorth() == 11;
    }

    @Test
    void getLast_empty() {
        Hand hand = new Hand(false);
        assert hand.getLast() == null;
    }

    @Test
    void testToString() {
        Hand hand = new Hand(false);
        hand.addCard(new Card(42));
        hand.addCard(new Card(51));
        assert hand.toString().equals("[Five of Diamonds (5), Ace of Diamonds (11)]");
    }
}