package ru.nsu.nmashkin.task231;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import org.junit.jupiter.api.Test;

class ModelTest {

    @Test
    void move() {
        Model model = new Model(10, 10, 50, Direction.UP,
                25, 20, new HashMap<>(), new HashMap<>());

        for (int i = 0; i < 25; i++) {
            MoveResult res = model.move();

            if (res != MoveResult.NEUTRAL) {
                assertNotEquals(MoveResult.WIN, res);
                break;
            }
        }

        assertTrue(model.getScore() < 20);
    }

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
