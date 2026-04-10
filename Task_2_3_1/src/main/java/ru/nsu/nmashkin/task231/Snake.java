package ru.nsu.nmashkin.task231;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import javafx.scene.paint.Color;
import javax.annotation.processing.Generated;

/**
 * .
 */
public class Snake {
    private final Deque<SnakePart> snake = new LinkedList<>();
    private final HashMap<SnakePartType, Color> coloring;
    private final int gridWidth;
    private final int gridHeight;
    private boolean dead = false;

    /**
     * .
     *
     * @param position .
     * @param coloring .
     * @param gridWidth .
     * @param gridHeight .
     */
    public Snake(Point position, HashMap<SnakePartType, Color> coloring,
                 int gridWidth, int gridHeight) {
        this.coloring = coloring;
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;

        snake.push(new SnakePart(position, SnakePartType.HEAD));
    }

    /**
     * Move the snake in the direction.
     * Snake grows with movement.
     *
     * @param direction .
     * @return true if successful.
     */
    public boolean move(Direction direction) {
        assert snake.peek() != null;

        if (dead) {
            return true;
        }

        SnakePart currHead = snake.pop();
        Point newHeadCoords = currHead.coords().applyDirection(direction);
        if (isSnake(newHeadCoords) || isOutOfBounds(newHeadCoords)) {
            snake.push(currHead);
            return false;
        }

        snake.push(new SnakePart(currHead.coords(),
                snake.isEmpty() ? SnakePartType.TAIL : SnakePartType.BODY));
        snake.push(new SnakePart(newHeadCoords, SnakePartType.HEAD));
        return true;
    }

    /**
     * Cut one from tail.
     */
    public void shrink() {
        snake.removeLast();
        if (snake.size() > 1) {
            SnakePart tail = snake.removeLast();
            snake.addLast(new SnakePart(tail.coords(), SnakePartType.TAIL));
        }
    }

    /**
     * .
     *
     * @return .
     */
    @Generated("Untested because getter")
    public SnakePart head() {
        assert snake.peek() != null;

        return snake.peek();
    }

    /**
     * .
     *
     * @return .
     */
    @Generated("Untested because getter")
    public SnakePart tail() {
        assert snake.peek() != null;

        return snake.peekLast();
    }

    /**
     * .
     *
     * @param point .
     * @return .
     */
    public boolean isSnake(Point point) {
        return snake.stream().anyMatch(part -> part.coords().equals(point));
    }

    /**
     * .
     *
     * @param point .
     * @return .
     */
    public boolean isOutOfBounds(Point point) {
        return point.x() < 0 || point.x() >= gridWidth
                || point.y() < 0 || point.y() >= gridHeight;
    }

    /**
     * .
     *
     * @return .
     */
    @Generated("Untested because getter")
    public Deque<SnakePart> getParts() {
        return snake;
    }

    /**
     * .
     *
     * @return .
     */
    @Generated("Untested because getter")
    public HashMap<SnakePartType, Color> getColoring() {
        return coloring;
    }

    /**
     * .
     */
    public void kill() {
        dead = true;
        coloring.replaceAll((partType, color) -> Color.GRAY);
    }

    /**
     * .
     *
     * @return .
     */
    @Generated("Untested because getter")
    public boolean isDead() {
        return dead;
    }
}
