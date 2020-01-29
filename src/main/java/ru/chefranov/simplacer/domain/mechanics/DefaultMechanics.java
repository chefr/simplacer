package ru.chefranov.simplacer.domain.mechanics;

import ru.chefranov.simplacer.domain.material.Grain;
import ru.chefranov.simplacer.domain.material.MaterialRepo;

import java.util.Arrays;

/**
 * Default Mechanics. Calculations on (Lalomov, Tabolich, 2013,
 * formulas 2.26, 2.39, 2.43, p. 68-69).
 * @author Chefranov R.M.
 */
public class DefaultMechanics implements Mechanics {

    private ConstantHandler constants;

    private double[] nonerodingVelocities;

    /**
     * @param constants Constant Handler
     * @param repo Material Repository
     */
    public DefaultMechanics(ConstantHandler constants, MaterialRepo repo) {
        this.constants = constants;
        nonerodingVelocities = Arrays.stream(repo.getGrains()).
                mapToDouble(this::getGrainNonerodingVelocity).toArray();
    }

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
    @Override
    public double calculateGrainInitialVelocity(Grain grain, int index,
                                                double flowVelocity,
                                                double angle) {
        double FS = constants.getConstant("STATIC_RES_START_MOTION_COEF_X");
        double FA = constants.getConstant("PART_WEIGHT_INCR_COEF");
        double angCoef = Math.cos(angle) - Math.sin(angle) / FS * FA;
        double nrdVelConsAng = nonerodingVelocities[index] *
                ((grain.getDiameter() < 0.01) ?
                        angCoef : Math.pow(angCoef, 0.77));
        return ((nrdVelConsAng < flowVelocity) ?
                flowVelocity - nrdVelConsAng : 0.0);
    }

    /**
     * Returns the noneroding velocity of the specified grain on the horizontal
     * surface.
     * @param grain Grain
     * @return Grain noneroding velocity
     */
    private double getGrainNonerodingVelocity(Grain grain) {
        double WD = constants.getConstant("WATER_DENSITY");
        double rho = (grain.getMineral().getDensity() - WD) / WD;
        double NU = constants.getConstant("WATER_VISCOSITY_COEF");
        double G = constants.getConstant("GRAVITY");
        double diameter = grain.getDiameter();
        return ((diameter < 0.025)?
                    3.75 * Math.pow(NU, 0.3) * Math.pow(G * rho, 0.35) *
                            Math.pow(diameter, 0.05) :
                    2.36 * Math.pow(NU, 0.136) * Math.pow(G * rho, 0.432) *
                            Math.pow(diameter, 0.296));
    }
}
