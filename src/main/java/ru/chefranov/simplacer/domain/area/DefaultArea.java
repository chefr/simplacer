package ru.chefranov.simplacer.domain.area;

import ru.chefranov.simplacer.domain.material.Sediment;

import java.util.Arrays;

/**
 * Default Area.
 * @author Chefranov R.M.
 */
public class DefaultArea implements Area {

    private final Cut[][] cuts;

    private final Sediment defaultSediment;

    private final double distance;

    /**
     * @param cuts Two-dimensional array of the cuts
     * @param defaultSediment Default sediment
     * @param distance Distance between adjacent cuts, m, > 0.0
     */
    public DefaultArea(Cut[][] cuts, Sediment defaultSediment,
                       double distance) {
        this.cuts = cuts;
        this.defaultSediment = defaultSediment;
        this.distance = distance;
    }

    /**
     * Returns the Cut Data.
     * @param index Index of the Grain in the Grain Repository
     * @return Cut Data
     */
    @Override
    public CutData[][] getCutData(int index) {
        CutData[][] data = new CutData[cuts.length][];
        double defSediment = defaultSediment.getGrainPercentage(index);
        for(int i = 0; i < cuts.length; ++i) {
            data[i] = Arrays.stream(cuts[i]).map(cut -> {
                Sediment sediment = cut.getDefaultSediment();
                return new DefaultCutData(cut.getTopHeight(),
                        cut.getLayerData(index),
                        (sediment == null)? defSediment :
                                sediment.getGrainPercentage(index));
            }).toArray(CutData[]::new);
        }
        return data;
    }

    /**
     * Calculates the next state of the field.
     */
    @Override
    public void next() {
        // TODO
    }

    /**
     * Returns a string representation of this object.
     * @return String representation of this object
     */
    @Override
    public String toString() {
        return String.format(
                "%s[distance=%.1f, defaultSediment=%s, cuts=%s]",
                getClass().getSimpleName(), distance, defaultSediment,
                Arrays.deepToString(cuts));
    }
}
