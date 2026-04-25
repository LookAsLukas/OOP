package ru.nsu.nmashkin.task231;

import javax.annotation.processing.Generated;

/**
 * .
 */
@Generated("Nothing to test")
public record SnakePart(Point coords, SnakePartType type) implements Obstacle {
    @Override
    public boolean isHit(Point point) {
        return point.equals(coords);
    }
}
