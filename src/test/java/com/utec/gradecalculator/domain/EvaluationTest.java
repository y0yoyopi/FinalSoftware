package com.utec.gradecalculator.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.testng.AssertJUnit.*;

class EvaluationTest {

    @Test
    void shouldCreateEvaluationWhenValuesAreValid() {
        Evaluation e = new Evaluation(75.5, 20.0);
        assertEquals(75.5, e.getScore(), 0.0001);
        assertEquals(20.0, e.getWeight(), 0.0001);
        assertNotNull(e.toString());
    }

    @Test
    void shouldAllowBoundaryValuesForScoreAndWeight() {
        Evaluation e1 = new Evaluation(Evaluation.MIN_SCORE, Evaluation.MIN_WEIGHT);
        assertEquals(Evaluation.MIN_SCORE, e1.getScore(), 0.0);
        assertEquals(Evaluation.MIN_WEIGHT, e1.getWeight(), 0.0);

        Evaluation e2 = new Evaluation(Evaluation.MAX_SCORE, Evaluation.MAX_WEIGHT);
        assertEquals(Evaluation.MAX_SCORE, e2.getScore(), 0.0);
        assertEquals(Evaluation.MAX_WEIGHT, e2.getWeight(), 0.0);
    }

    @Test
    void shouldThrowWhenScoreBelowMinimum() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> new Evaluation(-0.1, 10.0));
        assertTrue(ex.getMessage().toLowerCase().contains("score"));
    }

    @Test
    void shouldThrowWhenScoreAboveMaximum() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> new Evaluation(100.1, 10.0));
        assertTrue(ex.getMessage().toLowerCase().contains("score"));
    }

    @Test
    void shouldThrowWhenWeightNegative() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> new Evaluation(50.0, -1.0));
        assertTrue(ex.getMessage().toLowerCase().contains("weight"));
    }

    @Test
    void shouldThrowWhenWeightAboveMaximum() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> new Evaluation(50.0, 100.1));
        assertTrue(ex.getMessage().toLowerCase().contains("weight"));
    }

    @Test
    void equalsAndHashCode_shouldWorkForSameValues() {
        Evaluation a = new Evaluation(60.0, 30.0);
        Evaluation b = new Evaluation(60.0, 30.0);
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    void shouldThrowOnNaNOrInfiniteInputs() {
        assertThrows(IllegalArgumentException.class, () -> new Evaluation(Double.NaN, 10.0));
        assertThrows(IllegalArgumentException.class, () -> new Evaluation(50.0, Double.POSITIVE_INFINITY));
    }
}

