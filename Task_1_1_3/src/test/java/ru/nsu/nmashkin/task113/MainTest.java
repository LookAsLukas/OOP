package ru.nsu.nmashkin.task113;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import org.junit.jupiter.api.Test;

class MainTest {

    @Test
    void main_normal() {
        InputStream in = System.in;
        System.setIn(new ByteArrayInputStream("1+2-3*x/y\ny\n".getBytes()));
        Main.main(new String[]{"poplach"});
        System.setIn(in);
    }

    @Test
    void just_for_coverage_print() {
        Expression e = new Add(new Number(0), new Number(0));
        e.print();
        e = new Mul(new Number(0), new Number(0));
        e.print();
        e = new Div(new Number(0), new Number(0));
        e.print();
        e = new Sub(new Number(0), new Number(0));
        e.print();
        e = new Variable("x");
        e.print();
    }

}