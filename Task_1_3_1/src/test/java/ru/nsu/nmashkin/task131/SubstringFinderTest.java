package ru.nsu.nmashkin.task131;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.Test;

class SubstringFinderTest {
    @Test
    void find_huge() throws IOException {
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
        }

        try (InputStream in = new FileInputStream("test.txt")) {
            assertEquals(expected, SubstringFinder.find(in, "бла"));
        }

        File file = new File("test.txt");
        file.delete();
    }

    @Test
    void find_repeat() throws IOException {
        List<Long> expected = new ArrayList<>();
        try (BufferedWriter outw = new BufferedWriter(new FileWriter("test.txt",
                StandardCharsets.UTF_8))) {
            for (long i = 0; i < 228; i++) {
                if (i < 226) {
                    expected.add(i * 2);
                }
                outw.append(new String(new char[]{0xD80C, 0xDC04}));
            }
        }

        try (InputStream in = new FileInputStream("test.txt")) {
            assertEquals(expected, SubstringFinder.find(in,
                    new String(new char[]{0xD80C, 0xDC04, 0xD80C, 0xDC04, 0xD80C, 0xDC04})));
        } catch (IOException e) {
            System.err.println("Something went horribly wrong\n");
            return;
        }

        File file = new File("test.txt");
        file.delete();
    }

    @Test
    void find_edging() throws IOException{
        List<Long> expected = new ArrayList<>();
        try (BufferedWriter outw = new BufferedWriter(new FileWriter("test.txt",
                StandardCharsets.UTF_8))) {
            for (long i = 0; i < 4095; i++) {
                outw.append("ф");
            }
            outw.append("бла");
        }

        expected.add(4095L);

        try (InputStream in = new FileInputStream("test.txt")) {
            assertEquals(expected, SubstringFinder.find(in, "бла"));
        }

        File file = new File("test.txt");
        file.delete();
    }

    @Test
    void find_string_stream() throws IOException {
        List<Long> expected = new ArrayList<>();
        expected.add(0L);
        InputStream in = new ByteArrayInputStream("lollollolkek".getBytes(StandardCharsets.UTF_8));
        assertEquals(expected, SubstringFinder.find(in, "lollollol"));
    }
}