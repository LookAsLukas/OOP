package ru.nsu.nmashkin.task113;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import java.util.Queue;

import org.junit.jupiter.api.Test;

class ParserTest {

    @Test
    void parseString_jibberish() {
        Queue<Token> reversePolish = Parser.parseString(" ++*(+)()1.23dfs;\"'");
        assertNull(reversePolish);
    }

    @Test
    void parseString_incorrect_brackets() {
        Queue<Token> reversePolish = Parser.parseString(")");
        assertNull(reversePolish);
    }

    @Test
    void parseString_num_end() {
        Queue<Token> reversePolish = Parser.parseString("(12.3");
        assertNull(reversePolish);
    }

    @Test
    void parseString_var_end() {
        Queue<Token> reversePolish = Parser.parseString("(x0");
        assertNull(reversePolish);
    }

    @Test
    void parseString_normal() {
        Queue<Token> reversePolish = Parser.parseString("x+y");
        assertEquals("x", reversePolish.poll().getValue());
        assertEquals("y", reversePolish.poll().getValue());
        assertEquals("+", reversePolish.poll().getValue());
        assertEquals(0, reversePolish.size());
    }
}