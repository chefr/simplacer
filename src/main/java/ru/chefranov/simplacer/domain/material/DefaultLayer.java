package ru.chefranov.simplacer.domain.material;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Default sediment Layer
 * @author Chefranov R.M.
 */
public class DefaultLayer implements Layer, Serializable {

    private static final long serialVersionUID = -32705611916885430L;

    private double[] composition;

    private double thickness;

    /**
     * @param numberOfGrains Number of tte grains in repo.
     */
    public DefaultLayer(int numberOfGrains) {
        composition = new double[numberOfGrains];
        thickness = 0.0;
    }

    /**
     * @param sediment Sediment
     * @param thickness Thickness, m, > 0.0
     */
    public DefaultLayer(Sediment sediment, double thickness) {
        composition = Arrays.stream(sediment.getComposition()).map(
                percentage -> percentage * thickness / 100.0).toArray();
        this.thickness = thickness;
    }

    /**
     * Returns the thickness, m.
     * @return Thickness, > 0.0
     */
    @Override
    public double getThickness() {
        return thickness;
    }

    /**
     * Returns the Grain percentage, %.
     * @param index Index of the Grain in the Grain Repository
     * @return Grain percentage, >= 0.0 && <= 100.0
     */
    public double getGrainPercentage(int index) {
        return composition[index] * 100.0 / thickness;
    }

    /**
     * Returns the array [grain index in the Grain Repository] ->
     * percentage of this Grain.
     * @return Composition
     */
    @Override
    public double[] getComposition() {
        double[] percentageComposition = new double[composition.length];
        for(int i = 0; i < composition.length; ++i) {
            percentageComposition[i] = getGrainPercentage(i);
        }
        return percentageComposition;
    }

    /**
     * Returns the array [grain index in the Grain Repository] ->
     * thickness of this Grain Layer.
     * @return Composition
     */
    @Override
    public double[] getCompositionInThickness() {
        return composition;
    }

    /**
     * Adds the Layer to this Layer and returns this.
     * @param other Layer
     * @return This
     */
    @Override
    public Layer add(Layer other) {
        thickness += other.getThickness();
        double[] otherComposition = other.getCompositionInThickness();
        for(int i = 0; i < composition.length; ++i) {
            composition[i] += otherComposition[i];
        }
        return this;
    }

    /**
     * Accumulates the Grain layer with specified thickness, m.
     * @param index Index of the Grain in the Grain Repository
     * @param thickness Thickness, > 0.0
     */
    @Override
    public void accumulate(int index, double thickness) {
        composition[index] += thickness;
        this.thickness += thickness;
    }

    /**
     * Erodes the share of the Layer
     * @param share Share, > 0.0 && < 1.0
     * @return Eroded Layer
     */
    @Override
    public Layer erode(double share) {
        Layer layer = new DefaultLayer(composition.length);
        for(int i = 0; i < composition.length; ++i) {
            layer.accumulate(i, erode(i, share));
        }
        return layer;
    }

    /**
     * Erodes the Grain layer and returns the thickness of eroded layer, m.
     * @param index Index of the Grain in the Grain Repository
     * @param share Share, > 0.0 && <= 1.0
     * @return Thickness of eroded Grain layer, >= 0.0
     */
    @Override
    public double erode(int index, double share) {
        double erodedThickness = composition[index] * share;
        composition[index] -= erodedThickness;
        thickness -= erodedThickness;
        return erodedThickness;
    }

    /**
     * Returns a string representation of this object.
     * @return String representation of this object
     */
    @Override
    public String toString() {
        return String.format("%s[thickness=%.2f, composition=%s]",
                getClass().getSimpleName(), thickness,
                Arrays.toString(composition));
    }
}
