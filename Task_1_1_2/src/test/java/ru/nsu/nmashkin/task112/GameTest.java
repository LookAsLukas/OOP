package ru.nsu.nmashkin.task112;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    @Test
    void main() {
        InputStream in = System.in;
        System.setIn(new ByteArrayInputStream("1\n0\n1\n0\n0\n".getBytes()));
        Game.main(null);
        System.setIn(in);
    }
}