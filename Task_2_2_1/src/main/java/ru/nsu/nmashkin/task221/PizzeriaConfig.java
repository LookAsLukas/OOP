package ru.nsu.nmashkin.task221;

import java.util.List;

/**
 * Config class for JSON mapping.
 */
public class PizzeriaConfig {
    private List<Baker> bakers;
    private List<Courier> couriers;
    private int storageCapacity;

    /**
     * Get bakers.
     *
     * @return bakers
     */
    public List<Baker> getBakers() {
        return bakers;
    }

    /**
     * Get courier.
     *
     * @return courier
     */
    public List<Courier> getCouriers() {
        return couriers;
    }

    /**
     * Get storageCapacity.
     *
     * @return storageCapacity
     */
    public int getStorageCapacity() {
        return storageCapacity;
    }
}