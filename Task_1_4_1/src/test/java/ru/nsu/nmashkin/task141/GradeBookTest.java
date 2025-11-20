package ru.nsu.nmashkin.task141;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class GradeBookTest {

    @Test
    void average() {
        GradeBook gb = new GradeBook(3, false);
        gb.addAssessment(new Assessment(AssessmentType.ASSIGNMENT, 1, Grade.EXCELLENT));
        gb.addAssessment(new Assessment(AssessmentType.ASSIGNMENT, 1, Grade.GOOD));
        gb.addAssessment(new Assessment(AssessmentType.CREDIT, 1, Grade.EXCELLENT));
        gb.addAssessment(new Assessment(AssessmentType.COLLOQUIUM, 1, Grade.SATISFACTORY));
        gb.addAssessment(new Assessment(AssessmentType.EXAM, 5, Grade.SATISFACTORY));
        gb.addAssessment(new Assessment(AssessmentType.CREDIT, 5, Grade.SATISFACTORY));
        assertEquals(4.25, gb.getCurrentAverage());
    }

    @Test
    void canTransferToBudget_cant() {
        GradeBook gb = new GradeBook(3, true);
        gb.addAssessment(new Assessment(AssessmentType.EXAM, 1, Grade.EXCELLENT));
        gb.addAssessment(new Assessment(AssessmentType.EXAM, 1, Grade.GOOD));
        gb.addAssessment(new Assessment(AssessmentType.EXAM, 2, Grade.FAIL));
        assertFalse(gb.canTransferToBudget());
    }

    @Test
    void canTransferToBudget_semester1() {
        GradeBook gb = new GradeBook(1, true);
        assertFalse(gb.canTransferToBudget());
    }

    @Test
    void canTransferToBudget_semester2() {
        GradeBook gb = new GradeBook(2, true);
        gb.addAssessment(new Assessment(AssessmentType.EXAM, 1, Grade.EXCELLENT));
        gb.addAssessment(new Assessment(AssessmentType.EXAM, 1, Grade.GOOD));
        assertTrue(gb.canTransferToBudget());
    }

    @Test
    void canTransferToBudget() {
        GradeBook gb = new GradeBook(4, true);
        gb.addAssessment(new Assessment(AssessmentType.EXAM, 1, Grade.FAIL));
        gb.addAssessment(new Assessment(AssessmentType.EXAM, 1, Grade.FAIL));
        gb.addAssessment(new Assessment(AssessmentType.EXAM, 2, Grade.EXCELLENT));
        gb.addAssessment(new Assessment(AssessmentType.EXAM, 3, Grade.GOOD));
        assertTrue(gb.canTransferToBudget());
    }

    @Test
    void canGetRedDiploma() {
        GradeBook gb = new GradeBook(4, true);
        gb.addAssessment(new Assessment(AssessmentType.EXAM, 1, Grade.FAIL));
        gb.addAssessment(new Assessment(AssessmentType.EXAM, 1, Grade.FAIL));
        gb.addAssessment(new Assessment(AssessmentType.EXAM, 2, Grade.EXCELLENT));
        gb.addAssessment(new Assessment(AssessmentType.EXAM, 3, Grade.GOOD));
        assertTrue(gb.canGetRedDiploma());
    }

    @Test
    void canGetRedDiploma_cant1() {
        GradeBook gb = new GradeBook(8, true);
        gb.addAssessment(new Assessment(AssessmentType.EXAM, 7, Grade.SATISFACTORY));
        gb.addAssessment(new Assessment(AssessmentType.EXAM, 2, Grade.EXCELLENT));
        gb.addAssessment(new Assessment(AssessmentType.EXAM, 3, Grade.GOOD));
        assertFalse(gb.canGetRedDiploma());
    }

    @Test
    void canGetRedDiploma_cant2() {
        GradeBook gb = new GradeBook(8, true);
        gb.addAssessment(new Assessment(AssessmentType.THESIS_DEFENSE, 8, Grade.GOOD));
        gb.addAssessment(new Assessment(AssessmentType.EXAM, 2, Grade.EXCELLENT));
        gb.addAssessment(new Assessment(AssessmentType.EXAM, 3, Grade.GOOD));
        assertFalse(gb.canGetRedDiploma());
    }

    @Test
    void canGetRedDiploma_cant3() {
        GradeBook gb = new GradeBook(8, true);
        gb.addAssessment(new Assessment(AssessmentType.DIFFERENTIATED_CREDIT, 8, Grade.GOOD));
        gb.addAssessment(new Assessment(AssessmentType.DIFFERENTIATED_CREDIT, 8, Grade.GOOD));
        gb.addAssessment(new Assessment(AssessmentType.DIFFERENTIATED_CREDIT, 8, Grade.GOOD));
        gb.addAssessment(new Assessment(AssessmentType.DIFFERENTIATED_CREDIT, 8, Grade.GOOD));
        gb.addAssessment(new Assessment(AssessmentType.PRACTICE_REPORT_DEFENSE, 8, Grade.GOOD));
        gb.addAssessment(new Assessment(AssessmentType.PRACTICE_REPORT_DEFENSE, 8, Grade.GOOD));
        gb.addAssessment(new Assessment(AssessmentType.EXAM, 2, Grade.EXCELLENT));
        gb.addAssessment(new Assessment(AssessmentType.EXAM, 3, Grade.GOOD));
        assertFalse(gb.canGetRedDiploma());
    }

    @Test
    void canGetIncreasedScholarship_semester1() {
        GradeBook gb = new GradeBook(1, true);
        assertFalse(gb.canGetIncreasedScholarship());
    }

    @Test
    void canGetIncreasedScholarship_cant() {
        GradeBook gb = new GradeBook(8, true);
        gb.addAssessment(new Assessment(AssessmentType.DIFFERENTIATED_CREDIT, 7,
                Grade.EXCELLENT));
        gb.addAssessment(new Assessment(AssessmentType.DIFFERENTIATED_CREDIT, 7,
                Grade.EXCELLENT));
        gb.addAssessment(new Assessment(AssessmentType.DIFFERENTIATED_CREDIT, 7,
                Grade.EXCELLENT));
        gb.addAssessment(new Assessment(AssessmentType.DIFFERENTIATED_CREDIT, 7,
                Grade.EXCELLENT));
        gb.addAssessment(new Assessment(AssessmentType.EXAM, 7, Grade.EXCELLENT));
        gb.addAssessment(new Assessment(AssessmentType.CREDIT, 7, Grade.FAIL));
        assertFalse(gb.canGetIncreasedScholarship());
    }

    @Test
    void canGetIncreasedScholarship() {
        GradeBook gb = new GradeBook(8, true);
        gb.addAssessment(new Assessment(AssessmentType.DIFFERENTIATED_CREDIT, 7,
                                        Grade.EXCELLENT));
        gb.addAssessment(new Assessment(AssessmentType.DIFFERENTIATED_CREDIT, 7,
                                        Grade.EXCELLENT));
        gb.addAssessment(new Assessment(AssessmentType.DIFFERENTIATED_CREDIT, 7,
                                        Grade.EXCELLENT));
        gb.addAssessment(new Assessment(AssessmentType.DIFFERENTIATED_CREDIT, 7,
                                        Grade.EXCELLENT));
        gb.addAssessment(new Assessment(AssessmentType.EXAM, 7, Grade.EXCELLENT));
        gb.addAssessment(new Assessment(AssessmentType.CREDIT, 7, Grade.EXCELLENT));
        assertTrue(gb.canGetIncreasedScholarship());
    }

    @Test
    void addAssessment() {
        GradeBook gb = new GradeBook(8, true);
        assertTrue(gb.addAssessment(new Assessment(AssessmentType.EXAM, 1, Grade.GOOD)));
    }

    @Test
    void addAssessment_cant1() {
        GradeBook gb = new GradeBook(1, true);
        assertFalse(gb.addAssessment(new Assessment(AssessmentType.EXAM, 3, Grade.GOOD)));
    }

    @Test
    void addAssessment_cant2() {
        GradeBook gb = new GradeBook(8, true);
        assertFalse(gb.addAssessment(new Assessment(AssessmentType.EXAM, 8, Grade.GOOD)));
    }
}