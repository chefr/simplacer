package ru.chefranov.simplacer.domain.material;

import java.io.Serializable;
import java.util.Objects;

/**
 * Default Mineral.
 * @author Chefranov R.M.
 */
public class DefaultMineral implements Mineral, Serializable {

    private static final long serialVersionUID = -5021970761545134121L;

    private String name;

    private double density;

    /**
     * @param name Name
     * @param density Density, g/cm<sup>3</sup>, > 0.0
     */
    public DefaultMineral(String name, double density) {
        this.name = name;
        this.density = density;
    }

    /**
     * Returns the name.
     * @return Name
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Returns the density, g/cm<sup>3</sup>.
     * @return Density, > 0.0
     */
    @Override
    public double getDensity() {
        return density;
    }

    /**
     * Returns the result of comparing DefaultMineral objects for equality
     * based on their names.
     * @param otherObject Other object, presumably DefaultMineral class
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
        DefaultMineral other = (DefaultMineral)otherObject;
        return Objects.equals(name, other.name);
    }

    /**
     * Returns the hash code value based on the name.
     * @return Hash code value
     */
    @Override
    public int hashCode() {
        return name.hashCode();
    }

    /**
     * Returns a string representation of this object.
     * @return String representation of this object
     */
    @Override
    public String toString() {
        return String.format("%s[name=%s, density=%.2f]",
                getClass().getSimpleName(), name, density);
    }
}
