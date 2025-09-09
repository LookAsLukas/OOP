package ru.nsu.nmashkin.task112;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class CardTest {

    @Test
    void hide() {
        Card card = new Card(42);
        card.hide();
        assertTrue(card.isHidden());
    }

    @Test
    void hide_hidden() {
        Card card = new Card(42);
        card.hide();
        card.hide();
        assertTrue(card.isHidden());
    }

    @Test
    void reveal() {
        Card card = new Card(42);
        card.hide();
        card.reveal();
        assertEquals(new Card(42), card);
    }

    @Test
    void reveal_revealed() {
        Card card = new Card(42);
        card.reveal();
        assertEquals(new Card(42), card);
    }

    @Test
    void getWorth() {
        Card card = new Card(42);
        assertEquals(5, card.getWorth());
    }

    @Test
    void downgrade() {
        Card card = new Card(51);
        card.downgrade();
        assertEquals(1, card.getWorth());
    }

    @Test
    void downgrade_downgraded() {
        Card card = new Card(51);
        card.downgrade();
        card.downgrade();
        assertEquals(1, card.getWorth());
    }

    @Test
    void downgrade_non_downgradable() {
        Card card = new Card(42);
        card.downgrade();
        assertEquals(5, card.getWorth());
    }

    @Test
    void constructor() {
        Card card = new Card(-1);
        assertEquals(0, card.getWorth());
    }
}