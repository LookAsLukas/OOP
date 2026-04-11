package ru.nsu.nmashkin.task231;

import java.util.*;

import javafx.scene.paint.Color;
import javax.annotation.processing.Generated;

public class PeacefulBot implements Snake {
    private final Deque<SnakePart> snake = new LinkedList<>();
    private final HashMap<SnakePartType, Color> coloring;
    private final Set<Obstacle> obstacles;
    private final BotLogic botLogic;
    private boolean dead = false;

    /**
     * .
     *
     * @param position .
     * @param coloring .
     * @param obstacles .
     */
    public PeacefulBot(Point position, HashMap<SnakePartType, Color> coloring,
                       Set<Obstacle> obstacles, BotLogic botLogic) {
        this.coloring = coloring;
        this.obstacles = obstacles;
        this.botLogic = botLogic;

        snake.push(new SnakePart(position, SnakePartType.HEAD));

        botLogic.setBot(this);
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean move(Direction direction) {
        assert snake.peek() != null;

        if (dead) {
            return false;
        }

        if (direction == null) {
            direction = botLogic.nextMove();
        }

        SnakePart currHead = snake.pop();
        Point newHeadCoords = currHead.coords().applyDirection(direction);
        if (hitObstacle(newHeadCoords)) {
            snake.push(currHead);
            return false;
        }

        snake.push(new SnakePart(currHead.coords(),
                snake.isEmpty() ? SnakePartType.TAIL : SnakePartType.BODY));
        snake.push(new SnakePart(newHeadCoords, SnakePartType.HEAD));
        return true;
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean hitObstacle(Point point) {
        Set<Obstacle> newObstacles = new HashSet<>(Set.copyOf(snake));
        newObstacles.addAll(obstacles);
        return newObstacles.stream().anyMatch(obstacle -> obstacle.isHit(point));
    }

    /**
     * @inheritDoc
     */
    @Override
    public Point eatFood(Set<Food> foods) {
        Food foodToEat = foods.stream()
                .filter(food -> food.coords().equals(head().coords()))
                .findFirst().orElse(null);

        if (foodToEat == null) {
            SnakePart freedUp = snake.removeLast();
            if (snake.size() > 1) {
                SnakePart tail = snake.removeLast();
                snake.addLast(new SnakePart(tail.coords(), SnakePartType.TAIL));
            }
            return freedUp.coords();
        }

        foods.remove(foodToEat);
        return null;
    }

    /**
     * @inheritDoc
     */
    @Override
    @Generated("Untested because getter")
    public SnakePart head() {
        assert snake.peek() != null;

        return snake.peek();
    }

    /**
     * @inheritDoc
     */
    @Override
    @Generated("Untested because getter")
    public SnakePart tail() {
        assert snake.peek() != null;

        return snake.peekLast();
    }

    /**
     * @inheritDoc
     */
    @Override
    @Generated("Untested because getter")
    public Deque<SnakePart> getParts() {
        return snake;
    }

    /**
     * @inheritDoc
     */
    @Override
    @Generated("Untested because getter")
    public HashMap<SnakePartType, Color> getColoring() {
        return coloring;
    }

    /**
     * @inheritDoc
     */
    @Override
    public void kill() {
        dead = true;
        coloring.replaceAll((partType, color) -> Color.GRAY);
    }

    /**
     * @inheritDoc
     */
    @Override
    @Generated("Untested because getter")
    public boolean isDead() {
        return dead;
    }
}
