package ru.chefranov.simplacer.domain.material;

/**
 * Layer Data.
 * @author Chefranov R.M.
 */
public interface LayerData {

    /**
     * Returns the thickness, m.
     * @return Thickness, > 0.0
     */
    double getThickness();

    /**
     * Returns the percentage of a grain, %.
     * @return Percentage of a grain, >= 0.0 & <= 100.0
     */
    double getPercentage();
}
