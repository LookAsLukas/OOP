package ru.nsu.nmashkin.task231;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class PlayerRacerLogicTest {

    @Test
    void nextMove() {
        Set<Food> foods = new HashSet<>();
        foods.add(new Food(new Point(0, 0)));
        foods.add(new Food(new Point(2, 0)));
        foods.add(new Food(new Point(4, 0)));
        Set<Obstacle> obstacles = new HashSet<>();
        obstacles.add(new GridBorder(5, 5));
        Snake player = new Player(new Point(4, 4), null, obstacles);
        PlayerRacerLogic playerRacerLogic = new PlayerRacerLogic(foods, player);
        Snake bot = new PeacefulBot(new Point(2, 4), null,
                obstacles, playerRacerLogic);

        assertEquals(Direction.UP, playerRacerLogic.nextMove());
    }

    @Test
    void nextMove_loser() {
        Set<Food> foods = new HashSet<>();
        Set<Obstacle> obstacles = new HashSet<>();
        obstacles.add(new GridBorder(1, 1));
        Snake player = new Player(new Point(4, 4), null, obstacles);
        PlayerRacerLogic playerRacerLogic = new PlayerRacerLogic(foods, player);
        Snake bot = new EvilBot(new Point(0, 0), null,
                obstacles, playerRacerLogic);

        assertEquals(Direction.UP, playerRacerLogic.nextMove());
    }
}