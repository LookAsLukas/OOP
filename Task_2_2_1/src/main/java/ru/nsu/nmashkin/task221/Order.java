package ru.nsu.nmashkin.task221;

import java.util.Objects;

/**
 * Order class.
 */
public class Order {
    private final int id;
    private final int deliveryTime;
    private volatile OrderState state;

    /**
     * Enum for state of order.
     */
    public enum OrderState {
        QUEUED,
        COOKING,
        IN_STORAGE,
        DELIVERING,
        COMPLETED,
    }

    /**
     * Order constructor.
     *
     * @param id .
     * @param deliveryTime .
     */
    public Order(int id, int deliveryTime) {
        this.id = id;
        this.state = OrderState.QUEUED;
        this.deliveryTime = deliveryTime;
    }

    /**
     * Set order state.
     *
     * @param state .
     */
    public void setState(OrderState state) {
        this.state = state;
        System.out.println(this);
    }

    /**
     * .
     * @return .
     */
    public OrderState getState() {
        return state;
    }

    /**
     * Get order delivery time.
     *
     * @return .
     */
    public int getDeliveryTime() {
        return deliveryTime;
    }

    /**
     * .
     * @return .
     */
    @Override
    public String toString() {
        return String.format("[%d] %s", id, state);
    }

    /**
     * .
     * @param o   the reference object with which to compare.
     * @return .
     */
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Order order = (Order) o;
        return id == order.id
                && getDeliveryTime() == order.getDeliveryTime()
                && state == order.state;
    }

    /**
     * .
     * @return .
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, getDeliveryTime(), state);
    }
}
