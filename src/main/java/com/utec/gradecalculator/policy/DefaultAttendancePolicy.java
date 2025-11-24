package com.utec.gradecalculator.policy;

/**
 * Implementación simple:
 * - Si hasReachedMinClasses == true => 0.0
 * - Si es false => devuelve penaltyPointsIfNotReached (valor absoluto, configurable)
 *
 * Esta clase es inmutable y valida su parámetro en el constructor.
 */
public final class DefaultAttendancePolicy implements AttendancePolicy {

    private final double penaltyPointsIfNotReached;

    public DefaultAttendancePolicy(double penaltyPointsIfNotReached) {
        if (Double.isNaN(penaltyPointsIfNotReached) || Double.isInfinite(penaltyPointsIfNotReached)) {
            throw new IllegalArgumentException("penaltyPointsIfNotReached must be a finite number");
        }
        if (penaltyPointsIfNotReached < 0.0) {
            throw new IllegalArgumentException("penaltyPointsIfNotReached must be >= 0");
        }
        this.penaltyPointsIfNotReached = penaltyPointsIfNotReached;
    }

    @Override
    public double computePenalty(boolean hasReachedMinClasses, double weightedAverage) {
        return hasReachedMinClasses ? 0.0 : penaltyPointsIfNotReached;
    }

    public double getPenaltyPointsIfNotReached() {
        return penaltyPointsIfNotReached;
    }
}
