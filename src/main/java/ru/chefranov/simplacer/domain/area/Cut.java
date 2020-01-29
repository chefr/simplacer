package ru.chefranov.simplacer.domain.area;

import ru.chefranov.simplacer.domain.material.Layer;
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
     * Adds the Layer on top.
     * @param layer Layer
     */
    void addTopLayer(Layer layer);
}
