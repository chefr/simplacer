package ru.chefranov.simplacer.domain.material;

/**
 * Material Repository.
 * @author Chefranov R.M.
 */
public interface MaterialRepo {

    /**
     * Returns minerals.
     * @return Minerals
     */
    Mineral[] getMinerals();

    /**
     * Returns grains.
     * @return Grains
     */
    Grain[] getGrains();

    /**
     * Returns the number of grains.
     */
    int getNumberOfGrains();

    /**
     * Returns the index of the grain specified by identifier.
     * @param identifier Identifier
     * @return Index or -1 if there is no grain with the specified identifier
     */
    int getGrainIndex(String identifier);
}
