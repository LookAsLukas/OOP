package ru.nsu.nmashkin.task231;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import javafx.scene.paint.Color;
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
    private final Snake snake;
    private final Snake bot;
    private final BotLogic botLogic;
    private final Set<Food> foods = new HashSet<>();
    private final Set<Point> freeCells = new HashSet<>();
    private Direction direction;
    private Direction requestedDirection;
    private int score = 0;
    private int botScore = 0;

    /**
     * .
     *
     * @param gridWidth  .
     * @param gridHeight .
     * @param cellSize   .
     * @param direction  .
     * @param foodCount  .
     * @param maxScore   .
     */
    public Model(int gridWidth, int gridHeight, int cellSize,
                 Direction direction, int foodCount, int maxScore,
                 HashMap<SnakePartType, Color> snakeColoring,
                 HashMap<SnakePartType, Color> botColoring) {
        for (int x = 0; x < gridWidth; x++) {
            for (int y = 0; y < gridHeight; y++) {
                Point point = new Point(x, y);
                freeCells.add(point);
            }
        }

        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
        this.cellSize = cellSize;
        this.requestedDirection = direction;
        this.maxScore = maxScore;

        Point snakeStart = new Point(gridWidth / 4, gridHeight / 2);
        snake = new Snake(snakeStart, snakeColoring, gridWidth, gridHeight);
        freeCells.remove(snakeStart);

        Point botStart = new Point(3 * gridWidth / 4, gridHeight / 2);
        bot = new Snake(botStart, botColoring, gridWidth, gridHeight);
        freeCells.remove(botStart);

        for (int i = 0; i < foodCount; i++) {
            generateNewFood();
        }

        botLogic = new BotLogic(bot, foods);
    }

    /**
     * Move the snake and the bot.
     *
     * @return move result.
     */
    @Generated("Untestable because heavily affected by foods (rng)")
    public MoveResult move() {
        direction = requestedDirection;

        if (!snake.move(direction)) {
            snake.kill();
        }

        if (!bot.move(botLogic.getNextDirection())) {
            bot.kill();
        }

        if (bot.isDead() && snake.isDead()) {
            return MoveResult.TIE;
        }

        if (!snake.isDead()) {
            Point newHeadCoords = snake.head().coords();
            freeCells.remove(newHeadCoords);
            Food food = getFood(newHeadCoords);
            if (food == null) {
                freeCells.add(snake.tail().coords());
                snake.shrink();
            } else {
                score++;
                if (score >= maxScore) {
                    return MoveResult.WIN;
                }

                foods.remove(food);
                generateNewFood();
            }
        }

        if (!bot.isDead()) {
            Point newBotHeadCoords = bot.head().coords();
            freeCells.remove(newBotHeadCoords);
            Food food = getFood(newBotHeadCoords);
            if (food == null) {
                freeCells.add(bot.tail().coords());
                bot.shrink();
            } else {
                botScore++;
                if (botScore >= maxScore) {
                    return MoveResult.LOSE;
                }

                foods.remove(food);
                generateNewFood();
            }
        }

        return MoveResult.NEUTRAL;
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
     */
    public void setDirection(Direction direction) {
        if (direction == Direction.UP && this.direction == Direction.DOWN
                || direction == Direction.DOWN && this.direction == Direction.UP
                || direction == Direction.LEFT && this.direction == Direction.RIGHT
                || direction == Direction.RIGHT && this.direction == Direction.LEFT) {
            return;
        }

        this.requestedDirection = direction;
    }

    private void generateNewFood() {
        Point newFoodCell = freeCells.toArray(Point[]::new)[random.nextInt(freeCells.size())];
        foods.add(new Food(newFoodCell));
        freeCells.remove(newFoodCell);
    }

    /**
     * .
     *
     * @return .
     */
    @Generated("Untested because getter")
    public Snake getSnake() {
        return snake;
    }

    /**
     * .
     *
     * @return .
     */
    @Generated("Untested because getter")
    public Snake getBot() {
        return bot;
    }

    /**
     * .
     *
     * @return .
     */
    @Generated("Untested because getter")
    public int getGridHeight() {
        return gridHeight;
    }

    /**
     * .
     *
     * @return .
     */
    @Generated("Untested because getter")
    public int getGridWidth() {
        return gridWidth;
    }

    /**
     * .
     *
     * @return .
     */
    @Generated("Untested because getter")
    public int getCellSize() {
        return cellSize;
    }

    /**
     * .
     *
     * @return .
     */
    @Generated("Untested because getter")
    public Set<Food> getFoods() {
        return foods;
    }

    /**
     * .
     *
     * @return .
     */
    @Generated("Untested because getter")
    public int getScore() {
        return score;
    }

    /**
     * .
     *
     * @return .
     */
    @Generated("Untested because getter")
    public Direction getDirection() {
        return direction;
    }
}
