package ru.nsu.nmashkin.task212;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import org.junit.jupiter.api.Test;

class SlaveTest {

    private void masterSim(int port, CountDownLatch stopLatch) {
        Thread advertiser = new Thread(() -> {
            try (DatagramSocket udpSocket = new DatagramSocket()) {
                InetAddress group = InetAddress.getByName("230.0.0.1");
                byte[] msgBytes = ("MASTER_START:" + port).getBytes(StandardCharsets.UTF_8);
                DatagramPacket packet = new DatagramPacket(msgBytes, msgBytes.length, group, 4446);

                while (stopLatch.getCount() > 0) {
                    udpSocket.send(packet);
                    Thread.sleep(500);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        advertiser.setDaemon(true);
        advertiser.start();
    }

    @Test
    public void start() throws Exception {
        int port = 8003;
        AtomicBoolean resultReceived = new AtomicBoolean(false);
        CountDownLatch stopAdvertiser = new CountDownLatch(1);

        Thread masterSimulator = new Thread(() -> {
            try (ServerSocket server = new ServerSocket(port)) {
                masterSim(port, stopAdvertiser);

                try (Socket socket = server.accept();
                     ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                     ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

                    stopAdvertiser.countDown();

                    out.writeObject(new Task(99, new int[]{2, 4, 6}));
                    out.flush();

                    TaskResult result = (TaskResult) in.readObject();
                    if (result != null && result.taskId() == 99) {
                        resultReceived.set(result.hasNonPrime());
                    }

                    out.writeObject(null);
                    out.flush();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                stopAdvertiser.countDown();
            }
        });
        masterSimulator.start();

        Thread.sleep(200);

        Slave slave = new Slave(2);
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
        CountDownLatch stopAdvertiser = new CountDownLatch(1);

        Thread slaveThread = new Thread(() -> {
            Slave slave = new Slave(2);
            slave.start();
        });

        slaveThread.start();
        Thread.sleep(5000);

        Thread masterDelayedThread = new Thread(() -> {
            masterStartedLatch.countDown();
            try (ServerSocket server = new ServerSocket(port)) {
                masterSim(port, stopAdvertiser);

                try (Socket socket = server.accept();
                     ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                     ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

                    stopAdvertiser.countDown();

                    out.writeObject(new Task(42, new int[]{4, 6, 8}));
                    out.flush();

                    TaskResult result = (TaskResult) in.readObject();
                    if (result != null && result.taskId() == 42) {
                        taskProcessedAfterReconnect.set(true);
                    }

                    out.writeObject(null);
                    out.flush();
                    taskDoneLatch.countDown();

                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                stopAdvertiser.countDown();
            }
        });

        masterDelayedThread.start();

        assertTrue(masterStartedLatch.await(2, TimeUnit.SECONDS));
        assertTrue(taskDoneLatch.await(5, TimeUnit.SECONDS));

        slaveThread.interrupt();
        slaveThread.join(1000);

        assertTrue(taskProcessedAfterReconnect.get());
    }
}