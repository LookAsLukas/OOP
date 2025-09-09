package ru.nsu.nmashkin.task112;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Scanner;
import org.junit.jupiter.api.Test;

class RoundTest {

    @Test
    void initial_player_win() {
        Deck deck = new Deck(new int[]{0, 1, 51, 50});
        Scanner in = new Scanner("lol\n1\n0\n0\n0\n");
        Round round = new Round(in, deck);
        assertEquals(Round.RoundResult.PlayerWin, round.round());
    }

    @Test
    void initial_player_draw() {
        Deck deck = new Deck(new int[]{12, 11, 51, 50});
        Scanner in = new Scanner("lol\n1\n0\n0\n0\n");
        Round round = new Round(in, deck);
        assertEquals(Round.RoundResult.Draw, round.round());
    }

    @Test
    void player_turn_lose() {
        Deck deck = new Deck(new int[]{48, 0, 1, 49, 50});
        Scanner in = new Scanner("1\n0\n");
        Round round = new Round(in, deck);
        assertEquals(Round.RoundResult.DealerWin, round.round());
    }

    @Test
    void player_turn_win() {
        Deck deck = new Deck(new int[]{51, 0, 1, 49, 50});
        Scanner in = new Scanner("lol\n1\n0\n0\n0\n");
        Round round = new Round(in, deck);
        assertEquals(Round.RoundResult.PlayerWin, round.round());
    }

    @Test
    void player_turn_draw() {
        Deck deck = new Deck(new int[]{51, 12, 11, 49, 50});
        Scanner in = new Scanner("lol\n1\n0\n0\n0\n");
        Round round = new Round(in, deck);
        assertEquals(Round.RoundResult.Draw, round.round());
    }

    @Test
    void dealer_turn_initial_lose() {
        Deck deck = new Deck(new int[]{12, 11, 49, 50});
        Scanner in = new Scanner("0\n");
        Round round = new Round(in, deck);
        assertEquals(Round.RoundResult.DealerWin, round.round());
    }

    @Test
    void dealer_turn_lose() {
        Deck deck = new Deck(new int[]{5, 0, 10, 39, 50});
        Scanner in = new Scanner("0\n");
        Round round = new Round(in, deck);
        assertEquals(Round.RoundResult.DealerWin, round.round());
    }

    @Test
    void dealer_turn_win() {
        Deck deck = new Deck(new int[]{9, 0, 10, 39, 50});
        Scanner in = new Scanner("0\n");
        Round round = new Round(in, deck);
        assertEquals(Round.RoundResult.PlayerWin, round.round());
    }

    @Test
    void round_win() {
        Deck deck = new Deck(new int[]{11, 7, 49, 50});
        Scanner in = new Scanner("0\n");
        Round round = new Round(in, deck);
        assertEquals(Round.RoundResult.PlayerWin, round.round());
    }

    @Test
    void round_lose() {
        Deck deck = new Deck(new int[]{11, 7, 40, 50});
        Scanner in = new Scanner("0\n");
        Round round = new Round(in, deck);
        assertEquals(Round.RoundResult.DealerWin, round.round());
    }

    @Test
    void round_draw() {
        Deck deck = new Deck(new int[]{11, 7, 46, 50});
        Scanner in = new Scanner("0\n");
        Round round = new Round(in, deck);
        assertEquals(Round.RoundResult.Draw, round.round());
    }
}