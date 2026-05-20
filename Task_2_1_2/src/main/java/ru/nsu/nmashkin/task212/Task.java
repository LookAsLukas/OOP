package ru.nsu.nmashkin.task212;

import java.io.Serial;
import java.io.Serializable;

/**
 * Slave's work.
 *
 * @param taskId .
 * @param numbers .
 */
public record Task(int taskId, int[] numbers) implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
}