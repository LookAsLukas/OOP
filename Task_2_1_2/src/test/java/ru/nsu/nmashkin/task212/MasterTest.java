package ru.nsu.nmashkin.task212;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import org.junit.jupiter.api.Test;

/**
 * .
 */
public class MasterTest {

    @Test
    public void execute_allPrime() throws Exception {
        int port = 8011;
        int[] numbers = {2, 3, 5, 7, 11, 13, 17, 19};
        int chunkSize = 3;
        int expectedWorkers = 1;

        Master master = new Master(port);

        Thread slaveSimulator = new Thread(() -> {
            try {
                Thread.sleep(200);
                try (Socket socket = new Socket("localhost", port);
                     ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                     ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

                    while (true) {
                        Object obj = in.readObject();
                        if (obj == null) {
                            break;
                        }
                        Task task = (Task) obj;
                        out.writeObject(new TaskResult(task.taskId(), false));
                        out.flush();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        slaveSimulator.start();

        boolean result = master.execute(numbers, chunkSize, expectedWorkers);
        assertFalse(result);
        slaveSimulator.join();
    }

    @Test
    public void execute() throws Exception {
        int port = 8012;
        int[] numbers = {2, 3, 4, 5, 7};
        int chunkSize = 2;
        int expectedWorkers = 1;

        Master master = new Master(port);

        Thread slaveSimulator = new Thread(() -> {
            try {
                Thread.sleep(200);
                try (Socket socket = new Socket("localhost", port);
                     ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                     ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

                    while (true) {
                        Object obj = in.readObject();
                        if (obj == null) {
                            break;
                        }
                        Task task = (Task) obj;
                        boolean hasNonPrime = false;
                        for (int n : task.numbers()) {
                            if (n == 4) {
                                hasNonPrime = true;
                                break;
                            }
                        }
                        out.writeObject(new TaskResult(task.taskId(), hasNonPrime));
                        out.flush();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        slaveSimulator.start();

        boolean result = master.execute(numbers, chunkSize, expectedWorkers);
        assertTrue(result);
        slaveSimulator.join();
    }
}