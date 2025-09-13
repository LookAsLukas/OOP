package ru.nsu.nmashkin.task113;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Queue;
import org.junit.jupiter.api.Test;

class ParserTest {

    @Test
    void parseString_jibberish() {
        ParserException ex = assertThrows(ParserException.class,
                () -> {
                    Parser.parseString(" ++*(+)()1.23dfs;'");
                });
        assertEquals("Unrecognised character detected", ex.getMessage());
    }

    @Test
    void parseString_incorrect_brackets() {
        ParserException ex = assertThrows(ParserException.class,
                () -> {
                    Parser.parseString(")");
                });
        assertEquals("Invalid brackets pairing in the expression", ex.getMessage());
    }

    @Test
    void parseString_num_end() {
        ParserException ex = assertThrows(ParserException.class,
                () -> {
                    Parser.parseString("(12.3");
                });
        assertEquals("Invalid brackets pairing in the expression", ex.getMessage());
    }

    @Test
    void parseString_var_end() {
        ParserException ex = assertThrows(ParserException.class,
                () -> {
                    Parser.parseString("(x0");
                });
        assertEquals("Invalid brackets pairing in the expression", ex.getMessage());
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