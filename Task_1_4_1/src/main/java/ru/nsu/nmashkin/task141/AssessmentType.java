package ru.nsu.nmashkin.task141;

/**
 * Assessment type enum for GradeBook.
 */
public enum AssessmentType {
    ASSIGNMENT,
    CONTROL_WORK,
    COLLOQUIUM,
    EXAM,
    DIFFERENTIATED_CREDIT,
    CREDIT,
    PRACTICE_REPORT_DEFENSE,
    THESIS_DEFENSE;

    /**
     * Make a AssessmentType value from index.
     *
     * @param index said index
     * @return assessment type value
     */
    public static AssessmentType fromIndex(int index) {
        return switch (index) {
            case 0 -> ASSIGNMENT;
            case 1 -> CONTROL_WORK;
            case 2 -> COLLOQUIUM;
            case 3 -> EXAM;
            case 4 -> DIFFERENTIATED_CREDIT;
            case 5 -> CREDIT;
            case 6 -> PRACTICE_REPORT_DEFENSE;
            case 7 -> THESIS_DEFENSE;
            default -> throw new GradeBookException("No AssessmentType value for index " + index);
        };
    }
}
