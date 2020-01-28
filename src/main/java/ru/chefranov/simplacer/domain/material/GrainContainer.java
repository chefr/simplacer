package ru.chefranov.simplacer.domain.material;

/**
 * Grain Container.
 * @author Chefranov R.M.
 */
public interface GrainContainer {

    /**
     * Returns the Grain percentage, %.
     * @param index Index of the Grain in the Grain Repository
     * @return Grain percentage, >= 0.0 && <= 100.0
     */
    double getGrainPercentage(int index);

    /**
     * Returns the array [grain index in the Grain Repository] ->
     * percentage of this Grain.
     * @return Composition
     */
    double[] getComposition();
}
