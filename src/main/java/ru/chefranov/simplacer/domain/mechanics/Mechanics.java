package ru.chefranov.simplacer.domain.mechanics;

import ru.chefranov.simplacer.domain.material.Grain;

/**
 * Mechanics
 * @author Chefranov R.M.
 */
public interface Mechanics {

    /**
     * Calculates the grain initial velocity (m/s) with the specified surface
     * angle (degree) under the action of the flow with the specified
     * velocity (m/s).
     * @param grain Grain
     * @param index Index of the Grain in the Grain Repository
     * @param flowVelocity Flow velocity, > 0.0
     * @param angle Surface angle, rad
     * @return Grain initial velocity, > 0.0
     */
    public double calculateGrainInitialVelocity(Grain grain, int index,
                                                double flowVelocity,
                                                double angle);
}
