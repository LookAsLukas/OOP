package ru.nsu.nmashkin.task112;

import org.junit.jupiter.api.Test;

class CardTest {

    @Test
    void hide() {
        Card card = new Card(42);
        card.hide();
        assert card.toString().equals("<hidden card>");
    }

    @Test
    void reveal() {
        Card card = new Card(42);
        card.hide();
        card.reveal();
        assert card.toString().equals("Five of Diamonds (5)");
    }

    @Test
    void getWorth() {
        Card card = new Card(42);
        assert card.getWorth() == 5;
    }

    @Test
    void downgrade() {
        Card card = new Card(51);
        card.downgrade();
        assert card.getWorth() == 1;
    }

    @Test
    void testToString() {
        Card card = new Card(42);
        assert card.toString().equals("Five of Diamonds (5)");
    }
}