package ru.nsu.nmashkin.task231;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class PointTest {

    @Test
    void applyDirection() {
        Point p = new Point(6, 7);
        p.applyDirection(Direction.UP);
        p.applyDirection(Direction.RIGHT);
        p.applyDirection(Direction.LEFT);
        p.applyDirection(Direction.DOWN);
        assertEquals(new Point(6, 7), p);
    }
}
