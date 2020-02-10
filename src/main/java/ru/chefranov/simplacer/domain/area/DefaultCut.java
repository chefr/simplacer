package ru.chefranov.simplacer.domain.area;

import ru.chefranov.simplacer.domain.material.DefaultLayerData;
import ru.chefranov.simplacer.domain.material.Layer;
import ru.chefranov.simplacer.domain.material.LayerData;
import ru.chefranov.simplacer.domain.material.Sediment;

import java.util.ArrayList;

/**
 * Default Cut.
 * @author Chefranov R.M.
 */
public class DefaultCut implements Cut {

    private double topHeight;

    private final ArrayList<Layer> layers = new ArrayList<>();

    private Sediment defaultSediment;

    /**
     * @param topHeight Top height
     */
    public DefaultCut(double topHeight) {
        this.topHeight = topHeight;
    }

    /**
     * Sets the default sediment.
     * @param sediment Sediment
     */
    @Override
    public void setDefaultSediment(Sediment sediment) {
        defaultSediment = sediment;
    }

    /**
     * Sets the Layer on top without affecting top height.
     * @param layer Layer
     */
    public void setLayerOnTop(Layer layer) {
        layers.add(layer);
    }

    /**
     * Returns the cut top height, m.
     * @return Cut top height
     */
    @Override
    public double getTopHeight() {
        return topHeight;
    }

    /**
     * Returns the default sediment.
     * @return Default Sediment
     */
    @Override
    public Sediment getDefaultSediment() {
        return defaultSediment;
    }

    /**
     * Returns the Layer Data from bottom to top.
     * @param index Index of the Grain in the Grain Repository
     * @return Layer Data
     */
    @Override
    public LayerData[] getLayerData(int index) {
        return layers.stream().
                map(layer -> new DefaultLayerData(layer.getThickness(),
                        layer.getGrainPercentage(index))).
                toArray(DefaultLayerData[]::new);
    }

    /**
     * Adds the layer on top.
     * @param layer Layer
     */
    @Override
    public void addTopLayer(Layer layer) {
        layers.add(layer);
        topHeight += layer.getThickness();
    }

    /**
     * Returns a string representation of this object.
     * @return String representation of this object
     */
    @Override
    public String toString() {
        return String.format(
                "%s[topHeight=%.1f, defaultSediment=%s, layers=%s]",
                getClass().getSimpleName(), topHeight, defaultSediment, layers);
    }
}
