package ru.nsu.nmashkin.task231;

import javafx.scene.paint.Color;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class EvilBotTest {

    @Test
    void move() {
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

        bot.move(null);

        assertEquals(new Point(2, 3), bot.head().coords());
    }

    @Test
    void move_loser() {
        Set<Food> foods = new HashSet<>();
        Set<Obstacle> obstacles = new HashSet<>();
        obstacles.add(new GridBorder(1, 1));
        Snake player = new Player(new Point(4, 4), null, obstacles);
        PlayerRacerLogic playerRacerLogic = new PlayerRacerLogic(foods, player);
        Snake bot = new EvilBot(new Point(0, 0), null,
                obstacles, playerRacerLogic);

        assertFalse(bot.move(null));
    }

    @Test
    void hitObstacle() {
        Set<Food> foods = new HashSet<>();
        Set<Obstacle> obstacles = new HashSet<>();
        obstacles.add(new GridBorder(1, 1));
        Snake player = new Player(new Point(4, 4), null, obstacles);
        PlayerRacerLogic playerRacerLogic = new PlayerRacerLogic(foods, player);
        Snake bot = new EvilBot(new Point(0, 0), null,
                obstacles, playerRacerLogic);

        assertTrue(bot.hitObstacle(new Point(1, 0)));
    }

    @Test
    void eatFood() {
        Set<Food> foods = new HashSet<>();
        foods.add(new Food(new Point(2, 1)));
        Set<Obstacle> obstacles = new HashSet<>();
        obstacles.add(new GridBorder(5, 5));
        Snake player = new Player(new Point(4, 4), null, obstacles);
        PlayerRacerLogic playerRacerLogic = new PlayerRacerLogic(foods, player);
        Snake bot = new EvilBot(new Point(2, 2), null,
                obstacles, playerRacerLogic);

        bot.move(null);

        assertNull(bot.eatFood(foods));
    }

    @Test
    void eatFood_shrink() {
        Set<Food> foods = new HashSet<>();
        Set<Obstacle> obstacles = new HashSet<>();
        obstacles.add(new GridBorder(5, 5));
        Snake player = new Player(new Point(4, 4), null, obstacles);
        PlayerRacerLogic playerRacerLogic = new PlayerRacerLogic(foods, player);
        Snake bot = new EvilBot(new Point(2, 2), null,
                obstacles, playerRacerLogic);

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
        Set<Obstacle> obstacles = new HashSet<>();
        obstacles.add(new GridBorder(5, 5));
        Snake player = new Player(new Point(4, 4), null, obstacles);
        PlayerRacerLogic playerRacerLogic = new PlayerRacerLogic(foods, player);
        Snake bot = new EvilBot(new Point(2, 2), coloring,
                obstacles, playerRacerLogic);

        bot.kill();

        assertTrue(bot.isDead());
        assertTrue(coloring.values().stream().allMatch(color -> color == Color.GRAY));
    }
}