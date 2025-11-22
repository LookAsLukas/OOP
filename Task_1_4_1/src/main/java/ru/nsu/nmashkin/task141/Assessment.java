package ru.nsu.nmashkin.task141;

/**
 * Assessment class for GradeBook.
 *
 * @param type type of Assessment
 * @param semester semester of the assessment
 * @param grade grade for the assessment
 */
public record Assessment(AssessmentType type, String subjectName, int semester, Grade grade) {}