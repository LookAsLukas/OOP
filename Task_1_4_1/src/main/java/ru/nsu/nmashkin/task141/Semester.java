package ru.nsu.nmashkin.task141;

/**
 * Semester enum for maxAmounts.
 */
public enum Semester {
    Total,
    First,
    Second,
    Third,
    Fourth,
    Fifth,
    Sixth,
    Seventh,
    Eighth;

    /**
     * Make a Semester value from index.
     *
     * @param index said index
     * @return semester value
     */
    public static Semester fromIndex(int index) {
        return switch (index) {
            case 0 -> Total;
            case 1 -> First;
            case 2 -> Second;
            case 3 -> Third;
            case 4 -> Fourth;
            case 5 -> Fifth;
            case 6 -> Sixth;
            case 7 -> Seventh;
            case 8 -> Eighth;
            default -> throw new GradeBookException("No Semester value for index " + index);
        };
    }
}
