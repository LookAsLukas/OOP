package ru.nsu.nmashkin.task231;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.Test;

class PeacefulBotTest {

    @Test
    void move_far() {
        Set<Food> foods = new HashSet<>();
        foods.add(new Food(new Point(0, 0)));
        foods.add(new Food(new Point(2, 0)));
        foods.add(new Food(new Point(4, 0)));
        GeneralDirectionLogic generalDirectionLogic = new GeneralDirectionLogic(foods);
        Set<Obstacle> obstacles = new HashSet<>();
        obstacles.add(new GridBorder(5, 5));
        Snake bot = new PeacefulBot(new Point(2, 4), null,
                obstacles, generalDirectionLogic);

        bot.move(null);

        assertEquals(new Point(2, 3), bot.head().coords());
    }

    @Test
    void move_close() {
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

        bot.move(null);

        assertEquals(new Point(2, 1), bot.head().coords());
    }

    @Test
    void move_loser() {
        Set<Food> foods = new HashSet<>();
        GeneralDirectionLogic generalDirectionLogic = new GeneralDirectionLogic(foods);
        Set<Obstacle> obstacles = new HashSet<>();
        obstacles.add(new GridBorder(1, 1));
        Snake bot = new PeacefulBot(new Point(0, 0), null,
                obstacles, generalDirectionLogic);

        assertFalse(bot.move(null));
    }

    @Test
    void hitObstacle() {
        Set<Food> foods = new HashSet<>();
        GeneralDirectionLogic generalDirectionLogic = new GeneralDirectionLogic(foods);
        Set<Obstacle> obstacles = new HashSet<>();
        obstacles.add(new GridBorder(1, 1));
        Snake bot = new PeacefulBot(new Point(0, 0), null,
                obstacles, generalDirectionLogic);

        assertTrue(bot.hitObstacle(new Point(1, 0)));
    }

    @Test
    void eatFood() {
        Set<Food> foods = new HashSet<>();
        foods.add(new Food(new Point(2, 1)));
        GeneralDirectionLogic generalDirectionLogic = new GeneralDirectionLogic(foods);
        Set<Obstacle> obstacles = new HashSet<>();
        obstacles.add(new GridBorder(5, 5));
        Snake bot = new PeacefulBot(new Point(2, 2), null,
                obstacles, generalDirectionLogic);

        bot.move(null);

        assertNull(bot.eatFood(foods));
    }

    @Test
    void eatFood_shrink() {
        Set<Food> foods = new HashSet<>();
        GeneralDirectionLogic generalDirectionLogic = new GeneralDirectionLogic(foods);
        Set<Obstacle> obstacles = new HashSet<>();
        obstacles.add(new GridBorder(5, 5));
        Snake bot = new PeacefulBot(new Point(2, 2), null,
                obstacles, generalDirectionLogic);

        bot.move(null);

        assertEquals(new Point(2, 2), bot.eatFood(foods));
    }

    @Test
    void kill() {
        HashMap<SnakePartType, Color> coloring = new HashMap<>();
        coloring.put(SnakePartType.HEAD, Color.DARKGREEN);
        coloring.put(SnakePartType.BODY, Color.GREEN);
        coloring.put(SnakePartType.TAIL, Color.LIGHTGREEN);

        Set<Food> foods = new HashSet<>();
        GeneralDirectionLogic generalDirectionLogic = new GeneralDirectionLogic(foods);
        Set<Obstacle> obstacles = new HashSet<>();
        obstacles.add(new GridBorder(5, 5));
        Snake bot = new PeacefulBot(new Point(2, 2), coloring,
                obstacles, generalDirectionLogic);

        bot.kill();

        assertTrue(bot.isDead());
        assertTrue(coloring.values().stream().allMatch(color -> color == Color.GRAY));
    }
}