package ru.chefranov.simplacer.domain.area;

import ru.chefranov.simplacer.domain.material.*;

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
     * Erodes the Layer with specified thickness
     * @param thickness Thickness of the eroded layer
     * @param areaDefaultSediment Area default sediment
     * @param numberOfGrains Number of tte grains in repo.
     * @return Layer
     */
    @Override
    public Layer erodeLayer(double thickness, Sediment areaDefaultSediment,
                            int numberOfGrains) {
        Layer layer = new DefaultLayer(numberOfGrains);
        topHeight -= thickness;
        double remainingThickness = thickness;
        while(true) {
            if(layers.isEmpty()) {
                return layer.add(
                        new DefaultLayer((defaultSediment == null)?
                        areaDefaultSediment : defaultSediment,
                                remainingThickness));
            }
            double topLayerThickness =
                    layers.get(layers.size() - 1).getThickness();
            if(remainingThickness >= topLayerThickness) {
                layer.add(layers.remove(layers.size() - 1));
                if(remainingThickness == topLayerThickness) {
                    return layer;
                }
                remainingThickness -= topLayerThickness;
            } else {
                Layer topLayer = layers.get(layers.size() - 1);
                double k = remainingThickness / topLayer.getThickness();
                return layer.add(topLayer.erode(k));
            }
        }
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
