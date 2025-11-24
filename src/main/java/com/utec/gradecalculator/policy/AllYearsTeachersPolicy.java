package com.utec.gradecalculator.policy;

import com.utec.gradecalculator.domain.Student;

/**
 * Implementación simple:
 *  - Si allYearsTeachers == true => devuelve extraPointsConfigured
 *  - Si allYearsTeachers == false => devuelve 0.0
 *
 * Clase inmutable y valida el parámetro en el constructor.
 * No depende del estado interno del Student por defecto (pero el método recibe Student
 * por si en el futuro la política necesita consultar atributos del estudiante).
 */
public final class AllYearsTeachersPolicy implements ExtraPointsPolicy {

    private final double extraPointsConfigured;

    public AllYearsTeachersPolicy(double extraPointsConfigured) {
        if (Double.isNaN(extraPointsConfigured) || Double.isInfinite(extraPointsConfigured)) {
            throw new IllegalArgumentException("extraPointsConfigured must be a finite number");
        }
        if (extraPointsConfigured < 0.0) {
            throw new IllegalArgumentException("extraPointsConfigured must be >= 0");
        }
        this.extraPointsConfigured = extraPointsConfigured;
    }

    @Override
    public double computeExtraPoints(boolean allYearsTeachers, Student student) {
        return allYearsTeachers ? extraPointsConfigured : 0.0;
    }

    public double getExtraPointsConfigured() {
        return extraPointsConfigured;
    }
}

