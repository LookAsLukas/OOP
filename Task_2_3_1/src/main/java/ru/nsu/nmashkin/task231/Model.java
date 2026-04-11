package ru.nsu.nmashkin.task231;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import javafx.scene.paint.Color;

/**
 * .
 */
public class Model {
    private final Random random = new Random();
    private final int gridWidth;
    private final int gridHeight;
    private final int cellSize;
    private final int maxScore;
    private final Snake player;
    private final Snake bot;
    private final Set<Food> foods = new HashSet<>();
    private final Set<Point> freeCells = new HashSet<>();
    private final Set<Obstacle> obstacles = new HashSet<>();
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

        obstacles.add(new GridBorder(gridWidth, gridHeight));

        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
        this.cellSize = cellSize;
        this.requestedDirection = direction;
        this.maxScore = maxScore;

        Point snakeStart = new Point(gridWidth / 4, gridHeight / 2);
        player = new Player(snakeStart, snakeColoring, obstacles);
        freeCells.remove(snakeStart);

        Point botStart = new Point(3 * gridWidth / 4, gridHeight / 2);
        bot = new PeacefulBot(botStart, botColoring, obstacles, new GeneralDirectionLogic(foods));
        freeCells.remove(botStart);

        for (int i = 0; i < foodCount; i++) {
            generateNewFood();
        }
    }

    /**
     * Move the snake and the bot.
     *
     * @return move result.
     */
    public MoveResult move() {
        direction = requestedDirection;

        if (player.move(direction)) {
            freeCells.remove(player.head().coords());
        } else {
            player.kill();
        }

        if (bot.move(null)) {
            freeCells.remove(bot.head().coords());
        } else {
            bot.kill();
        }

        if (bot.isDead() && player.isDead()) {
            return MoveResult.TIE;
        }

        Point freedUp = player.eatFood(foods);
        if (freedUp != null) {
            freeCells.remove(freedUp);
        } else if (!player.isDead()) {
            score++;
            if (score >= maxScore) {
                return MoveResult.WIN;
            }
            generateNewFood();
        }

        freedUp = bot.eatFood(foods);
        if (freedUp != null) {
            freeCells.remove(freedUp);
        } else if (!bot.isDead()) {
            botScore++;
            if (botScore >= maxScore) {
                return MoveResult.LOSE;
            }
            generateNewFood();
        }

        return MoveResult.NEUTRAL;
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
        if (freeCells.isEmpty()) {
            return;
        }

        Point newFoodCell = freeCells.toArray(Point[]::new)[random.nextInt(freeCells.size())];
        foods.add(new Food(newFoodCell));
        freeCells.remove(newFoodCell);
    }

    /**
     * .
     *
     * @return .
     */
    public Snake getSnake() {
        return player;
    }

    /**
     * .
     *
     * @return .
     */
    public Snake getBot() {
        return bot;
    }

    /**
     * .
     *
     * @return .
     */
    public int getGridHeight() {
        return gridHeight;
    }

    /**
     * .
     *
     * @return .
     */
    public int getGridWidth() {
        return gridWidth;
    }

    /**
     * .
     *
     * @return .
     */
    public int getCellSize() {
        return cellSize;
    }

    /**
     * .
     *
     * @return .
     */
    public Set<Food> getFoods() {
        return foods;
    }

    /**
     * .
     *
     * @return .
     */
    public int getScore() {
        return score;
    }

    /**
     * .
     *
     * @return .
     */
    public Direction getDirection() {
        return direction;
    }
}
