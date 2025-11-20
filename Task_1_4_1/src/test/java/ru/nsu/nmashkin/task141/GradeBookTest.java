package ru.nsu.nmashkin.task141;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
        GradeBook gb = new GradeBook(2, true);
        gb.addAssessment(new Assessment(AssessmentType.EXAM, 1, Grade.EXCELLENT));
        gb.addAssessment(new Assessment(AssessmentType.EXAM, 1, Grade.GOOD));
        gb.addAssessment(new Assessment(AssessmentType.EXAM, 2, Grade.FAIL));
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
        GradeBook gb = new GradeBook(3, true);
        gb.addAssessment(new Assessment(AssessmentType.EXAM, 1, Grade.FAIL));
        gb.addAssessment(new Assessment(AssessmentType.EXAM, 1, Grade.FAIL));
        gb.addAssessment(new Assessment(AssessmentType.EXAM, 2, Grade.EXCELLENT));
        gb.addAssessment(new Assessment(AssessmentType.EXAM, 3, Grade.GOOD));
        assertTrue(gb.canTransferToBudget());
    }
}