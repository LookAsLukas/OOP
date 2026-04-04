package ru.nsu.nmashkin.task231;

import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;
import java.util.Set;
import javax.annotation.processing.Generated;

/**
 * .
 */
public class Model {
    private final Random random = new Random();
    private final int gridWidth;
    private final int gridHeight;
    private final int cellSize;
    private final int maxScore;
    private final Deque<SnakePart> snake = new LinkedList<>();
    private final Set<Food> foods = new HashSet<>();
    private Direction direction;
    private Direction requestedDirection;
    private int score = 0;

    /**
     * .
     *
     * @param gridWidth .
     * @param gridHeight .
     * @param cellSize .
     * @param direction .
     * @param foodCount .
     * @param maxScore .
     */
    public Model(int gridWidth, int gridHeight, int cellSize,
                 Direction direction, int foodCount, int maxScore) {
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
        this.cellSize = cellSize;
        this.requestedDirection = direction;
        this.maxScore = maxScore;

        snake.push(new SnakePart(new Point(gridWidth / 2, gridHeight / 2), SnakePartType.HEAD));

        for (int i = 0; i < foodCount; i++) {
            generateNewFood();
        }
    }

    /**
     * Move the snake.
     *
     * @return move result.
     */
    @Generated("Untestable because it is hard")
    public MoveResult move() {
        assert snake.peek() != null;

        this.direction = this.requestedDirection;

        SnakePart currHead = snake.pop();
        Point newHeadCoords = currHead.coords().applyDirection(direction);
        if (isSnake(newHeadCoords) || isOutOfBounds(newHeadCoords)) {
            snake.push(currHead);
            return MoveResult.LOSE;
        }

        snake.push(new SnakePart(currHead.coords(),
                snake.isEmpty() ? SnakePartType.TAIL : SnakePartType.BODY));
        snake.push(new SnakePart(newHeadCoords, SnakePartType.HEAD));

        Food food = getFood(newHeadCoords);
        if (food == null) {
            snake.removeLast();
            if (snake.size() > 1) {
                SnakePart tail = snake.removeLast();
                snake.addLast(new SnakePart(tail.coords(), SnakePartType.TAIL));
            }
        } else {
            score++;
            if (score >= maxScore) {
                return MoveResult.WIN;
            }

            foods.remove(food);
            generateNewFood();
        }

        return MoveResult.NEUTRAL;
    }

    private boolean isSnake(Point point) {
        for (SnakePart part : snake) {
            if (part.coords().equals(point)) {
                return true;
            }
        }
        return false;
    }

    private boolean isOutOfBounds(Point point) {
        return point.x() < 0 || point.x() >= gridWidth
                || point.y() < 0 || point.y() >= gridHeight;
    }

    private Food getFood(Point point) {
        for (Food food : foods) {
            if (point.equals(food.coords())) {
                return food;
            }
        }
        return null;
    }

    /**
     * .
     *
     * @param direction .
     * @return .
     */
    public boolean setDirection(Direction direction) {
        if (direction == Direction.UP && this.direction == Direction.DOWN
                || direction == Direction.DOWN && this.direction == Direction.UP
                || direction == Direction.LEFT && this.direction == Direction.RIGHT
                || direction == Direction.RIGHT && this.direction == Direction.LEFT) {
            return false;
        }

        this.requestedDirection = direction;
        return true;
    }

    private void generateNewFood() {
        int freeCount = 0;
        for (int x = 0; x < gridWidth; x++) {
            for (int y = 0; y < gridHeight; y++) {
                Point point = new Point(x, y);
                if (!isSnake(point) && getFood(point) == null) {
                    freeCount++;
                }
            }
        }

        int pos = random.nextInt(0, freeCount);
        for (int x = 0; x < gridWidth; x++) {
            for (int y = 0; y < gridHeight; y++) {
                Point point = new Point(x, y);
                if (pos == 0) {
                    foods.add(new Food(point));
                    return;
                }

                if (!isSnake(point) && getFood(point) == null) {
                    pos--;
                }
            }
        }
    }

    /**
     * .
     *
     * @return .
     */
    @Generated("Untested because getted")
    public Deque<SnakePart> getSnake() {
        return snake;
    }

    /**
     * .
     *
     * @return .
     */
    @Generated("Untested because getted")
    public int getGridHeight() {
        return gridHeight;
    }

    /**
     * .
     *
     * @return .
     */
    @Generated("Untested because getted")
    public int getGridWidth() {
        return gridWidth;
    }

    /**
     * .
     *
     * @return .
     */
    @Generated("Untested because getted")
    public int getCellSize() {
        return cellSize;
    }

    /**
     * .
     *
     * @return .
     */
    @Generated("Untested because getted")
    public Set<Food> getFoods() {
        return foods;
    }

    /**
     * .
     *
     * @return .
     */
    @Generated("Untested because getted")
    public int getScore() {
        return score;
    }

    /**
     * .
     *
     * @return .
     */
    @Generated("Untested because getted")
    public Direction getDirection() {
        return direction;
    }
}
