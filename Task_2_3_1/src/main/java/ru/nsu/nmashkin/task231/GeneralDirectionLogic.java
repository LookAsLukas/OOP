package ru.nsu.nmashkin.task231;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Set;
import java.util.stream.Collectors;
import javafx.util.Pair;

/**
 * .
 */
public class GeneralDirectionLogic implements BotLogic {
    private final Set<Food> foods;
    private final HashMap<Direction, Point> variants = new HashMap<>();
    private Snake bot;

    /**
     * .
     *
     * @param foods .
     */
    public GeneralDirectionLogic(Set<Food> foods) {
        this.foods = foods;
    }

    /**
     * If far from foods, try to go in the general direction towards them.
     * If close, go to the closest.
     *
     * @return .
     */
    @Override
    public Direction nextMove() {
        assembleVariants();
        if (variants.isEmpty()) {
            return Direction.UP; // Loser
        }

        return variants.keySet().stream()
                .map(direction -> new Pair<>(foodDistanceSum(direction), direction))
                .min(Comparator.comparing(Pair::getKey)).get().getValue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setBot(Snake bot) {
        this.bot = bot;
    }

    private void assembleVariants() {
        Point headCell = bot.head().coords();

        variants.clear();
        variants.putAll(Arrays.stream(Direction.values())
                .filter(direction -> !bot.hitObstacle(headCell.applyDirection(direction)))
                .collect(Collectors.toMap(
                        direction -> direction,
                        headCell::applyDirection
                )));
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
