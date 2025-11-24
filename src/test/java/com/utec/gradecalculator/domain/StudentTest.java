package com.utec.gradecalculator.domain;


import static org.junit.jupiter.api.Assertions.*;
import static org.testng.AssertJUnit.assertEquals;

import org.junit.jupiter.api.Test;

import java.util.List;

class StudentTest {

    @Test
    void shouldCreateStudentWithValidId() {
        Student s = new Student("2023001");
        assertEquals("2023001", s.getId());
    }

    @Test
    void shouldFailWhenStudentIdIsBlank() {
        assertThrows(IllegalArgumentException.class, () -> new Student("  "));
    }

    @Test
    void shouldAddEvaluationSuccessfully() {
        Student s = new Student("1");
        s.addEvaluation(new Evaluation(15, 50));
        assertEquals(1, s.getEvaluations().size());
    }

    @Test
    void shouldNotAllowMoreThan10Evaluations() {
        Student s = new Student("1");

        for (int i = 0; i < 10; i++) {
            s.addEvaluation(new Evaluation(10, 10));
        }

        assertThrows(IllegalStateException.class, () ->
                s.addEvaluation(new Evaluation(20, 10)));
    }

    @Test
    void shouldSetAttendanceCorrectly() {
        Student s = new Student("1");
        s.setHasReachedMinClasses(true);
        assertTrue(s.hasReachedMinClasses());
    }

    @Test
    void shouldReturnUnmodifiableEvaluationList() {
        Student s = new Student("1");
        s.addEvaluation(new Evaluation(10, 50));

        List<Evaluation> list = s.getEvaluations();
        assertThrows(UnsupportedOperationException.class, () -> list.add(null));
    }
}
