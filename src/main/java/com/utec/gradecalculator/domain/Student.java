package com.utec.gradecalculator.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Entidad Student minimalista para el examen.
 * - Mantiene lista de evaluations (máx. MAX_EVALUATIONS)
 * - Estado de asistencia: hasReachedMinClasses
 */
public class Student {
    public static final int MAX_EVALUATIONS = 10;

    private final String id;
    private final List<Evaluation> evaluations = new ArrayList<>();
    private boolean hasReachedMinClasses;

    public Student(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Student id must not be null or empty");
        }
        this.id = id.trim();
    }

    public String getId() {
        return id;
    }

    public List<Evaluation> getEvaluations() {
        return Collections.unmodifiableList(evaluations);
    }

    public boolean hasReachedMinClasses() {
        return hasReachedMinClasses;
    }

    public void setHasReachedMinClasses(boolean hasReachedMinClasses) {
        this.hasReachedMinClasses = hasReachedMinClasses;
    }

    /**
     * Agrega una evaluación validando RNF01 (máx 10).
     */
    public void addEvaluation(Evaluation evaluation) {
        Objects.requireNonNull(evaluation, "evaluation must not be null");
        if (evaluations.size() >= MAX_EVALUATIONS) {
            throw new IllegalStateException("Maximum number of evaluations (" + MAX_EVALUATIONS + ") exceeded");
        }
        evaluations.add(evaluation);
    }

    /**
     * Remueve evaluación por índice.
     */
    public Evaluation removeEvaluation(int index) {
        return evaluations.remove(index);
    }

    public void clearEvaluations() {
        evaluations.clear();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        Student student = (Student) o;
        return Objects.equals(id, student.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}

