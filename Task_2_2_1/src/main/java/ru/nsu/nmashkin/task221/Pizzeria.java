package ru.nsu.nmashkin.task221;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Main class.
 */
public class Pizzeria {
    /**
     * .
     * @param args .
     */
    public static void main(String[] args) {
        try {
            int orderCnt = Integer.parseInt(args[0]);

            ObjectMapper mapper = new ObjectMapper();
            PizzeriaConfig config = mapper.readValue(
                    new File("pizzeria_config.json"),
                    PizzeriaConfig.class
            );

            Storage storage = new Storage(config.getStorageCapacity());
            OrderQueue queue = new OrderQueue();

            List<Thread> bakerThreads = new ArrayList<>();
            List<Thread> courierThreads = new ArrayList<>();

            for (Baker baker : config.getBakers()) {
                baker.setOrderQueue(queue);
                baker.setStorage(storage);

                Thread thread = new Thread(baker);
                bakerThreads.add(thread);
                thread.start();
            }
            for (Courier courier : config.getCouriers()) {
                courier.setStorage(storage);

                Thread thread = new Thread(courier);
                courierThreads.add(thread);
                thread.start();
            }

            OrderGenerator generator = new OrderGenerator(orderCnt, queue);
            Thread generatorThread = new Thread(generator);
            generatorThread.start();
            generatorThread.join();

            queue.stopAcceptingOrders();
            storage.noMoreOrders();

            for (Thread thread : bakerThreads) {
                thread.join();
            }
            for (Thread thread : courierThreads) {
                thread.join();
            }
        } catch (Exception e) {
            System.err.println("Not today, pal!");
        }
    }
}