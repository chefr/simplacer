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
}
