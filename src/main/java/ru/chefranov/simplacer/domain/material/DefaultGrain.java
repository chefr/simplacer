package ru.chefranov.simplacer.domain.material;

import java.util.Objects;

/**
 * Default Grain.
 * @author Chefranov R.M.
 */
public class DefaultGrain implements Grain {

    private String identifier;

    private Mineral mineral;

    private double diameter;

    /**
     * @param identifier Identifier
     * @param mineral Mineral
     * @param diameter Diameter, cm, > 0.0 && <= 0.1
     */
    public DefaultGrain(String identifier, Mineral mineral, double diameter) {
        this.identifier = identifier;
        this.mineral = mineral;
        this.diameter = diameter;
    }

    /**
     * Returns the identifier.
     * @return Identifier
     */
    @Override
    public String getIdentifier() {
        return identifier;
    }

    /**
     * Returns the Mineral.
     * @return Mineral
     */
    @Override
    public Mineral getMineral() {
        return mineral;
    }

    /**
     * Returns the diameter, cm.
     * @return Diameter, > 0.0 && <= 0.1
     */
    @Override
    public double getDiameter() {
        return diameter;
    }

    /**
     * Returns the result of comparing DefaultGrain objects for equality
     * based on their identifiers.
     * @param otherObject Other object, presumably DefaultGrain class
     * @return {@code true} if objects are equal to each other, {@code false}
     * otherwise
     */
    @Override
    public boolean equals(Object otherObject) {
        if(this == otherObject) {
            return true;
        }
        if(otherObject == null) {
            return false;
        }
        if(getClass() != otherObject.getClass()) {
            return false;
        }
        DefaultGrain other = (DefaultGrain)otherObject;
        return Objects.equals(identifier, other.identifier);
    }

    /**
     * Returns the hash code value based on the identifier.
     * @return Hash code value
     */
    @Override
    public int hashCode() {
        return identifier.hashCode();
    }

    /**
     * Returns a string representation of this object.
     * @return String representation of this object
     */
    @Override
    public String toString() {
        return String.format("%s[identifier=%s, mineral=%s, diameter=%.3f]",
                getClass().getSimpleName(), identifier, mineral, diameter);
    }
}
