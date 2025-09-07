package ru.nsu.nmashkin.task112;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;
import org.junit.jupiter.api.Test;

class GameTest {

    @Test
    void main() {
        InputStream in = System.in;
        System.setIn(new ByteArrayInputStream("lol\n0\nlol\n1\n0\n1\n0\n0\n".getBytes()));
        Game.main(null);
        System.setIn(in);
    }

    @Test
    void round() {
        Scanner in = new Scanner(new ByteArrayInputStream("lol\n1\n0\n0\n0\n".getBytes()));
        Game.round(in);
        in.close();

        for (int i = 0; i < 500; i++) {
            in = new Scanner(new ByteArrayInputStream("1\n0\n0\n0\n".getBytes()));
            Game.round(in);
            in.close();
        }
    }
}