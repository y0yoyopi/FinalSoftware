package com.utec.gradecalculator.policy;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DefaultAttendancePolicyTest {

    @Test
    void shouldReturnZeroWhenHasReachedMinClasses() {
        AttendancePolicy p = new DefaultAttendancePolicy(5.0);
        assertEquals(0.0, p.computePenalty(true, 80.0), 0.0001);
    }

    @Test
    void shouldReturnPenaltyWhenNotReached() {
        AttendancePolicy p = new DefaultAttendancePolicy(3.5);
        assertEquals(3.5, p.computePenalty(false, 90.0), 0.0001);
    }

    @Test
    void constructorShouldRejectNegativePenalty() {
        assertThrows(IllegalArgumentException.class, () -> new DefaultAttendancePolicy(-0.1));
    }

    @Test
    void constructorShouldRejectNaNOrInfinite() {
        assertThrows(IllegalArgumentException.class, () -> new DefaultAttendancePolicy(Double.NaN));
        assertThrows(IllegalArgumentException.class, () -> new DefaultAttendancePolicy(Double.POSITIVE_INFINITY));
    }

    @Test
    void computeIsDeterministic() {
        AttendancePolicy p = new DefaultAttendancePolicy(2.0);
        double first = p.computePenalty(false, 70.0);
        double second = p.computePenalty(false, 70.0);
        assertEquals(first, second, 0.0);
    }

    @Test
    void weightedAverageArgumentCanBeIgnored() {
        AttendancePolicy p = new DefaultAttendancePolicy(4.0);
        assertEquals(4.0, p.computePenalty(false, 0.0), 0.0001);
        assertEquals(4.0, p.computePenalty(false, 100.0), 0.0001);
    }
}
