package ru.nsu.nmashkin.task141;

/**
 * Grade enum for GradeBook.
 */
public enum Grade {
    EXCELLENT(5),
    GOOD(4),
    SATISFACTORY(3),
    FAIL(2);

    private final int val;

    Grade(int val) {
        this.val = val;
    }

    /**
     * Get the numeric value.
     *
     * @return the numeric value
     */
    public int getVal() {
        return val;
    }
}
