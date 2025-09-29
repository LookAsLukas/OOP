package ru.nsu.nmashkin.task131;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.Test;

class SubstringFinderTest {

    @Test
    void find() {
        List<Long> res = SubstringFinder.find("lolkek.txt", "бла");

        assertEquals(new ArrayList<>(List.of(0L, 9L, 37L)), res);
    }

    @Test
    void find_huge() {
        if (true) return;
        List<Long> expected = new ArrayList<>();
        try (BufferedWriter outw = new BufferedWriter(new FileWriter("test.txt",
                StandardCharsets.UTF_8))) {
            Random random = new Random();
            for (long i = 0; i < 69420; i++) {
                double lol = random.nextDouble();
                if (lol < 0.48) {
                    outw.append("лол");
                } else if (lol < 0.96) {
                    outw.append("кек");
                } else {
                    outw.append("бла");
                    expected.add(i * 3);
                }
            }
        } catch (IOException e) {
            System.err.println("Something went wrong\n");
            return;
        }

        assertEquals(expected, SubstringFinder.find("test.txt", "бла"));

        File file = new File("test.txt");
        file.delete();
    }

    @Test
    void find_error() {
        assertThrows(SubstringFinderException.class, () -> SubstringFinder.find("ligma", "ballz"));
    }

    @Test
    void find_edging() {
        List<Long> expected = new ArrayList<>();
        try (BufferedWriter outw = new BufferedWriter(new FileWriter("test.txt",
                StandardCharsets.UTF_8))) {
            for (long i = 0; i < 4095; i++) {
                outw.append("ф");
            }
            outw.append("бла");
        } catch (IOException e) {
            System.err.println("Something went wrong\n");
        }

        expected.add(4095L);
        assertEquals(expected, SubstringFinder.find("test.txt", "бла"));

        File file = new File("test.txt");
        file.delete();
    }
}