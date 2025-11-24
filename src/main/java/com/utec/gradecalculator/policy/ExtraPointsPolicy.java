package com.utec.gradecalculator.policy;

import com.utec.gradecalculator.domain.Student;

/**
 * Política de puntos extra.
 *
 * Calcula los puntos extra (valor absoluto, p. ej. +2.5) que deben sumarse
 * a la nota final en función del consenso colectivo (allYearsTeachers) y, si
 * hace falta, información del estudiante.
 *
 * El método debe ser determinista y sin efectos secundarios.
 */
public interface ExtraPointsPolicy {

    /**
     * Calcula puntos extra absolutos (>= 0).
     *
     * @param allYearsTeachers consenso colectivo (true/false)
     * @param student          estudiante (puede ser null si la implementación no lo usa)
     * @return puntos extra a sumar (>= 0)
     */
    double computeExtraPoints(boolean allYearsTeachers, Student student);
}

