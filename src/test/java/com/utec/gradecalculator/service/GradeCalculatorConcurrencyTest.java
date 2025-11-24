package com.utec.gradecalculator.service;


import com.utec.gradecalculator.domain.Evaluation;
import com.utec.gradecalculator.domain.GradeResult;
import com.utec.gradecalculator.domain.Student;
import com.utec.gradecalculator.policy.AllYearsTeachersPolicy;
import com.utec.gradecalculator.policy.DefaultAttendancePolicy;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.*;

class GradeCalculatorConcurrencyTest {

    @Test
    void shouldSupport50ConcurrentCalculations() throws Exception {
        GradeCalculator calc = new GradeCalculator();
        Student s = new Student("concurrency");
        s.addEvaluation(new Evaluation(80.0, 50.0));
        s.addEvaluation(new Evaluation(90.0, 50.0));
        s.setHasReachedMinClasses(true);

        DefaultAttendancePolicy attendancePolicy = new DefaultAttendancePolicy(0.0);
        AllYearsTeachersPolicy extraPolicy = new AllYearsTeachersPolicy(0.0);

        int threads = 50;
        ExecutorService ex = Executors.newFixedThreadPool(threads);
        List<Future<GradeResult>> futures = new ArrayList<>();

        for (int i = 0; i < threads; i++) {
            futures.add(ex.submit(() -> calc.calculateFinalGrade(s, attendancePolicy, extraPolicy, false)));
        }

        GradeResult first = futures.get(0).get(1, TimeUnit.SECONDS);
        for (Future<GradeResult> f : futures) {
            GradeResult r = f.get(1, TimeUnit.SECONDS);
            assertEquals(first, r);
        }

        ex.shutdown();
        assertTrue(ex.awaitTermination(1, TimeUnit.SECONDS));
    }
}
