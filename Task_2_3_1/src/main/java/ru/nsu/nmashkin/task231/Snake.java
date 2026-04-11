package ru.nsu.nmashkin.task231;

import javafx.scene.paint.Color;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * Snake interface.
 */
public interface Snake {
    /**
     * Move the Snake in the direction.
     *
     * @param direction .
     * @return true if successful.
     */
    boolean move(Direction direction);

    /**
     * Try to eat the food in head position.
     *
     * @return freed up position if the Snake shrunk,
     *         null else.
     */
    Point eatFood(Set<Food> foods);

    /**
     * Get collection of snake parts.
     *
     * @return .
     */
    Collection<SnakePart> getParts();

    /**
     * Get snake head.
     *
     * @return .
     */
    SnakePart head();

    /**
     * Get snake tail.
     *
     * @return .
     */
    SnakePart tail();

    /**
     * Get snake coloring.
     *
     * @return .
     */
    Map<SnakePartType, Color> getColoring();

    /**
     * Does the point hit any obstacles.
     *
     * @param point .
     * @return true is yes.
     */
    boolean hitObstacle(Point point);

    /**
     * Disables the Snake.
     */
    void kill();

    /**
     * Is the Snake dead.
     *
     * @return true if yes.
     */
    boolean isDead();
}
