package ru.chefranov.simplacer.domain.area;

/**
 * Wave.
 * @author Chefranov R.M.
 */
public interface Wave {

    /**
     * Returns the direction, rad
     * @return Direction, 0.0 - 2 * Pi
     */
    double getDirection();

    /**
     * Returns the velocity by height.
     * @param height Height, m
     * @return  Velocity, m/s, >= 0.0
     */
    double getVelocityByHeight(double height);
}
