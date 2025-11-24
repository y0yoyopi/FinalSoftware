package com.utec.gradecalculator.service;

import com.utec.gradecalculator.domain.GradeResult;
import com.utec.gradecalculator.domain.Evaluation;
import com.utec.gradecalculator.domain.Student;
import com.utec.gradecalculator.policy.AttendancePolicy;
import com.utec.gradecalculator.policy.ExtraPointsPolicy;

import java.util.List;
import java.util.Objects;

/**
 * Servicio que orquesta el cálculo de la nota final.
 * - No tiene estado mutable.
 * - Es determinista.
 */
public final class GradeCalculator {

    /**
     * Calcula la nota final del estudiante usando las políticas provistas.
     *
     * @param student            estudiante (no null)
     * @param attendancePolicy   política de asistencia (no null)
     * @param extraPointsPolicy  política de puntos extra (no null)
     * @param allYearsTeachers   valor boolean que representa el consenso colectivo
     * @return GradeResult con detalle del cálculo
     */
    public GradeResult calculateFinalGrade(
            Student student,
            AttendancePolicy attendancePolicy,
            ExtraPointsPolicy extraPointsPolicy,
            boolean allYearsTeachers) {

        Objects.requireNonNull(student, "student must not be null");
        Objects.requireNonNull(attendancePolicy, "attendancePolicy must not be null");
        Objects.requireNonNull(extraPointsPolicy, "extraPointsPolicy must not be null");

        List<Evaluation> evaluations = student.getEvaluations();

        double sumWeightedScores = 0.0;
        double sumWeights = 0.0;
        for (Evaluation e : evaluations) {
            // Evaluations already validadas; asumimos score y weight válidos
            sumWeightedScores += e.getScore() * e.getWeight();
            sumWeights += e.getWeight();
        }

        final double weightedAverage;
        if (sumWeights > 0.0) {
            weightedAverage = sumWeightedScores / sumWeights;
        } else {
            // Definición: si no hay peso (o no hay evaluaciones) el promedio es 0.0
            // Esto facilita manejo de caso borde en la rúbrica.
            // También se incluye en el detalle.
            weightedAverage = 0.0;
        }

        double penalty = attendancePolicy.computePenalty(student.hasReachedMinClasses(), weightedAverage);
        if (Double.isNaN(penalty) || Double.isInfinite(penalty) || penalty < 0.0) {
            throw new IllegalStateException("AttendancePolicy returned invalid penalty: " + penalty);
        }

        double extra = extraPointsPolicy.computeExtraPoints(allYearsTeachers, student);
        if (Double.isNaN(extra) || Double.isInfinite(extra) || extra < 0.0) {
            throw new IllegalStateException("ExtraPointsPolicy returned invalid extra points: " + extra);
        }

        double rawFinal = weightedAverage - penalty + extra;
        double finalGrade = clamp(rawFinal, 0.0, 100.0);

        String detail = buildDetail(weightedAverage, sumWeights, penalty, extra, finalGrade);

        return new GradeResult(weightedAverage, penalty, extra, finalGrade, detail);
    }

    private static double clamp(double v, double min, double max) {
        if (v < min) return min;
        if (v > max) return max;
        return v;
    }

    private static String buildDetail(double weightedAverage, double sumWeights,
                                      double penalty, double extraPoints, double finalGrade) {
        // Formato compacto y claro, cumple RF05
        return String.format(
                "weightedAverage=%.4f, sumWeights=%.4f, penalty=%.4f, extraPoints=%.4f, finalGrade=%.4f",
                weightedAverage, sumWeights, penalty, extraPoints, finalGrade
        );
    }
}
