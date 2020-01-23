package ru.chefranov.simplacer.domain.material;

import java.util.Map;

/**
 * Sediment.
 * @author Chefranov R.M.
 */
public interface Sediment {

    /**
     * Returns the Grain percentage, %.
     * @param grain Grain
     * @return Grain percentage, >= 0.0 && <= 100.0
     */
    double getGrainPercentage(Grain grain);

    /**
     * Returns the composition as (grain -> percentage) map.
     * @return Composition
     */
    public Map<Grain, Double> getComposition();
}
