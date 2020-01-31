package ru.chefranov.simplacer.domain.area;

import ru.chefranov.simplacer.domain.material.LayerData;

import java.util.Arrays;

/**
 * Default Cut Data.
 * @author Chefranov R.M.
 */
public class DefaultCutData implements CutData {

    private double topHeight;

    private LayerData[] layerData;

    private double underlying;

    /**
     * @param topHeight Top height
     * @param layerData Layer Data from bottom to top
     * @param underlying Underlying, %, >= 0.0 & <= 100.0
     */
    public DefaultCutData(double topHeight, LayerData[] layerData,
                          double underlying) {
        this.topHeight = topHeight;
        this.layerData = layerData;
        this.underlying = underlying;
    }

    /**
     * Returns the top height, m.
     * @return Top height
     */
    @Override
    public double getTopHeight() {
        return topHeight;
    }

    /**
     * Returns the Layer Data from bottom to top.
     * @return Layer Data
     */
    @Override
    public LayerData[] getLayerData() {
        return layerData;
    }

    /**
     * Returns the percentage of a grain in underlying, %.
     * @return Percentage of a grain in underlying, >= 0.0 & <= 100.0
     */
    @Override
    public double getUnderlying() {
        return underlying;
    }

    /**
     * Returns the result of comparing DefaultCutData objects for equality.
     * @param otherObject Other object, presumably DefaultCutData class
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
        DefaultCutData other = (DefaultCutData)otherObject;
        return topHeight == other.topHeight &&
                Arrays.equals(layerData, other.layerData) &&
                underlying == other.underlying;
    }

    /**
     * Returns a string representation of this object.
     * @return String representation of this object
     */
    @Override
    public String toString() {
        return String.format(
                "%s[topHeight=%.1f, layerData=%s, underlying=%.2f]",
                getClass().getSimpleName(), topHeight,
                Arrays.toString(layerData), underlying);
    }
}
