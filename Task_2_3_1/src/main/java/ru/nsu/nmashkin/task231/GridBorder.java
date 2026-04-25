package ru.nsu.nmashkin.task231;

/**
 * Grid border.
 *
 * @param gridWidth .
 * @param gridHeight .
 */
public record GridBorder(int gridWidth, int gridHeight) implements Obstacle {
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isHit(Point point) {
        return point.x() < 0 || point.x() >= gridWidth
                || point.y() < 0 || point.y() >= gridHeight;
    }
}
