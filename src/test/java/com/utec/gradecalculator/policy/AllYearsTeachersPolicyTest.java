package com.utec.gradecalculator.policy;

import com.utec.gradecalculator.domain.Student;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AllYearsTeachersPolicyTest {

    @Test
    void shouldReturnConfiguredExtraWhenConsensusTrue() {
        ExtraPointsPolicy p = new AllYearsTeachersPolicy(2.5);
        Student dummy = new Student("s1");
        assertEquals(2.5, p.computeExtraPoints(true, dummy), 0.0001);
    }

    @Test
    void shouldReturnZeroWhenConsensusFalse() {
        ExtraPointsPolicy p = new AllYearsTeachersPolicy(2.5);
        Student dummy = new Student("s1");
        assertEquals(0.0, p.computeExtraPoints(false, dummy), 0.0001);
    }

    @Test
    void constructorShouldRejectNegativeExtra() {
        assertThrows(IllegalArgumentException.class, () -> new AllYearsTeachersPolicy(-0.1));
    }

    @Test
    void constructorShouldRejectNaNOrInfinite() {
        assertThrows(IllegalArgumentException.class, () -> new AllYearsTeachersPolicy(Double.NaN));
        assertThrows(IllegalArgumentException.class, () -> new AllYearsTeachersPolicy(Double.POSITIVE_INFINITY));
    }

    @Test
    void computeIsDeterministic() {
        ExtraPointsPolicy p = new AllYearsTeachersPolicy(1.0);
        double first = p.computeExtraPoints(true, null);
        double second = p.computeExtraPoints(true, null);
        assertEquals(first, second, 0.0);
    }

    @Test
    void shouldIgnoreStudentWhenNotNeeded() {
        ExtraPointsPolicy p = new AllYearsTeachersPolicy(3.0);
        // student puede ser null sin lanzar NPE en esta implementación
        assertEquals(3.0, p.computeExtraPoints(true, null), 0.0001);
    }
}

