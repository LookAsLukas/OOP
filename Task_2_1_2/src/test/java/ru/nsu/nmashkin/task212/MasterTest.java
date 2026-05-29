package ru.nsu.nmashkin.task212;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicBoolean;
import org.junit.jupiter.api.Test;

class MasterTest {

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

    @Test
    public void masterRespondsToSlaveReady() throws Exception {
        int port = 8030;
        Master master = new Master(port);
        AtomicBoolean responseReceived = new AtomicBoolean(false);

        Thread slaveSimulator = new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            try {
                try (MulticastSocket multicastSocket = new MulticastSocket(4446)) {
                    InetAddress group = InetAddress.getByName("230.0.0.1");
                    NetworkInterface netIf = NetworkInterface.getByInetAddress(
                            InetAddress.getLocalHost());
                    if (netIf == null) {
                        netIf = NetworkInterface.getNetworkInterfaces().nextElement();
                    }
                    multicastSocket.joinGroup(new InetSocketAddress(group, 4446), netIf);
                    multicastSocket.setSoTimeout(3000);

                    byte[] msgBytes = "SLAVE_READY".getBytes(StandardCharsets.UTF_8);
                    DatagramPacket readyPacket = new DatagramPacket(msgBytes, msgBytes.length,
                            group, 4446);
                    multicastSocket.send(readyPacket);

                    byte[] buffer = new byte[1024];
                    while (true) {
                        try {
                            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                            multicastSocket.receive(packet);
                            String response = new String(packet.getData(), 0,
                                    packet.getLength(), StandardCharsets.UTF_8);

                            System.err.println(response);
                            if (response.startsWith("MASTER_INFO:")) {
                                responseReceived.set(true);
                                break;
                            }
                        } catch (SocketTimeoutException e) {
                            System.out.println("No Master answered. Re-broadcasting/waiting...");
                            multicastSocket.send(readyPacket); // Повторяем запрос
                        }
                    }
                } catch (Exception e) {
                    System.err.println("Discovery exception: " + e.getMessage());
                    return;
                }

                try (Socket socket = new Socket("localhost", port);
                     ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                     ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

                    Task task = (Task) in.readObject();
                    out.writeObject(new TaskResult(task.taskId(), false));
                    out.flush();
                    in.readObject();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        slaveSimulator.start();

        master.execute(new int[]{1}, 1, 1);

        slaveSimulator.join(5000);
        assertTrue(responseReceived.get());
    }


    @Test
    public void workerCrash_returnsTaskToQueue() throws Exception {
        int port = 8040;
        int[] numbers = {2, 3, 5, 7};
        Master master = new Master(port);

        Thread badSlave = new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            try (Socket socket = new Socket("localhost", port);
                 ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                 ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {
                in.readObject();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        AtomicBoolean success = new AtomicBoolean(false);
        Thread goodSlave = new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try (Socket socket = new Socket("localhost", port);
                 ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                 ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

                for (int i = 0; i < 2; i++) {
                    Task task = (Task) in.readObject();
                    out.writeObject(new TaskResult(task.taskId(), false));
                    out.flush();
                }
                success.set(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        badSlave.start();
        goodSlave.start();

        boolean result = master.execute(numbers, 2, 2);

        assertFalse(result);
        goodSlave.join(2000);
        assertTrue(success.get());
    }
}