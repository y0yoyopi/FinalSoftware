package com.utec.gradecalculator.service;

import com.utec.gradecalculator.domain.Evaluation;
import com.utec.gradecalculator.domain.GradeResult;
import com.utec.gradecalculator.domain.Student;
import com.utec.gradecalculator.policy.AllYearsTeachersPolicy;
import com.utec.gradecalculator.policy.DefaultAttendancePolicy;
import com.utec.gradecalculator.policy.AttendancePolicy;
import com.utec.gradecalculator.policy.ExtraPointsPolicy;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GradeCalculatorTest {

    private final GradeCalculator calc = new GradeCalculator();

    @Test
    void shouldCalculateFinalGradeNormally() {
        Student s = new Student("s1");
        s.addEvaluation(new Evaluation(80.0, 50.0));
        s.addEvaluation(new Evaluation(90.0, 50.0));
        s.setHasReachedMinClasses(true);

        AttendancePolicy ap = new DefaultAttendancePolicy(5.0); // won't apply
        ExtraPointsPolicy ep = new AllYearsTeachersPolicy(0.0); // no extra

        GradeResult r = calc.calculateFinalGrade(s, ap, ep, false);

        assertEquals(85.0, r.getWeightedAverage(), 1e-6);
        assertEquals(0.0, r.getPenalty(), 1e-6);
        assertEquals(0.0, r.getExtraPoints(), 1e-6);
        assertEquals(85.0, r.getFinalGrade(), 1e-6);
        assertTrue(r.getDetail().contains("weightedAverage"));
    }

    @Test
    void shouldApplyAttendancePenaltyWhenNotReached() {
        Student s = new Student("s2");
        s.addEvaluation(new Evaluation(70.0, 100.0));
        s.setHasReachedMinClasses(false);

        AttendancePolicy ap = new DefaultAttendancePolicy(10.0); // penaliza 10 puntos
        ExtraPointsPolicy ep = new AllYearsTeachersPolicy(0.0);

        GradeResult r = calc.calculateFinalGrade(s, ap, ep, false);

        assertEquals(70.0, r.getWeightedAverage(), 1e-6);
        assertEquals(10.0, r.getPenalty(), 1e-6);
        assertEquals(60.0, r.getFinalGrade(), 1e-6);
    }

    @Test
    void shouldApplyExtraPointsWhenAllYearsTeachersTrue() {
        Student s = new Student("s3");
        s.addEvaluation(new Evaluation(60.0, 100.0));
        s.setHasReachedMinClasses(true);

        AttendancePolicy ap = new DefaultAttendancePolicy(0.0);
        ExtraPointsPolicy ep = new AllYearsTeachersPolicy(2.5);

        GradeResult r = calc.calculateFinalGrade(s, ap, ep, true);

        assertEquals(60.0, r.getWeightedAverage(), 1e-6);
        assertEquals(2.5, r.getExtraPoints(), 1e-6);
        assertEquals(62.5, r.getFinalGrade(), 1e-6);
    }

    @Test
    void shouldHandleZeroEvaluationsGracefully() {
        Student s = new Student("s4");
        // no evaluations added
        s.setHasReachedMinClasses(true);

        AttendancePolicy ap = new DefaultAttendancePolicy(0.0);
        ExtraPointsPolicy ep = new AllYearsTeachersPolicy(0.0);

        GradeResult r = calc.calculateFinalGrade(s, ap, ep, false);

        assertEquals(0.0, r.getWeightedAverage(), 1e-6);
        assertEquals(0.0, r.getFinalGrade(), 1e-6);
        assertTrue(r.getDetail().contains("sumWeights=0.0000"));
    }

    @Test
    void finalGradeShouldBeClampedBetween0And100() {
        Student s = new Student("s5");
        s.addEvaluation(new Evaluation(99.0, 100.0));
        s.setHasReachedMinClasses(true);

        AttendancePolicy ap = new DefaultAttendancePolicy(0.0);
        ExtraPointsPolicy ep = new AllYearsTeachersPolicy(5.0);

        GradeResult r = calc.calculateFinalGrade(s, ap, ep, true);
        assertEquals(100.0, r.getFinalGrade(), 1e-6);

        // Now big penalty
        AttendancePolicy ap2 = new DefaultAttendancePolicy(200.0);
        ExtraPointsPolicy ep2 = new AllYearsTeachersPolicy(0.0);
        GradeResult r2 = calc.calculateFinalGrade(s, ap2, ep2, false);
        assertEquals(0.0, r2.getFinalGrade(), 1e-6);
    }

    @Test
    void calculateIsDeterministic() {
        Student s = new Student("s6");
        s.addEvaluation(new Evaluation(50.0, 50.0));
        s.addEvaluation(new Evaluation(70.0, 50.0));
        s.setHasReachedMinClasses(true);

        AttendancePolicy ap = new DefaultAttendancePolicy(1.0);
        ExtraPointsPolicy ep = new AllYearsTeachersPolicy(1.0);

        GradeResult r1 = calc.calculateFinalGrade(s, ap, ep, true);
        GradeResult r2 = calc.calculateFinalGrade(s, ap, ep, true);

        assertEquals(r1, r2);
        assertEquals(r1.getDetail(), r2.getDetail());
    }
}
