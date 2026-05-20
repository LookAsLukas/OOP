package ru.nsu.nmashkin.task212;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class EndToEndTest {

    @Test
    public void e2e() {
        int port = 8004;
        int[] numbers = {11, 13, 17, 19, 23, 29};
        Master master = new Master(port);

        Thread slaveThread = new Thread(() -> {
            Slave slave = new Slave("localhost", port, 2);
            slave.start();
        });
        slaveThread.start();

        boolean result = master.execute(numbers, 2, 1);
        assertFalse(result);

        slaveThread.interrupt();
    }

    @Test
    public void e2e_allPrime() {
        int port = 8005;
        int[] numbers = {11, 13, 15, 17, 19};
        Master master = new Master(port);

        Thread slaveThread = new Thread(() -> {
            Slave slave = new Slave("localhost", port, 2);
            slave.start();
        });
        slaveThread.start();

        boolean result = master.execute(numbers, 2, 1);
        assertTrue(result);

        slaveThread.interrupt();
    }
}