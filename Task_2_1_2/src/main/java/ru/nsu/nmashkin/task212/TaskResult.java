package ru.nsu.nmashkin.task212;

import java.io.Serial;
import java.io.Serializable;

/**
 * Slave's work report.
 *
 * @param taskId .
 * @param hasNonPrime .
 */
public record TaskResult(int taskId, boolean hasNonPrime) implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
}