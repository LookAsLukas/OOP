package ru.nsu.nmashkin.task212;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Master of Slaves.
 */
public class Master {
    private final int port;
    private final BlockingQueue<Task> taskQueue = new LinkedBlockingQueue<>();
    private final AtomicBoolean globalResult = new AtomicBoolean(false);
    private final AtomicBoolean isDone = new AtomicBoolean(false);
    private final List<Thread> workerHandlers = new CopyOnWriteArrayList<>();
    private final Object lock = new Object();
    private volatile MulticastSocket discoverySocket;
    private int tasksLeft;

    private static final String MULTICAST_ADDRESS = "230.0.0.1";
    private static final int MULTICAST_PORT = 4446;

    /**
     * Make a master.
     *
     * @param port to listen to.
     */
    public Master(int port) {
        this.port = port;
    }

    /**
     * Accept slaves, give them work, return result.
     */
    public boolean execute(int[] numbers, int chunkSize, int expectedWorkers) {
        int taskId = 0;
        for (int i = 0; i < numbers.length; i += chunkSize) {
            int end = Math.min(i + chunkSize, numbers.length);
            int[] chunk = Arrays.copyOfRange(numbers, i, end);
            taskQueue.add(new Task(taskId++, chunk));
        }

        tasksLeft = taskQueue.size();

        System.out.println("Master is up and expecting " + expectedWorkers + " slaves...");

        Thread discoveryThread = new Thread(this::searchForSlaves);
        discoveryThread.setDaemon(true);
        discoveryThread.start();

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            Socket[] workerSockets = new Socket[expectedWorkers];
            for (int i = 0; i < expectedWorkers; i++) {
                try {
                    workerSockets[i] = serverSocket.accept();
                    System.out.println("Slave detected: " + workerSockets[i].getRemoteSocketAddress());
                } catch (IOException e) {
                    System.out.println("Not enough slaves: " + e.getMessage());
                    break;
                }
            }

            System.out.println("All slaves are ready, " + taskQueue.size() + " works to be done");

            for (var sock : workerSockets) {
                if (sock != null) {
                    Thread handler = new Thread(new WorkerHandler(sock));
                    workerHandlers.add(handler);
                    handler.start();
                }
            }

            synchronized (lock) {
                while (tasksLeft > 0 && !globalResult.get()) {
                    lock.wait();
                }
            }

        } catch (IOException | InterruptedException e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            isDone.set(true);
            for (Thread t : workerHandlers) {
                t.interrupt();
            }

            if (discoverySocket != null && !discoverySocket.isClosed()) {
                discoverySocket.close();
            }
        }

        return globalResult.get();
    }

    private void searchForSlaves() {
        try {
            discoverySocket = new MulticastSocket(MULTICAST_PORT);
            InetAddress group = InetAddress.getByName(MULTICAST_ADDRESS);
            NetworkInterface netIf = NetworkInterface.getByInetAddress(InetAddress.getLocalHost());
            if (netIf == null) {
                netIf = NetworkInterface.getNetworkInterfaces().nextElement();
            }
            discoverySocket.joinGroup(new InetSocketAddress(group, MULTICAST_PORT), netIf);

            byte[] msgBytes = ("MASTER_START:" + port).getBytes(StandardCharsets.UTF_8);
            DatagramPacket notification = new DatagramPacket(msgBytes, msgBytes.length, group, MULTICAST_PORT);
            discoverySocket.send(notification);

            byte[] buffer = new byte[1024];
            while (!isDone.get()) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                discoverySocket.receive(packet);
                String message = new String(packet.getData(), 0, packet.getLength(), StandardCharsets.UTF_8);

                if ("SLAVE_READY".equals(message)) {
                    byte[] responseBytes = ("MASTER_INFO:" + port).getBytes(StandardCharsets.UTF_8);
                    DatagramPacket response = new DatagramPacket(
                            responseBytes, responseBytes.length,
                            group, MULTICAST_PORT
                    );
                    discoverySocket.send(response);
                }
            }
        } catch (SocketException e) {
            System.out.println("Search ended");
        } catch (Exception e) {
            System.err.println("We're screwed: " + e.getMessage());
        } finally {
            if (discoverySocket != null && !discoverySocket.isClosed()) {
                discoverySocket.close();
            }
        }
    }

    private class WorkerHandler implements Runnable {
        private final Socket socket;

        public WorkerHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            Task currentTask = null;
            try (Socket s = socket;
                 ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
                 ObjectInputStream in = new ObjectInputStream(s.getInputStream())) {

                while (!isDone.get() && !globalResult.get()) {
                    currentTask = taskQueue.poll(1, TimeUnit.SECONDS);
                    if (currentTask == null) {
                        if (taskQueue.isEmpty()) {
                            break;
                        }
                        continue;
                    }

                    out.writeObject(currentTask);
                    out.flush();

                    TaskResult result = (TaskResult) in.readObject();

                    synchronized (lock) {
                        if (result.hasNonPrime()) {
                            globalResult.set(true);
                        }
                        System.out.println("Slave has completed work #" + result.taskId()
                                + " with result " + result.hasNonPrime());
                        tasksLeft--;

                        if (result.hasNonPrime() || tasksLeft == 0) {
                            lock.notifyAll();
                        }
                    }

                    if (!s.isClosed()) {
                        currentTask = null;
                    }
                }

                if (!s.isClosed()) {
                    out.writeObject(null);
                    out.flush();
                }

            } catch (Exception e) {
                System.err.println("Slave is dead: " + socket.getRemoteSocketAddress());
            } finally {
                System.out.println(currentTask);
                if (currentTask != null) {
                    System.out.println("Sun is still up in the sky, returning task to queue: " + currentTask.taskId());
                    taskQueue.add(currentTask);
                }
            }
        }
    }
}