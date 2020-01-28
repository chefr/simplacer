package ru.chefranov.simplacer.domain.material;

/**
 * Sediment Layer.
 * @author Chefranov R.M.
 */
public interface Layer extends GrainContainer {

    /**
     * Returns the thickness, m.
     * @return Thickness, > 0.0
     */
    double getThickness();

    /**
     * Accumulates the Grain layer with specified thickness, m.
     * @param index Index of the Grain in the Grain Repository
     * @param thickness Thickness, > 0.0
     */
    void accumulate(int index, double thickness);

    /**
     * Erodes the share of the Grain layer and returns the thickness of this
     * layer, m.
     * @param index Index of the Grain in the Grain Repository
     * @param share Share, > 0.0 && < 1.0
     * @return Thickness of eroded Grain layer, >= 0.0
     */
    double erode(int index, double share);
}
