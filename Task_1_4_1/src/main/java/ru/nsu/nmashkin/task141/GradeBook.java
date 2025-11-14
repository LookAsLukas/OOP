package ru.nsu.nmashkin.task141;

public class GradeBook {
    private enum ControlType {
        Task(0),
        ControlWork(1),
        Colloquium(2),
        Exam(3),
        DiffCredit(4),
        Credit(5),
        PracticeReport(6),
        Final(7);

        private final int ind;

        ControlType(int ind) {
            this.ind = ind;
        }

        public int getVal() {
            return ind;
        }
    }

    private final int typeCount = 8;
    private final int semesterCount = 9;
    private int[][][] grades = new int[typeCount][semesterCount][];

    public GradeBook() {
        for (int i = 0; i < typeCount; i++) {
            for (int j = 0; j < semesterCount; j++) {
                int[][] template = new int[][]{
                        {15, 2, 2, 3, 2, 2, 2, 2, 0},
                        {13, 3, 3, 2, 1, 2, 2, 0, 0},
                        {2, 1, 1, 0, 0, 0, 0, 0, 0},
                        {19, 3, 3, 2, 5, 3, 2, 1, 0},
                        {35, 3, 3, 6, 5, 4, 6, 4, 4},
                        {7, 3, 2, 0, 0, 0, 0, 1, 1},
                        {3, 0, 0, 0, 0, 0, 0, 1, 2},
                        {1, 0, 0, 0, 0, 0, 0, 0, 1}
                };
                grades[i][j] = new int[template[i][j]];
            }
        }
    }

    public GradeBook(int[][][] grades) {
        this();
        for (int i = 0; i < typeCount; i++) {
            for (int j = 0; j < semesterCount; j++) {
                System.arraycopy(grades[i][j], 0, this.grades[i][j], 0, grades[i][j].length);
            }
        }
    }

    public double average() {
        int sum = 0;
        int total = 0;
        for (int[][] controlGrades : grades) {
            for (int a : controlGrades[0]) {
                sum += a;
            }
            total += 5 * controlGrades.length;
        }
        return (double)sum / total;
    }

    public boolean canBeOnBudget(int currentSemester) {
        return grades[ControlType.Exam.getVal()][currentSemester][0] > 3
               && (!(currentSemester > 1)
                   || grades[ControlType.Exam.getVal()][currentSemester - 1][0] > 3);
    }


}
