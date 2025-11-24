package com.utec.gradecalculator.policy;


public interface AttendancePolicy {
    /**
     * Devuelve la penalización en puntos (>= 0). Si no hay penalización debe devolver 0.0.
     *
     * @param hasReachedMinClasses true si el estudiante alcanzó la asistencia mínima
     * @param weightedAverage      promedio ponderado calculado (puede ignorarse)
     * @return puntos a restar (>= 0)
     */
    double computePenalty(boolean hasReachedMinClasses, double weightedAverage);
}

