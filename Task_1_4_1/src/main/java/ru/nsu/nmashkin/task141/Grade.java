package ru.nsu.nmashkin.task141;

public enum Grade {
    EXCELLENT(5),
    GOOD(4),
    SATISFACTORY(3),
    FAIL(2);

    private final int val;

    Grade(int val) {
        this.val = val;
    }

    public int getVal() {
        return val;
    }
}
