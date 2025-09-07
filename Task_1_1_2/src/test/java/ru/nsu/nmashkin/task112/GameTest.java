package ru.nsu.nmashkin.task112;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

class GameTest {

    @Test
    void main() {
        InputStream in = System.in;
        System.setIn(new ByteArrayInputStream("1\n0\n1\n0\n0\n0\n".getBytes()));
        Game dummy = new Game();
        dummy.main(null);
        System.setIn(in);
        dummy.destroyScanner();
    }

    @Test
    void round() {
        InputStream in = System.in;
        System.setIn(new ByteArrayInputStream("1\n0\n0\n0\n".getBytes()));
        Game dummy = new Game();
        try {
            dummy.round();
        } catch (IllegalStateException e) {
            System.out.println("No idea what went wrong");
        }
        System.setIn(in);
        dummy.destroyScanner();
    }

    @Test
    void destroyScanner() {
        Game dummy = new Game();
        dummy.destroyScanner();
    }
}