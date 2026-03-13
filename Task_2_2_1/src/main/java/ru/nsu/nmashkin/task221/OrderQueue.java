package ru.nsu.nmashkin.task221;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Order (Blocking) Queue.
 */
public class OrderQueue {
    private final Queue<Order> orders = new LinkedList<>();
    private final Object monitor = new Object();
    private final Storage storage;
    private boolean acceptingOrders = true;

    /**
     * .
     *
     * @param storage .
     */
    public OrderQueue(Storage storage) {
        this.storage = storage;
    }

    /**
     * .
     *
     * @param order .
     */
    public void addOrder(Order order) {
        synchronized (monitor) {
            if (!acceptingOrders) {
                return;
            }

            orders.add(order);
            monitor.notify();
        }
    }

    /**
     * .
     *
     * @return .
     * @throws InterruptedException .
     */
    public Order takeOrder() throws InterruptedException {
        synchronized (monitor) {
            while (orders.isEmpty() && acceptingOrders) {
                monitor.wait();
            }
            if (!acceptingOrders && orders.isEmpty()) {
                return null;
            }

            Order result = orders.poll();
            if (!acceptingOrders && orders.isEmpty()) {
                storage.noMoreOrders();
            }
            return result;
        }
    }

    /**
     * .
     */
    public void stopAcceptingOrders() {
        synchronized (monitor) {
            acceptingOrders = false;
            monitor.notifyAll();
        }
    }
}