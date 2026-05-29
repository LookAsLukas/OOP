package ru.nsu.nmashkin.task212;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;

/**
 * Slave for 300$.
 */
public record Slave(int threadCount) {
    private static final String MULTICAST_ADDRESS = "230.0.0.1";
    private static final int MULTICAST_PORT = 4446;

    /**
     * Start the work day.
     */
    public void start() {
        System.out.println("Slave ready. Initiating Master discovery...");

        while (true) {
            InetSocketAddress masterAddress = discoverMaster();
            if (masterAddress == null) {
                System.out.println("Discovery failed. Retrying...");
                continue;
            }

            try {
                System.out.println("Connecting to discovered Master at " + masterAddress);

                try (Socket socket = new Socket(masterAddress.getAddress(),
                        masterAddress.getPort());
                     ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                     ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

                    System.out.println("Master detected");

                    while (true) {
                        Object input = in.readObject();
                        if (input == null) {
                            System.out.println("Work is over");
                            return;
                        }

                        Task task = (Task) input;
                        System.out.println("Work received: #" + task.taskId()
                                + ", size: " + task.numbers().length);

                        boolean result = MultiThreaded.hasNonPrime(task.numbers(), threadCount);

                        TaskResult taskResult = new TaskResult(task.taskId(), result);
                        out.writeObject(taskResult);
                        out.flush();
                        System.out.println("Work #" + task.taskId()
                                + " is done, result: " + result);
                    }
                }
            } catch (ConnectException e) {
                System.err.println("Master found but unavailable (Connection refused).");
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Connection with Master broken: " + e.getMessage());
                System.out.println("Work is over");
                return;
            }

            try {
                Thread.sleep(3000);
            } catch (InterruptedException ie) {
                System.out.println("Slave interrupted, stopping loop.");
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    private InetSocketAddress discoverMaster() {
        try (MulticastSocket multicastSocket = new MulticastSocket(MULTICAST_PORT)) {
            InetAddress group = InetAddress.getByName(MULTICAST_ADDRESS);
            NetworkInterface netIf = NetworkInterface.getByInetAddress(InetAddress.getLocalHost());
            if (netIf == null) {
                netIf = NetworkInterface.getNetworkInterfaces().nextElement();
            }
            multicastSocket.joinGroup(new InetSocketAddress(group, MULTICAST_PORT), netIf);

            byte[] msgBytes = "SLAVE_READY".getBytes(StandardCharsets.UTF_8);
            DatagramPacket readyPacket = new DatagramPacket(msgBytes, msgBytes.length,
                    group, MULTICAST_PORT);
            multicastSocket.send(readyPacket);
            multicastSocket.setSoTimeout(3000);

            byte[] buffer = new byte[1024];
            while (true) {
                try {
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                    multicastSocket.receive(packet);
                    String response = new String(packet.getData(), 0,
                            packet.getLength(), StandardCharsets.UTF_8);

                    if (response.startsWith("MASTER_INFO:")
                            || response.startsWith("MASTER_START:")) {
                        int port = Integer.parseInt(response.split(":")[1]);
                        InetAddress masterIp = packet.getAddress();

                        if (masterIp.isLoopbackAddress() || masterIp.isAnyLocalAddress()) {
                            masterIp = InetAddress.getLocalHost();
                        }
                        return new InetSocketAddress(masterIp, port);
                    }
                } catch (SocketTimeoutException e) {
                    System.out.println("No Master answered. Re-broadcasting/waiting...");
                    multicastSocket.send(readyPacket);
                }
            }
        } catch (Exception e) {
            System.err.println("Discovery exception: " + e.getMessage());
            return null;
        }
    }
}