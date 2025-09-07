package ru.nsu.nmashkin.task112;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

class GameTest {

    @Test
    void main() {
        InputStream in = System.in;
        System.setIn(new ByteArrayInputStream("1\n0\n1\n0\n0\n".getBytes()));
        Game.main(null);
        System.setIn(in);
    }

    @Test
    void round() {
        InputStream in = System.in;
        System.setIn(new ByteArrayInputStream("1\n0\n".getBytes()));
        Game.round();
        System.setIn(in);
    }
}