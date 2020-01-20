package ru.chefranov.simplacer.domain.material;

/**
 * Mineral.
 * @author Chefranov R.M.
 */
public interface Mineral {

    /**
     * Returns the name.
     * @return Name
     */
    String getName();

    /**
     * Returns the density, g/cm<sup>3</sup>.
     * @return Density, > 0.0
     */
    double getDensity();
}
