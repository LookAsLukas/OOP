package ru.nsu.nmashkin.task221;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Baker class.
 */
public class Baker implements Runnable {
    @JsonProperty("cookingTimeSeconds")
    private int cookingTimeSeconds;
    private OrderQueue orderQueue;
    private Storage storage;

    /**
     * Constructor for JSON.
     */
    public Baker() {}

    /**
     * .
     * @param orderQueue .
     */
    public void setOrderQueue(OrderQueue orderQueue) {
        this.orderQueue = orderQueue;
    }

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
    public int getCookingTimeSeconds() {
        return cookingTimeSeconds;
    }

    /**
     * .
     */
    @Override
    public void run() {
        while (true) {
            try {
                Order order = orderQueue.takeOrder();
                if (order == null) {
                    break;
                }

                order.setState(Order.OrderState.COOKING);

                Thread.sleep(cookingTimeSeconds * 100L);

                storage.addPizza(order);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}