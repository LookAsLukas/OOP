package ru.nsu.nmashkin.task221;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Storage class.
 */
public class Storage {
    private final int capacity;
    private final Queue<Order> pizzas = new LinkedList<>();
    private final Object monitor = new Object();
    private boolean ordersComing = true;

    /**
     * Storage constructor.
     *
     * @param capacity .
     */
    public Storage(int capacity) {
        this.capacity = capacity;
    }

    /**
     * Add pizza to storage. Blocks if storage is full.
     *
     * @param order .
     * @throws InterruptedException .
     */
    public void addPizza(Order order) throws InterruptedException {
        synchronized (monitor) {
            while (pizzas.size() >= capacity) {
                monitor.wait();
            }
            pizzas.add(order);
            order.setState(Order.OrderState.IN_STORAGE);
            monitor.notifyAll();
        }
    }

    /**
     * Take up to maxCount pizzas from storage.
     *
     * @param maxCount .
     * @return .
     * @throws InterruptedException .
     */
    public Queue<Order> takePizzas(int maxCount) throws InterruptedException {
        synchronized (monitor) {
            while (pizzas.isEmpty() && ordersComing) {
                monitor.wait();
            }

            if (!ordersComing && pizzas.isEmpty()) {
                return null;
            }

            Queue<Order> taken = new LinkedList<>();
            int count = Math.min(maxCount, pizzas.size());
            for (int i = 0; i < count; i++) {
                Order order = pizzas.poll();
                if (order == null) {
                    break;
                }

                order.setState(Order.OrderState.DELIVERING);
                taken.add(order);
            }
            monitor.notifyAll();
            return taken;
        }
    }

    /**
     * Promise that there will be no orders added to the order queue.
     */
    public void noMoreOrders() {
        synchronized (monitor) {
            ordersComing = false;
            monitor.notifyAll();
        }
    }
}