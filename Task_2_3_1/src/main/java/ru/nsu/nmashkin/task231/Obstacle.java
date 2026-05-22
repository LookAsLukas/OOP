package ru.nsu.nmashkin.task231;

/**
 * Obstacle interface.
 */
public interface Obstacle {
    /**
     * Is the obstacle hit at the point.
     *
     * @param point .
     * @return true if yes.
     */
    boolean isHit(Point point);
}
