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
public class PlayerRacerLogic implements BotLogic {
    private final Set<Food> foods;
    private final HashMap<Direction, Point> variants = new HashMap<>();
    private final Snake player;
    private Snake bot;

    /**
     * .
     *
     * @param foods .
     * @param player .
     */
    public PlayerRacerLogic(Set<Food> foods, Snake player) {
        this.foods = foods;
        this.player = player;
    }

    /**
     * Finds closest food to player and goes there.
     *
     * @return .
     */
    @Override
    public Direction nextMove() {
        assembleVariants();
        if (variants.isEmpty()) {
            return Direction.UP; // Loser
        }

        Point playerHead = player.head().coords();
        Point playerGoal = foods.stream().map(Food::coords)
                .min(Comparator.comparingDouble(playerHead::distance)).orElse(playerHead);

        return variants.keySet().stream()
                .map(dir -> new Pair<>(playerGoal.distance(variants.get(dir)), dir))
                .min(Comparator.comparing(Pair::getKey)).get().getValue();
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

    /**
     * .
     *
     * @param bot .
     */
    @Override
    public void setBot(Snake bot) {
        this.bot = bot;
    }
}
