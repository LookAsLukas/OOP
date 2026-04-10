package ru.nsu.nmashkin.task231;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.Test;

class SnakeTest {

    @Test
    void move() {
        Snake snake = new Snake(
                new Point(2, 2),
                new HashMap<>(),
                5, 5);

        boolean result = true;
        result &= snake.move(Direction.UP);
        result &= snake.move(Direction.RIGHT);
        result &= snake.move(Direction.DOWN);
        assertTrue(result);

        assertFalse(snake.move(Direction.LEFT));
    }

    @Test
    void shrink() {
        Snake snake = new Snake(
                new Point(2, 2),
                new HashMap<>(),
                5, 5);

        snake.move(Direction.UP);
        snake.move(Direction.RIGHT);
        snake.move(Direction.DOWN);
        snake.shrink();

        assertTrue(snake.move(Direction.LEFT));
    }

    @Test
    void isSnake() {
        Snake snake = new Snake(
                new Point(0, 0),
                new HashMap<>(),
                5, 5);

        assertTrue(snake.isSnake(new Point(0, 0)));
        assertFalse(snake.isSnake(new Point(0, 1)));
    }

    @Test
    void isOutOfBounds() {
        Snake snake = new Snake(
                new Point(0, 0),
                new HashMap<>(),
                5, 5);

        assertTrue(snake.isOutOfBounds(new Point(0, 9)));
        assertFalse(snake.isOutOfBounds(new Point(0, 1)));
    }

    @Test
    void kill() {
        HashMap<SnakePartType, Color> coloring = new HashMap<>();
        coloring.put(SnakePartType.HEAD, Color.DARKGREEN);
        coloring.put(SnakePartType.BODY, Color.GREEN);
        coloring.put(SnakePartType.TAIL, Color.LIGHTGREEN);
        Snake snake = new Snake(
                new Point(0, 0),
                coloring,
                5, 5);
        snake.kill();

        assertTrue(snake.isDead());
        assertTrue(coloring.values().stream().allMatch(color -> color == Color.GRAY));
    }
}