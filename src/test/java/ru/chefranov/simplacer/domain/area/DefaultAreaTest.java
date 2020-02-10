package ru.chefranov.simplacer.domain.area;

import org.junit.Assert;
import org.junit.Test;
import ru.chefranov.simplacer.domain.material.*;

import java.io.IOException;

/**
 * Tests DefaultArea.
 * @author Chefranov R.M.
 */
public class DefaultAreaTest {

    /**
     * Tests the getCutData method.
     */
    @Test
    public void testGetCutData() {
        Cut[][] cuts = new Cut[][]{{
            new DefaultCut(100.0),
                new DefaultCut(95.0),
                new DefaultCut(108.5)}, {
            new DefaultCut(85.0),
                new DefaultCut(90.5),
                new DefaultCut(94.0)}};
        Sediment sediment = null;
        SedimentBuilder builder = new DefaultSediment.Builder(3);
        try {
            builder.addGrainPercentage(0, 10).
                    addGrainPercentage(1, 70).
                    addGrainPercentage(2, 20);
            sediment = builder.getSediment();
        } catch(IOException ex) {
            Assert.fail();
        }
        cuts[0][0].setDefaultSediment(sediment);
        cuts[1][2].setDefaultSediment(sediment);
        cuts[0][0].setLayerOnTop(new DefaultLayer(sediment, 1.5));
        cuts[0][1].setLayerOnTop(new DefaultLayer(sediment, 1.0));
        builder.clearComposition();
        try {
            builder.addGrainPercentage(0, 25).
                    addGrainPercentage(1, 75);
            sediment = builder.getSediment();
        } catch(IOException ex) {
            Assert.fail();
        }
        cuts[1][0].setDefaultSediment(sediment);
        cuts[0][0].setLayerOnTop(new DefaultLayer(sediment, 0.5));
        cuts[0][2].setLayerOnTop(new DefaultLayer(sediment, 2.3));
        Area area = new DefaultArea(cuts, sediment, 500.0);
        CutData[][] expected = new CutData[][]{
                {new DefaultCutData(100.0, new LayerData[]{
                        new DefaultLayerData(1.5, 10.0),
                        new DefaultLayerData(0.5, 25.0)}, 10.0),
                        new DefaultCutData(95.0, new LayerData[]{
                                new DefaultLayerData(1.0, 10.0)}, 25.0),
                        new DefaultCutData(108.5, new LayerData[]{
                                new DefaultLayerData(2.3, 25.0)}, 25.0)},
                {new DefaultCutData(85.0, new LayerData[]{}, 25.0),
                        new DefaultCutData(90.5, new LayerData[]{}, 25.0),
                        new DefaultCutData(94.0, new LayerData[]{}, 10.0)}};
        CutData[][] actual = area.getCutData(0);
        Assert.assertArrayEquals(expected, actual);
    }

    /**
     * Tests the next method.
     */
    @Test
    public void next() {
        // TODO
    }
}
