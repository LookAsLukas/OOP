package ru.nsu.nmashkin.task231;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class GeneralDirectionLogicTest {

    @Test
    void nextMove_far() {
        Set<Food> foods = new HashSet<>();
        foods.add(new Food(new Point(0, 0)));
        foods.add(new Food(new Point(2, 0)));
        foods.add(new Food(new Point(4, 0)));
        GeneralDirectionLogic generalDirectionLogic = new GeneralDirectionLogic(foods);
        Set<Obstacle> obstacles = new HashSet<>();
        obstacles.add(new GridBorder(5, 5));
        Snake bot = new PeacefulBot(new Point(2, 4), null,
                obstacles, generalDirectionLogic);

        assertEquals(Direction.UP, generalDirectionLogic.nextMove());
    }

    @Test
    void nextMove_close() {
        Set<Food> foods = new HashSet<>();
        foods.add(new Food(new Point(2, 1)));
        foods.add(new Food(new Point(2, 4)));
        foods.add(new Food(new Point(2, 4)));
        foods.add(new Food(new Point(2, 4)));
        GeneralDirectionLogic generalDirectionLogic = new GeneralDirectionLogic(foods);
        Set<Obstacle> obstacles = new HashSet<>();
        obstacles.add(new GridBorder(5, 5));
        Snake bot = new PeacefulBot(new Point(2, 2), null,
                obstacles, generalDirectionLogic);

        assertEquals(Direction.UP, generalDirectionLogic.nextMove());
    }

    @Test
    void nextMove_loser() {
        Set<Food> foods = new HashSet<>();
        GeneralDirectionLogic generalDirectionLogic = new GeneralDirectionLogic(foods);
        Set<Obstacle> obstacles = new HashSet<>();
        obstacles.add(new GridBorder(1, 1));
        Snake bot = new PeacefulBot(new Point(0, 0), null,
                obstacles, generalDirectionLogic);

        assertEquals(Direction.UP, generalDirectionLogic.nextMove());
    }
}