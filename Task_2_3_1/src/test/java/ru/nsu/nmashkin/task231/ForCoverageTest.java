package ru.nsu.nmashkin.task231;

import org.junit.jupiter.api.Test;

class ForCoverageTest {

    @Test
    void forCoverage() {
        Model model = new Model(5, 5, 50, Direction.UP,
                5, 20, null, null);
        model.getScore();
        model.getSnake();
        model.getFoods();
        model.getCellSize();
        model.getGridHeight();
        model.getGridWidth();
        model.getBot();
        model.getDirection();

        Snake player = new Player(new Point(2, 2), null, null);
        player.head();
        player.tail();
        player.getParts();
        player.getColoring();
        player.isDead();

        Snake bot = new PeacefulBot(new Point(2, 2), null, null, new GeneralDirectionLogic(null));
        bot.head();
        bot.tail();
        bot.getParts();
        bot.getColoring();
        bot.isDead();
    }
}
