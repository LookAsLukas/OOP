package ru.nsu.nmashkin.task113;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import java.util.Queue;
import org.junit.jupiter.api.Test;

class ParserTest {

    @Test
    void parseString_jibberish() {
        Queue<Token> RPN = Parser.parseString(" ++*(+)()1.23dfs;\"'");
        assertNull(RPN);
    }

    @Test
    void parseString_incorrect_brackets() {
        Queue<Token> RPN = Parser.parseString(")");
        assertNull(RPN);
    }

    @Test
    void parseString_num_end() {
        Queue<Token> RPN = Parser.parseString("(12.3");
        assertNull(RPN);
    }

    @Test
    void parseString_var_end() {
        Queue<Token> RPN = Parser.parseString("(x0");
        assertNull(RPN);
    }

    @Test
    void parseString_normal() {
        Queue<Token> RPN = Parser.parseString("x+y");
        assertEquals("x", RPN.poll().getValue());
        assertEquals("y", RPN.poll().getValue());
        assertEquals("+", RPN.poll().getValue());
        assertEquals(0, RPN.size());
    }
}