package com.utec.gradecalculator.domain;


import java.util.Objects;

/**
 * Resultado inmutable del cálculo de la nota final.
 *
 * Campos:
 *  - weightedAverage: promedio ponderado calculado (0..100)
 *  - penalty: puntos a restar por asistencia (>= 0)
 *  - extraPoints: puntos extra aplicados (>= 0)
 *  - finalGrade: nota final resultante (0..100)
 *  - detail: texto explicativo (puede ser vacío)
 *
 * Validaciones:
 *  - weightedAverage y finalGrade deben estar en [MIN_GRADE, MAX_GRADE]
 *  - penalty y extraPoints deben ser >= 0
 *
 * Nota: se decide validar rangos aquí para evitar resultados incoherentes al construir el objeto.
 */
public final class GradeResult {

    public static final double MIN_GRADE = 0.0;
    public static final double MAX_GRADE = 100.0;
    public static final double MIN_NON_NEGATIVE = 0.0;

    private final double weightedAverage;
    private final double penalty;
    private final double extraPoints;
    private final double finalGrade;
    private final String detail;

    public GradeResult(double weightedAverage,
                       double penalty,
                       double extraPoints,
                       double finalGrade,
                       String detail) {

        validateFinite(weightedAverage, "weightedAverage");
        validateFinite(penalty, "penalty");
        validateFinite(extraPoints, "extraPoints");
        validateFinite(finalGrade, "finalGrade");

        if (weightedAverage < MIN_GRADE || weightedAverage > MAX_GRADE) {
            throw new IllegalArgumentException(
                    String.format("weightedAverage must be between %.1f and %.1f (was: %s)",
                            MIN_GRADE, MAX_GRADE, weightedAverage));
        }
        if (penalty < MIN_NON_NEGATIVE) {
            throw new IllegalArgumentException("penalty must be >= 0");
        }
        if (extraPoints < MIN_NON_NEGATIVE) {
            throw new IllegalArgumentException("extraPoints must be >= 0");
        }
        if (finalGrade < MIN_GRADE || finalGrade > MAX_GRADE) {
            throw new IllegalArgumentException(
                    String.format("finalGrade must be between %.1f and %.1f (was: %s)",
                            MIN_GRADE, MAX_GRADE, finalGrade));
        }

        this.weightedAverage = weightedAverage;
        this.penalty = penalty;
        this.extraPoints = extraPoints;
        this.finalGrade = finalGrade;
        this.detail = (detail == null) ? "" : detail;
    }

    private static void validateFinite(double v, String name) {
        if (Double.isNaN(v) || Double.isInfinite(v)) {
            throw new IllegalArgumentException(name + " must be a finite number");
        }
    }

    public double getWeightedAverage() {
        return weightedAverage;
    }

    public double getPenalty() {
        return penalty;
    }

    public double getExtraPoints() {
        return extraPoints;
    }

    public double getFinalGrade() {
        return finalGrade;
    }

    public String getDetail() {
        return detail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GradeResult)) return false;
        GradeResult that = (GradeResult) o;
        return Double.compare(that.weightedAverage, weightedAverage) == 0 &&
                Double.compare(that.penalty, penalty) == 0 &&
                Double.compare(that.extraPoints, extraPoints) == 0 &&
                Double.compare(that.finalGrade, finalGrade) == 0 &&
                Objects.equals(detail, that.detail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(weightedAverage, penalty, extraPoints, finalGrade, detail);
    }

    @Override
    public String toString() {
        return "GradeResult{" +
                "weightedAverage=" + weightedAverage +
                ", penalty=" + penalty +
                ", extraPoints=" + extraPoints +
                ", finalGrade=" + finalGrade +
                ", detail='" + detail + '\'' +
                '}';
    }
}
