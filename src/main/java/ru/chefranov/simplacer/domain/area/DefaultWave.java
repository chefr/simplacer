package ru.chefranov.simplacer.domain.area;

/**
 * Default Wave.
 * @author Chefranov R.M.
 */
public class DefaultWave implements Wave {

    private double criticalDepth;

    private double maxVelocity;

    private double direction;

    /**
     * @param maxVelocity Maximum velocity, m/s, > 0.0
     * @param direction Direction, grad, >= 0.0, < 360.0
     * @param criticalDepth Critical depth, m, < 0.0
     */
    public DefaultWave(double maxVelocity, double direction,
                       double criticalDepth) {
        this.maxVelocity = maxVelocity;
        this.direction = Math.toRadians(direction);
        this.criticalDepth = criticalDepth;
    }

    /**
     * Returns the direction, rad
     * @return Direction, 0.0 - 2 * Pi
     */
    public double getDirection() {
        return direction;
    }

    /**
     * Returns the velocity by height.
     * @param height Height, m
     * @return  Velocity, m/s, >= 0.0
     */
    public double getVelocityByHeight(double height) {
        return (height > 0.0 || height <= criticalDepth)? 0.0 :
                (1.0 - height / criticalDepth) * maxVelocity;
    }

    /**
     * Returns a string representation of this object.
     * @return String representation of this object
     */
    @Override
    public String toString() {
        return String.format(
                "%s[maxVelocity=%.1f, direction=%.1f, criticalDepth=%.1f]",
                getClass().getSimpleName(), maxVelocity, direction,
                criticalDepth);
    }
}
