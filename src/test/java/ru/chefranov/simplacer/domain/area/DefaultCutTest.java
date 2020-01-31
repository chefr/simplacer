package ru.chefranov.simplacer.domain.area;

import org.junit.Assert;
import org.junit.Test;
import ru.chefranov.simplacer.domain.material.*;

import java.io.IOException;

/**
 * Tests DefaultCut.
 * @author Chefranov R.M.
 */
public class DefaultCutTest {

    /**
     * Tests the setDefaultSediment and getDefaultSediment methods.
     */
    @Test
    public void testSetGetDefaultSediment() {
        DefaultCut cut = new DefaultCut(100.0);
        Assert.assertNull(cut.getDefaultSediment());
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
        cut.setDefaultSediment(sediment);
        Assert.assertEquals(sediment, cut.getDefaultSediment());
    }

    /**
     * Tests the getTopHeight method.
     */
    @Test
    public void testGetTopHeight() {
        DefaultCut cut = new DefaultCut(100.0);
        Assert.assertEquals(100.0, cut.getTopHeight(), 0.0);
    }

    /**
     * Tests the getLayerData method.
     */
    @Test
    public void getLayerData() {
        DefaultCut cut = new DefaultCut(100.0);
        Assert.assertArrayEquals(new LayerData[]{}, cut.getLayerData(0));
        SedimentBuilder builder = new DefaultSediment.Builder(2);
        Layer layer = null;
        try {
            builder.addGrainPercentage(0, 25.0).
                    addGrainPercentage(1, 75.0);
            layer = new DefaultLayer(builder.getSediment(), 2.0);
        }  catch(IOException ex) {
            Assert.fail();
        }
        cut.addTopLayer(layer);
        builder.clearComposition();
        try {
            builder.addGrainPercentage(1, 100.0);
            layer = new DefaultLayer(builder.getSediment(), 0.5);
        }  catch(IOException ex) {
            Assert.fail();
        }
        cut.addTopLayer(layer);
        Assert.assertArrayEquals(new LayerData[]{
                new DefaultLayerData(2.0, 25.0),
                new DefaultLayerData(0.5, 0.0)
                }, cut.getLayerData(0));
    }

    /**
     * Tests the addTopLayer method.
     */
    @Test
    public void addTopLayer() {
        DefaultCut cut = new DefaultCut(100.0);
        SedimentBuilder builder = new DefaultSediment.Builder(2);
        Layer layer = null;
        try {
            builder.addGrainPercentage(0, 25.0).
                    addGrainPercentage(1, 75.0);
            layer = new DefaultLayer(builder.getSediment(), 2.0);
        }  catch(IOException ex) {
            Assert.fail();
        }
        cut.addTopLayer(layer);
        Assert.assertEquals(102.0, cut.getTopHeight(), 0.0);
    }
}