package ru.nsu.nmashkin.task231;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

class ModelTest {

    @Test
    void setDirection() {
        Model model = new Model(10, 10, 50, Direction.UP,
                5, 20, new HashMap<>(), new HashMap<>());
        model.move();
        model.setDirection(Direction.RIGHT);
        model.setDirection(Direction.DOWN);
        model.move();
        assertEquals(Direction.RIGHT, model.getDirection());
    }
}
