package ru.nsu.nmashkin.task112;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import org.junit.jupiter.api.Test;

class GameTest {

    @Test
    void main() {
        InputStream in = System.in;
        System.setIn(new ByteArrayInputStream("lol\n0\nlol\n1\n0\n1\n0\n0\n".getBytes()));
        Game.main(new String[]{"poplach nemosch"});
        System.setIn(in);

        for (int i = 0; i < 500; i++) {
            in = System.in;
            System.setIn(new ByteArrayInputStream("1\n0\n1\n0\n0\n0\n".getBytes()));
            Game.main(new String[]{"poplach"});
            System.setIn(in);
        }
    }
}