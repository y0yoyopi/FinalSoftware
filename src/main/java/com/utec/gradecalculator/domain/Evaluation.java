package com.utec.gradecalculator.domain;

import java.util.Objects;

/**
 * Value object inmutable que representa una evaluación (nota y peso).
 *
 * Reglas / validaciones:
 *  - score (nota) debe estar en [MIN_SCORE, MAX_SCORE]
 *  - weight (peso) debe estar en [MIN_WEIGHT, MAX_WEIGHT]
 *
 * Esta clase evita "valores mágicos" exponiendo constantes públicas.
 */
public final class Evaluation {

    // Constantes reutilizables (evitan valores mágicos y ayudan a tests/sonar)
    public static final double MIN_SCORE = 0.0;
    public static final double MAX_SCORE = 100.0;

    public static final double MIN_WEIGHT = 0.0;
    public static final double MAX_WEIGHT = 100.0;

    private final double score;
    private final double weight;

    /**
     * Crea una instancia inmutable de Evaluation validando inputs.
     *
     * @param score  la nota obtenida (0..100)
     * @param weight el peso de la evaluación (0..100)
     * @throws IllegalArgumentException si alguno de los parámetros no cumple las reglas
     */
    public Evaluation(double score, double weight) {
        validateScore(score);
        validateWeight(weight);
        this.score = score;
        this.weight = weight;
    }

    private static void validateScore(double score) {
        if (Double.isNaN(score) || Double.isInfinite(score)) {
            throw new IllegalArgumentException("Score must be a finite number");
        }
        if (score < MIN_SCORE || score > MAX_SCORE) {
            throw new IllegalArgumentException(
                    String.format("Score must be between %.1f and %.1f (was: %s)", MIN_SCORE, MAX_SCORE, score)
            );
        }
    }

    private static void validateWeight(double weight) {
        if (Double.isNaN(weight) || Double.isInfinite(weight)) {
            throw new IllegalArgumentException("Weight must be a finite number");
        }
        if (weight < MIN_WEIGHT || weight > MAX_WEIGHT) {
            throw new IllegalArgumentException(
                    String.format("Weight must be between %.1f and %.1f (was: %s)", MIN_WEIGHT, MAX_WEIGHT, weight)
            );
        }
    }

    public double getScore() {
        return score;
    }

    public double getWeight() {
        return weight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Evaluation)) return false;
        Evaluation that = (Evaluation) o;
        return Double.compare(that.score, score) == 0 &&
                Double.compare(that.weight, weight) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(score, weight);
    }

    @Override
    public String toString() {
        return "Evaluation{" +
                "score=" + score +
                ", weight=" + weight +
                '}';
    }
}
