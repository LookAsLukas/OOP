package ru.nsu.nmashkin.task221;

import java.util.Random;

/**
 * Order Generator.
 */
public class OrderGenerator implements Runnable {
    private final Random random = new Random();
    private final OrderQueue orderQueue;
    private final int orderCnt;

    /**
     * Constructor.
     *
     * @param orderCnt .
     * @param orderQueue .
     */
    public OrderGenerator(int orderCnt, OrderQueue orderQueue) {
        this.orderCnt = orderCnt;
        this.orderQueue = orderQueue;
    }

    /**
     * .
     */
    @Override
    public void run() {
        for (int i = 0; i < orderCnt; i++) {
            try {
                Order order = new Order(i * 67, random.nextInt(3, 10));
                orderQueue.addOrder(order);

                Thread.sleep(random.nextInt(5, 100));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
