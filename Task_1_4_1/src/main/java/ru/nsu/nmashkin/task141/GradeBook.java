package ru.nsu.nmashkin.task141;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class GradeBook {
    private final Map<AssessmentType, int[]> maxAmounts = new HashMap<>();
    private final int currentSemester;
    private final boolean isPaidEducation;
    private List<Assessment> assessments = new ArrayList<>();

    private void setMaxAmounts() {
        maxAmounts.put(AssessmentType.ASSIGNMENT, new int[]{15, 2, 2, 3, 2, 2, 2, 2, 0});
        maxAmounts.put(AssessmentType.CONTROL_WORK, new int[]{13, 3, 3, 2, 1, 2, 2, 0, 0});
        maxAmounts.put(AssessmentType.COLLOQUIUM, new int[]{2, 1, 1, 0, 0, 0, 0, 0, 0});
        maxAmounts.put(AssessmentType.EXAM, new int[]{19, 3, 3, 2, 5, 3, 2, 1, 0});
        maxAmounts.put(AssessmentType.DIFFERENTIATED_CREDIT, new int[]{35, 3, 3, 6, 5, 4, 6, 4, 4});
        maxAmounts.put(AssessmentType.CREDIT, new int[]{7, 3, 2, 0, 0, 0, 0, 1, 1});
        maxAmounts.put(AssessmentType.PRACTICE_REPORT_DEFENSE, new int[]{3, 0, 0, 0, 0, 0, 0, 1, 2});
        maxAmounts.put(AssessmentType.THESIS_DEFENSE, new int[]{1, 0, 0, 0, 0, 0, 0, 0, 1});
    }

    public GradeBook(int currentSemester, boolean isPaidEducation) {
        this.currentSemester = currentSemester;
        this.isPaidEducation = isPaidEducation;
        setMaxAmounts();
    }

    public boolean addAssessment(Assessment assessment) {
        long sameCount = assessments.stream()
                            .filter(a -> a.type() == assessment.type()
                                                    && a.semester() == assessment.semester())
                            .count();
        if (sameCount >= maxAmounts.get(assessment.type())[assessment.semester()]) {
            return false;
        }

        assessments.add(assessment);
        return true;
    }

    public double getCurrentAverage() {
        return assessments.stream()
                .filter(assessment -> assessment.semester() <= currentSemester)
                .mapToInt(assessment -> assessment.grade().getVal())
                .average()
                .orElse(0.0);
    }

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

    public boolean canGetRedDiploma() {
        List<Assessment> allLastGrades = maxAmounts.keySet().stream()
                .flatMap(this::getLastGrades)
                .toList();
        return allLastGrades.stream()
                .filter(assessment -> assessment.grade() == Grade.EXCELLENT)
                .count() / (double)allLastGrades.size() >= 0.75
                && allLastGrades.stream()
                .filter(assessment -> assessment.type() == AssessmentType.DIFFERENTIATED_CREDIT
                                                 || assessment.type() == AssessmentType.EXAM)
                .allMatch(assessment -> assessment.grade() != Grade.SATISFACTORY)
                && assessments.stream()
                .filter(assessment -> assessment.type() == AssessmentType.THESIS_DEFENSE)
                .findFirst()
                .orElse(new Assessment(AssessmentType.THESIS_DEFENSE, 8, Grade.EXCELLENT))
                .grade() == Grade.EXCELLENT;
    }

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
        Stream<Assessment> existing = assessments.stream()
                        .filter(assessment -> assessment.semester() == lastSemester
                                                         && assessment.type() == assessmentType);
        return Stream.concat(existing,
            Stream.generate(() -> new Assessment(assessmentType, lastSemester, Grade.EXCELLENT))
                .limit(maxAmounts.get(assessmentType)[lastSemester] - existing.count()));
    }

    private int getLastGradeSemester(AssessmentType assessmentType) {
        return IntStream.range(0, maxAmounts.get(assessmentType).length)
                .map(i -> maxAmounts.get(assessmentType).length - 1 - i)
                .filter(i -> maxAmounts.get(assessmentType)[i] != 0)
                .findFirst()
                .orElse(-1);
    }

    private boolean hasNoSatisfactoryGradesInSession(List<Assessment> sessionAssessments) {
        return sessionAssessments.stream()
                .noneMatch(assessment -> assessment.grade() == Grade.SATISFACTORY);
    }

    private boolean hasExcellentGradePercentage() {
        long totalGrades = assessments.stream()
                .filter(assessment -> assessment.semester() <= currentSemester)
                .count();

        long excellentGrades = assessments.stream()
                .filter(assessment -> assessment.semester() <= currentSemester)
                .filter(assessment -> assessment.grade() == Grade.EXCELLENT)
                .count();

        return totalGrades > 0 && (double)excellentGrades / totalGrades >= 0.75;
    }

    private boolean hasNoSatisfactoryFinalGrades() {
        return assessments.stream()
                .filter(this::isFinalAssessment)
                .noneMatch(assessment -> assessment.grade() == Grade.SATISFACTORY);
    }

    private boolean isFinalAssessment(Assessment assessment) {
        return assessment.type() == AssessmentType.EXAM ||
                assessment.type() == AssessmentType.DIFFERENTIATED_CREDIT;
    }

    private List<Assessment> getCurrentSemesterAssessments() {
        return assessments.stream()
                .filter(assessment -> assessment.semester() == currentSemester)
                .collect(Collectors.toList());
    }
}