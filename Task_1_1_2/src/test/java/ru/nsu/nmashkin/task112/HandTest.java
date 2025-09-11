package ru.nsu.nmashkin.task112;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class HandTest {

    @Test
    void addCard() {
        Hand hand = new Hand(false);
        hand.addCard(new Card(42));
        assertEquals("[Five of Diamonds (5)]", hand.toString());
    }

    @Test
    void addCard_dealer() {
        Hand hand = new Hand(true);
        hand.addCard(new Card(42));
        hand.addCard(new Card(42));
        assertEquals("[Five of Diamonds (5), <hidden card>]", hand.toString());
    }

    @Test
    void sum() {
        Hand hand = new Hand(false);
        hand.addCard(new Card(42));
        hand.addCard(new Card(51));
        assertEquals(16, hand.sum());
    }

    @Test
    void sum_empty() {
        Hand hand = new Hand(false);
        assertEquals(0, hand.sum());
    }

    @Test
    void rebalance() {
        Hand hand = new Hand(false);
        hand.addCard(new Card(42));
        hand.addCard(new Card(51));
        assertFalse(hand.rebalance());
        assertEquals(6, hand.sum());
    }

    @Test
    void rebalance_no_rebalance() {
        Hand hand = new Hand(false);
        hand.addCard(new Card(42));
        hand.addCard(new Card(50));
        assertTrue(hand.rebalance());
        assertEquals(15, hand.sum());
    }

    @Test
    void enableDealer() {
        Hand hand = new Hand(true);
        hand.addCard(new Card(42));
        hand.addCard(new Card(51));
        hand.enableDealer();
        assertNotEquals("<hidden card>", hand.toString());
    }

    @Test
    void enableDealer_empty() {
        Hand hand = new Hand(true);
        hand.enableDealer();
        assertEquals("[]", hand.toString());
    }

    @Test
    void getLast() {
        Hand hand = new Hand(false);
        hand.addCard(new Card(42));
        hand.addCard(new Card(51));
        assertEquals(11, hand.getLast().getWorth());
    }

    @Test
    void getLast_empty() {
        Hand hand = new Hand(false);
        assertNull(hand.getLast());
    }
}