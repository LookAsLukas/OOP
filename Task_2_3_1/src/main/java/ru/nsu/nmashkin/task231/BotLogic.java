package ru.nsu.nmashkin.task231;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Set;
import javafx.util.Pair;

/**
 * .
 */
public class BotLogic {
    private final Snake bot;
    private final Set<Food> foods;
    HashMap<Direction, Point> variants = new HashMap<>();

    /**
     * .
     *
     * @param bot .
     * @param foods .
     */
    public BotLogic(Snake bot, Set<Food> foods) {
        this.bot = bot;
        this.foods = foods;
    }

    /**
     * If far from foods, try to go in the general direction towards them.
     * If close, go to the closest.
     *
     * @return .
     */
    public Direction getNextDirection() {
        assembleVariants();
        if (variants.isEmpty()) {
            return Direction.UP; // Loser
        }

        return variants.keySet().stream()
                .map(direction -> new Pair<>(foodDistanceSum(direction), direction))
                .min(Comparator.comparing(Pair::getKey)).get().getValue();
    }

    private void assembleVariants() {
        variants.clear();
        Point headCell = bot.head().coords();

        Point newCell = headCell.applyDirection(Direction.UP);
        if (!bot.isSnake(newCell) && !bot.isOutOfBounds(newCell)) {
            variants.put(Direction.UP, newCell);
        }

        newCell = headCell.applyDirection(Direction.DOWN);
        if (!bot.isSnake(newCell) && !bot.isOutOfBounds(newCell)) {
            variants.put(Direction.DOWN, newCell);
        }

        newCell = headCell.applyDirection(Direction.RIGHT);
        if (!bot.isSnake(newCell) && !bot.isOutOfBounds(newCell)) {
            variants.put(Direction.RIGHT, newCell);
        }

        newCell = headCell.applyDirection(Direction.LEFT);
        if (!bot.isSnake(newCell) && !bot.isOutOfBounds(newCell)) {
            variants.put(Direction.LEFT, newCell);
        }
    }

    private double foodDistanceSum(Direction direction) {
        double result = 0;
        for (Food food : foods) {
            double distance = variants.get(direction).distance(food.coords());
            if (distance <= 3) {
                return foods.stream().map(fd -> variants.get(direction).distance(fd.coords()))
                        .min(Comparator.comparingDouble(Double::doubleValue)).get();
            }

            result += distance;
        }
        return result;
    }
}
