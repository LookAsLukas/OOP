package ru.nsu.nmashkin.task221;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Queue;

/**
 * Courier class.
 */
public class Courier implements Runnable {
    @JsonProperty("capacity")
    private int capacity;
    private Storage storage;

    /**
     * Constructor for JSON.
     */
    public Courier() {}

    /**
     * .
     * @param storage .
     */
    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    /**
     * .
     * @return .
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * .
     */
    @Override
    public void run() {
        while (true) {
            try {
                Queue<Order> orders = storage.takePizzas(capacity);
                if (orders == null) {
                    break;
                }

                for (Order order : orders) {
                    Thread.sleep(order.getDeliveryTime() * 100L);
                    order.setState(Order.OrderState.COMPLETED);
                }

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}