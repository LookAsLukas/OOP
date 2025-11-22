package ru.nsu.nmashkin.task141;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * GradeBook class.
 */
public class GradeBook {
    private final Map<AssessmentType, Map<Semester, Integer>> maxAmounts = new HashMap<>();
    private final int currentSemester;
    private final boolean isPaidEducation;
    private final List<Assessment> assessments = new ArrayList<>();
    private final String studentFirstName;
    private final String studentLastName;
    private final int srn;

    private void setMaxAmounts() {
        int[][] template = {
            {15, 2, 2, 3, 2, 2, 2, 2, 0},
            {13, 3, 3, 2, 1, 2, 2, 0, 0},
            {2, 1, 1, 0, 0, 0, 0, 0, 0},
            {19, 3, 3, 2, 5, 3, 2, 1, 0},
            {35, 3, 3, 6, 5, 4, 6, 4, 4},
            {7, 3, 2, 0, 0, 0, 0, 1, 1},
            {3, 0, 0, 0, 0, 0, 0, 1, 2},
            {1, 0, 0, 0, 0, 0, 0, 0, 1},
        };

        for (int i = 0; i < template.length; i++) {
            maxAmounts.put(AssessmentType.fromIndex(i), new HashMap<>());
            for (int j = 0; j < template[i].length; j++) {
                maxAmounts.get(AssessmentType.fromIndex(i))
                        .put(Semester.fromIndex(j), template[i][j]);
            }
        }
    }

    /**
     * Constructor for GradeBook.
     *
     * @param currentSemester student's current semester
     * @param isPaidEducation is the education paid
     */
    public GradeBook(int currentSemester,
                     boolean isPaidEducation,
                     String studentFirstName,
                     String studentLastName,
                     int srn) {
        this.currentSemester = currentSemester;
        this.isPaidEducation = isPaidEducation;
        this.studentFirstName = studentFirstName;
        this.studentLastName = studentLastName;
        this.srn = srn;
        setMaxAmounts();
    }

    /**
     * Attempt to add an Assessment. Fails if Assessment's semester is higher
     * than current semester or if too many Assessments.
     *
     * @param assessment Assessment to add
     */
    public void addAssessment(Assessment assessment) {
        if (assessment.semester() > currentSemester) {
            throw new GradeBookException("Assessment's semester cannot be higher "
                    + "than current semester");
        }

        long sameTypeAndSemesterCount = assessments.stream()
            .filter(a -> a.type() == assessment.type() && a.semester() == assessment.semester())
            .count();
        if (sameTypeAndSemesterCount >= maxAmounts.get(assessment.type())
                                        .get(Semester.fromIndex(assessment.semester()))) {
            throw new GradeBookException("Too many assessments of type " + assessment.type()
                    + "in semester " + assessment.semester());
        }

        assessments.add(assessment);
    }

    /**
     * Get average grade.
     *
     * @return average grade
     */
    public double getCurrentAverage() {
        return assessments.stream()
                .mapToInt(assessment -> assessment.grade().getVal())
                .average()
                .orElse(0.0);
    }

    /**
     * Can the student transfer to budget.
     *
     * @return true if yes
     */
    public boolean canTransferToBudget() {
        if (!isPaidEducation || currentSemester == 1) {
            return false;
        }

        return assessments.stream()
                .filter(assessment -> assessment.semester() == currentSemester - 1
                        || assessment.semester() == currentSemester - 2)
                .allMatch(assessment -> assessment.grade() == Grade.EXCELLENT
                                                   || assessment.grade() == Grade.GOOD);
    }

    /**
     * Can the student get red diploma.
     *
     * @return true if yes
     */
    public boolean canGetRedDiploma() {
        List<Assessment> allLastGrades = maxAmounts.keySet().stream()
                .flatMap(this::getLastGrades)
                .toList();
        return allLastGrades.stream()
                .filter(assessment -> assessment.grade() == Grade.EXCELLENT)
                .count() / (double) allLastGrades.size() >= 0.75
                && allLastGrades.stream()
                .filter(assessment -> assessment.type() == AssessmentType.DIFFERENTIATED_CREDIT
                                                 || assessment.type() == AssessmentType.EXAM)
                .allMatch(assessment -> assessment.grade() != Grade.SATISFACTORY)
                && assessments.stream()
                .filter(assessment -> assessment.type() == AssessmentType.THESIS_DEFENSE)
                .findFirst()
                .orElse(new Assessment(AssessmentType.THESIS_DEFENSE, "", 8, Grade.EXCELLENT))
                .grade() == Grade.EXCELLENT;
    }

    /**
     * Can the student get increased scholarship.
     *
     * @return true if yes
     */
    public boolean canGetIncreasedScholarship() {
        if (currentSemester == 1) {
            return false;
        }

        return assessments.stream()
                .filter(assessment -> (assessment.type() == AssessmentType.DIFFERENTIATED_CREDIT
                                                 || assessment.type() == AssessmentType.CREDIT
                                                 || assessment.type() == AssessmentType.EXAM)
                                                 && assessment.semester() == currentSemester - 1)
                .allMatch(assessment -> assessment.grade() == Grade.EXCELLENT);
    }

    private Stream<Assessment> getLastGrades(AssessmentType assessmentType) {
        int lastSemester = getLastGradeSemester(assessmentType);
        List<Assessment> existing = assessments.stream()
                .filter(assessment -> assessment.semester() == lastSemester
                                                 && assessment.type() == assessmentType)
                .toList();
        return Stream.concat(existing.stream(),
            Stream.generate(() -> new Assessment(assessmentType, "", lastSemester, Grade.EXCELLENT))
                .limit(maxAmounts.get(assessmentType).get(Semester.fromIndex(lastSemester))
                        - existing.size()));
    }

    private int getLastGradeSemester(AssessmentType assessmentType) {
        return IntStream.range(0, maxAmounts.get(assessmentType).size())
                .map(i -> maxAmounts.get(assessmentType).size() - 1 - i)
                .filter(i -> maxAmounts.get(assessmentType).get(Semester.fromIndex(i)) != 0)
                .findFirst()
                .orElse(-1);
    }
}