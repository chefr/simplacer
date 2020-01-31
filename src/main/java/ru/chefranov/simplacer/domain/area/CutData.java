package ru.chefranov.simplacer.domain.area;

import ru.chefranov.simplacer.domain.material.LayerData;

/**
 * Cut Data.
 * @author Chefranov R.M.
 */
public interface CutData {

    /**
     * Returns the top height, m.
     * @return Top height
     */
    double getTopHeight();

    /**
     * Returns the Layer Data from bottom to top.
     * @return Layer Data
     */
    LayerData[] getLayerData();

    /**
     * Returns the percentage of a grain in underlying, %.
     * @return Percentage of a grain in underlying, >= 0.0 & <= 100.0
     */
    double getUnderlying();
}
