package ru.nsu.nmashkin.task221;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Queue;
import org.junit.jupiter.api.Test;

class StorageTest {

    @Test
    void addPizza() {
        Storage storage = new Storage(100);
        try {
            Order order = new Order(0, 67);
            storage.addPizza(order);
            assertEquals(Order.OrderState.IN_STORAGE, order.getState());
        } catch (InterruptedException e) {
            System.err.println("Not today, pal!");
        }
    }

    @Test
    void takePizzas() {
        Storage storage = new Storage(100);
        try {
            Order order = new Order(0, 67);
            storage.addPizza(order);

            Queue<Order> pizzas = storage.takePizzas(67);
            assertEquals(1, pizzas.size());
            assertEquals(Order.OrderState.DELIVERING, pizzas.poll().getState());
        } catch (InterruptedException e) {
            System.err.println("Not today, pal!");
        }
    }

    @Test
    void noMoreOrders() {
        Storage storage = new Storage(100);
        try {
            Order order = new Order(0, 67);
            storage.addPizza(order);

            storage.takePizzas(67);
            storage.noMoreOrders();
            assertNull(storage.takePizzas(67));

        } catch (InterruptedException e) {
            System.err.println("Not today, pal!");
        }
    }
}