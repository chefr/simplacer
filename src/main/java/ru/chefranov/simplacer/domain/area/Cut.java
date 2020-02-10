package ru.chefranov.simplacer.domain.area;

import ru.chefranov.simplacer.domain.material.Layer;
import ru.chefranov.simplacer.domain.material.LayerData;
import ru.chefranov.simplacer.domain.material.Sediment;

/**
 * Cut.
 * @author Chefranov R.M.
 */
public interface Cut {

    /**
     * Sets the default sediment.
     * @param sediment Sediment
     */
    void setDefaultSediment(Sediment sediment);

    /**
     * Sets the Layer on top without affecting top height.
     * @param layer Layer
     */
    void setLayerOnTop(Layer layer);

    /**
     * Returns the top height, m.
     * @return Top height
     */
    double getTopHeight();

    /**
     * Returns the default Sediment.
     * @return Sediment
     */
    Sediment getDefaultSediment();

    /**
     * Returns the Layer Data from bottom to top.
     * @param index Index of the Grain in the Grain Repository
     * @return Layer Data
     */
    LayerData[] getLayerData(int index);

    /**
     * Adds the Layer on top.
     * @param layer Layer
     */
    void addTopLayer(Layer layer);
}
