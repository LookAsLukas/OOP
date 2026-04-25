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

class PlayerTest {

    @Test
    void move() {
        Set<Obstacle> obstacles = new HashSet<>();
        obstacles.add(new GridBorder(5, 5));
        Snake player = new Player(new Point(2, 2), null, obstacles);

        boolean result = true;
        result &= player.move(Direction.UP);
        result &= player.move(Direction.RIGHT);
        result &= player.move(Direction.DOWN);

        assertTrue(result);
        assertFalse(player.move(Direction.LEFT));
    }

    @Test
    void hitObstacle() {
        Set<Obstacle> obstacles = new HashSet<>();
        obstacles.add(new GridBorder(5, 5));
        Snake player = new Player(new Point(2, 2), null, obstacles);

        assertTrue(player.hitObstacle(new Point(5, 5)));
    }

    @Test
    void eatFood() {
        Set<Obstacle> obstacles = new HashSet<>();
        obstacles.add(new GridBorder(5, 5));
        Snake player = new Player(new Point(2, 2), null, obstacles);
        Set<Food> foods = new HashSet<>();
        foods.add(new Food(new Point(2, 1)));

        player.move(Direction.UP);

        assertNull(player.eatFood(foods));
    }

    @Test
    void eatFood_shrink() {
        Set<Obstacle> obstacles = new HashSet<>();
        obstacles.add(new GridBorder(5, 5));
        Snake player = new Player(new Point(2, 2), null, obstacles);
        Set<Food> foods = new HashSet<>();

        player.move(Direction.UP);

        assertEquals(new Point(2, 2), player.eatFood(foods));
    }

    @Test
    void kill() {
        HashMap<SnakePartType, Color> coloring = new HashMap<>();
        coloring.put(SnakePartType.HEAD, Color.DARKGREEN);
        coloring.put(SnakePartType.BODY, Color.GREEN);
        coloring.put(SnakePartType.TAIL, Color.LIGHTGREEN);

        Set<Obstacle> obstacles = new HashSet<>();
        obstacles.add(new GridBorder(5, 5));
        Snake player = new Player(new Point(2, 2), coloring, obstacles);

        player.kill();

        assertTrue(player.isDead());
        assertTrue(coloring.values().stream().allMatch(color -> color == Color.GRAY));
    }
}