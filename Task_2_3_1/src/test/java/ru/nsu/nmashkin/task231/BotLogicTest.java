package ru.nsu.nmashkin.task231;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class BotLogicTest {

    @Test
    void getNextDirection_far() {
        Set<Food> foods = new HashSet<>();
        foods.add(new Food(new Point(0, 0)));
        foods.add(new Food(new Point(2, 0)));
        foods.add(new Food(new Point(4, 0)));
        BotLogic botLogic = new BotLogic(
                new Snake(new Point(2, 4), new HashMap<>(), 5, 5), foods);

        assertEquals(Direction.UP, botLogic.getNextDirection());
    }

    @Test
    void getNextDirection_close() {
        Set<Food> foods = new HashSet<>();
        foods.add(new Food(new Point(2, 1)));
        foods.add(new Food(new Point(2, 4)));
        foods.add(new Food(new Point(2, 4)));
        foods.add(new Food(new Point(2, 4)));
        BotLogic botLogic = new BotLogic(
                new Snake(new Point(2, 2), new HashMap<>(), 5, 5), foods);

        assertEquals(Direction.UP, botLogic.getNextDirection());
    }

    @Test
    void getNextDirection_loser() {
        Set<Food> foods = new HashSet<>();
        BotLogic botLogic = new BotLogic(
                new Snake(new Point(0, 0), new HashMap<>(), 1, 1), foods);

        assertEquals(Direction.UP, botLogic.getNextDirection());
    }
}