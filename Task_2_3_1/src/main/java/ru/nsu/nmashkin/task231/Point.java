package ru.nsu.nmashkin.task231;

import javax.annotation.processing.Generated;

/**
 * .
 */
public record Point(int x, int y) {
    /**
     * Needed for Model::move().
     *
     * @param direction .
     * @return new Point.
     */
    public Point applyDirection(Direction direction) {
        return switch (direction) {
            case UP -> new Point(x, y - 1);
            case DOWN -> new Point(x, y + 1);
            case LEFT -> new Point(x - 1, y);
            case RIGHT -> new Point(x + 1, y);
        };
    }
}
