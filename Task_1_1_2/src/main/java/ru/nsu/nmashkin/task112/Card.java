package ru.nsu.nmashkin.task112;

public class Card {
    private boolean hidden = false;
    private final String name;
    private int worth;

    public Card(int num) {
        worth = Math.min(10, num % 13 + 2) + (num % 13 == 12 ? 1 : 0);

        String[] names = new String[] {
                "Two",
                "Three",
                "Four",
                "Five",
                "Six",
                "Seven",
                "Eight",
                "Nine",
                "Ten",
                "Jack",
                "Queen",
                "King",
                "Ace"
        };
        String[] suits = new String[] {
                "Hearts",
                "Spades",
                "Clubs",
                "Diamonds"
        };
        name = names[num % 13] + " of " + suits[num / 13];
    }

    public void hide() {
        hidden = true;
    }

    public void reveal() {
        hidden = false;
    }

    public int getWorth() {
        return worth;
    }

    public boolean downgrade() {
        if (worth != 11) {
            return false;
        }
        worth = 1;
        return true;
    }

    @Override
    public String toString() {
        if (hidden) {
            return "<hidden card>";
        }
        return name + " (" + worth + ")";
    }
}
