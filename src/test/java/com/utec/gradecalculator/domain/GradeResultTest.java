package com.utec.gradecalculator.domain;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GradeResultTest {

    @Test
    void shouldCreateGradeResultWhenValuesAreValid() {
        GradeResult r = new GradeResult(80.0, 0.0, 2.5, 82.5, "detalle");
        assertEquals(80.0, r.getWeightedAverage(), 0.0001);
        assertEquals(0.0, r.getPenalty(), 0.0001);
        assertEquals(2.5, r.getExtraPoints(), 0.0001);
        assertEquals(82.5, r.getFinalGrade(), 0.0001);
        assertEquals("detalle", r.getDetail());
        assertNotNull(r.toString());
    }

    @Test
    void shouldTreatNullDetailAsEmptyString() {
        GradeResult r = new GradeResult(70.0, 1.0, 0.0, 69.0, null);
        assertEquals("", r.getDetail());
    }

    @Test
    void equalsAndHashCode_shouldWorkForSameValues() {
        GradeResult a = new GradeResult(60.0, 2.0, 1.0, 59.0, "d");
        GradeResult b = new GradeResult(60.0, 2.0, 1.0, 59.0, "d");
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    void shouldThrowWhenWeightedAverageOutOfRange() {
        assertThrows(IllegalArgumentException.class, () ->
                new GradeResult(-0.1, 0.0, 0.0, 0.0, ""));
        assertThrows(IllegalArgumentException.class, () ->
                new GradeResult(100.1, 0.0, 0.0, 0.0, ""));
    }

    @Test
    void shouldThrowWhenPenaltyOrExtraNegative() {
        assertThrows(IllegalArgumentException.class, () ->
                new GradeResult(50.0, -0.1, 0.0, 50.0, ""));
        assertThrows(IllegalArgumentException.class, () ->
                new GradeResult(50.0, 0.0, -0.1, 50.0, ""));
    }

    @Test
    void shouldThrowWhenFinalGradeOutOfRange() {
        assertThrows(IllegalArgumentException.class, () ->
                new GradeResult(50.0, 0.0, 0.0, -0.1, ""));
        assertThrows(IllegalArgumentException.class, () ->
                new GradeResult(50.0, 0.0, 0.0, 100.01, ""));
    }

    @Test
    void shouldThrowOnNaNOrInfiniteInputs() {
        assertThrows(IllegalArgumentException.class, () ->
                new GradeResult(Double.NaN, 0.0, 0.0, 0.0, ""));
        assertThrows(IllegalArgumentException.class, () ->
                new GradeResult(50.0, Double.POSITIVE_INFINITY, 0.0, 50.0, ""));
    }
}

