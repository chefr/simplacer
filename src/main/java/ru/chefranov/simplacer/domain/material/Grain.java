package ru.chefranov.simplacer.domain.material;

/**
 * Grain.
 * @author Chefranov R.M.
 */
public interface Grain {

    /**
     * Returns the identifier.
     * @return Identifier
     */
    String getIdentifier();

    /**
     * Returns the Mineral.
     * @return Mineral
     */
    Mineral getMineral();

    /**
     * Returns the diameter, cm.
     * @return Diameter, > 0.0 && <= 0.1
     */
    double getDiameter();
}
