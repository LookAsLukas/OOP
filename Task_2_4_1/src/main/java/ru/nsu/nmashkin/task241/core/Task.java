package ru.nsu.nmashkin.task241.core;

import java.time.LocalDate;

/**
 * .
 *
 * @param id .
 * @param name .
 * @param maxScore .
 * @param softDeadline .
 * @param hardDeadline .
 */
public record Task(String id, String name, int maxScore,
                   LocalDate softDeadline, LocalDate hardDeadline) {}