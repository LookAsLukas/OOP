package ru.nsu.nmashkin.task212;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;

/**
 * Slave for 300$.
 */
public record Slave(String masterHost, int masterPort, int threadCount) {
    /**
     * Start the work day.
     */
    public void start() {
        System.out.println("Slave ready for " + masterHost + ":" + masterPort);

        while (true) {
            try {
                System.out.println("Trying to connect to Master...");

                try (Socket socket = new Socket(masterHost, masterPort);
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
                System.err.println("Master is unavailable (Connection refused).");
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Connection with Master broken: " + e.getMessage());
                System.out.println("Work is over");
                return;
            }

            try {
                int reconnectDelayMs = 3000;
                System.out.println("Reconnecting in " + (reconnectDelayMs / 1000) + " seconds...");
                Thread.sleep(reconnectDelayMs);
            } catch (InterruptedException ie) {
                System.out.println("Slave interrupted, stopping reconnect loop.");
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}