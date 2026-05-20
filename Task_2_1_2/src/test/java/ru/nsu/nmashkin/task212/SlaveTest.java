package ru.nsu.nmashkin.task212;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import org.junit.jupiter.api.Test;

public class SlaveTest {

    @Test
    public void start() throws Exception {
        int port = 8003;
        AtomicBoolean resultReceived = new AtomicBoolean(false);

        Thread masterSimulator = new Thread(() -> {
            try (ServerSocket server = new ServerSocket(port);
                 Socket socket = server.accept();
                 ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                 ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

                out.writeObject(new Task(99, new int[]{2, 4, 6}));
                out.flush();

                TaskResult result = (TaskResult) in.readObject();
                if (result != null && result.taskId() == 99) {
                    resultReceived.set(result.hasNonPrime());
                }

                out.writeObject(null);
                out.flush();
            } catch (Exception ignored) {}
        });
        masterSimulator.start();

        Thread.sleep(200);

        Slave slave = new Slave("localhost", port, 2);
        slave.start();

        masterSimulator.join();
        assertTrue(resultReceived.get());
    }

    @Test
    public void start_reconnect() throws Exception {
        int port = 8025;
        AtomicBoolean taskProcessedAfterReconnect = new AtomicBoolean(false);
        CountDownLatch masterStartedLatch = new CountDownLatch(1);
        CountDownLatch taskDoneLatch = new CountDownLatch(1);

        Thread slaveThread = new Thread(() -> {
            Slave slave = new Slave("localhost", port, 2);
            slave.start();
        });

        slaveThread.start();
        Thread.sleep(500);

        Thread masterDelayedThread = new Thread(() -> {
            masterStartedLatch.countDown();
            try (ServerSocket server = new ServerSocket(port);
                 Socket socket = server.accept();
                 ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                 ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

                out.writeObject(new Task(42, new int[]{4, 6, 8}));
                out.flush();

                TaskResult result = (TaskResult) in.readObject();
                if (result != null && result.taskId() == 42) {
                    taskProcessedAfterReconnect.set(true);
                }

                out.writeObject(null);
                out.flush();
                taskDoneLatch.countDown();

            } catch (Exception ignored) {}
        });

        masterDelayedThread.start();

        assertTrue(masterStartedLatch.await(2, TimeUnit.SECONDS));
        assertTrue(taskDoneLatch.await(5, TimeUnit.SECONDS));

        slaveThread.interrupt();
        slaveThread.join(1000);

        assertTrue(taskProcessedAfterReconnect.get());
    }
}