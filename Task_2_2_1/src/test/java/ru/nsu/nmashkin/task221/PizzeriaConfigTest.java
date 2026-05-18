package ru.nsu.nmashkin.task221;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.util.List;
import org.junit.jupiter.api.Test;

class PizzeriaConfigTest {

    @Test
    void getBakers() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            PizzeriaConfig config = mapper.readValue(
                    new File("pizzeria_config.json"),
                    PizzeriaConfig.class
            );

            List<Baker> bakers = config.getBakers();
            assertEquals(3, bakers.size());
            assertEquals(3, bakers.get(0).getCookingTimeSeconds());
            assertEquals(4, bakers.get(1).getCookingTimeSeconds());
            assertEquals(5, bakers.get(2).getCookingTimeSeconds());

        } catch (Exception e) {
            System.err.println("Not today, pal!");
        }
    }

    @Test
    void getCouriers() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            PizzeriaConfig config = mapper.readValue(
                    new File("pizzeria_config.json"),
                    PizzeriaConfig.class
            );

            List<Courier> couriers = config.getCouriers();
            assertEquals(2, couriers.size());
            assertEquals(2, couriers.get(0).getCapacity());
            assertEquals(3, couriers.get(1).getCapacity());

        } catch (Exception e) {
            System.err.println("Not today, pal!");
        }
    }

    @Test
    void getStorageCapacity() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            PizzeriaConfig config = mapper.readValue(
                    new File("pizzeria_config.json"),
                    PizzeriaConfig.class
            );

            assertEquals(10, config.getStorageCapacity());

        } catch (Exception e) {
            System.err.println("Not today, pal!");
        }
    }
}