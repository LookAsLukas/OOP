package ru.nsu.nmashkin.task141;

public class Grade {
    private final int max;
    private int curr;

    public Grade(int curr, int max) {
        this.max = max;
        this.curr = curr;
    }

    public Grade(int max) {
        this.max = max;
    }

    public int getMax() {
        return max;
    }

    public int getCurr() {
        return curr;
    }
}
