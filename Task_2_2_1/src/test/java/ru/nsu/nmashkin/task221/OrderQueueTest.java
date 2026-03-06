package ru.nsu.nmashkin.task221;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class OrderQueueTest {

    @Test
    void takeOrder() {
        OrderQueue queue = new OrderQueue();
        try {
            Order order = new Order(0, 67);
            queue.addOrder(order);
            assertEquals(order, queue.takeOrder());
        } catch (InterruptedException e) {
            System.err.println("Not today, pal!");
        }
    }

    @Test
    void stopAcceptingOrders() {
        OrderQueue queue = new OrderQueue();
        try {
            Order order = new Order(0, 67);
            queue.addOrder(order);
            queue.takeOrder();
            queue.stopAcceptingOrders();
            assertNull(queue.takeOrder());
        } catch (InterruptedException e) {
            System.err.println("Not today, pal!");
        }
    }
}