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
     * Tests the setLayerOnTop method.
     */
    @Test
    public void testSetLayerOnTop() {
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
        cut.setLayerOnTop(layer);
        Assert.assertEquals(100.0, cut.getTopHeight(), 0.0);
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
        Cut cut = getCut();
        Assert.assertArrayEquals(new LayerData[]{
                new DefaultLayerData(2.0, 25.0),
                new DefaultLayerData(1.4, 0.0)
        }, cut.getLayerData(0));
    }

    /**
     * Tests the addTopLayer method.
     */
    @Test
    public void testAddTopLayer() {
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

    /**
     * Tests the erodeLayer method.
     */
    @Test
    public void erodeLayer() {
        Cut cut = getCut();
        SedimentBuilder builder = new DefaultSediment.Builder(2);
        Sediment areaDefaultSediment = null;
        try {
            builder.addGrainPercentage(0, 100.0);
            areaDefaultSediment = builder.getSediment();
        } catch(IOException ex) {
            Assert.fail();
        }
        Layer eroded = cut.erodeLayer(1.0, areaDefaultSediment, 2);
        Assert.assertEquals(1.0, eroded.getThickness(), 0.0);
        Assert.assertArrayEquals(new double[]{0.0, 100.0},
                eroded.getComposition(), 0.001);
        Assert.assertEquals(99.0, cut.getTopHeight(), 0.0);
        LayerData[] data = cut.getLayerData(0);
        Assert.assertEquals(data.length, 2);
        Assert.assertEquals(data[0].getThickness(), 2.0, 0.0);
        Assert.assertEquals(data[0].getPercentage(), 25.0, 0.0);
        Assert.assertEquals(data[1].getThickness(), 0.4, 0.001);
        Assert.assertEquals(data[1].getPercentage(), 0.0, 0.0);
        data = cut.getLayerData(1);
        Assert.assertEquals(data[0].getPercentage(), 75.0, 0.0);
        Assert.assertEquals(data[1].getPercentage(), 100.0, 0.0);

        cut = getCut();
        eroded = cut.erodeLayer(2.0, areaDefaultSediment, 2);
        Assert.assertEquals(2.0, eroded.getThickness(), 0.0);
        Assert.assertArrayEquals(new double[]{0.15, 1.85},
                eroded.getCompositionInThickness(), 0.001);
        Assert.assertEquals(98.0, cut.getTopHeight(), 0.0);
        data = cut.getLayerData(0);
        Assert.assertEquals(data.length, 1);
        Assert.assertEquals(data[0].getThickness(), 1.4, 0.0);
        Assert.assertEquals(data[0].getPercentage(), 25.0, 0.0);
        data = cut.getLayerData(1);
        Assert.assertEquals(data[0].getPercentage(), 75.0, 0.0);

        cut = getCut();
        eroded = cut.erodeLayer(1.4, areaDefaultSediment, 2);
        Assert.assertEquals(1.4, eroded.getThickness(), 0.0);
        Assert.assertArrayEquals(new double[]{0.0, 100.0},
                eroded.getComposition(), 0.001);
        Assert.assertEquals(98.6, cut.getTopHeight(), 0.0);
        data = cut.getLayerData(0);
        Assert.assertEquals(data.length, 1);
        Assert.assertEquals(data[0].getThickness(), 2.0, 0.0);
        Assert.assertEquals(data[0].getPercentage(), 25.0, 0.0);
        data = cut.getLayerData(1);
        Assert.assertEquals(data[0].getPercentage(), 75.0, 0.0);

        cut = getCut();
        cut.setDefaultSediment(null);
        eroded = cut.erodeLayer(3.4, areaDefaultSediment, 2);
        Assert.assertEquals(3.4, eroded.getThickness(), 0.0);
        Assert.assertArrayEquals(new double[]{0.5, 2.9},
                eroded.getCompositionInThickness(), 0.001);
        Assert.assertEquals(96.6, cut.getTopHeight(), 0.0);
        data = cut.getLayerData(0);
        Assert.assertEquals(data.length, 0);

        cut = getCut();
        eroded = cut.erodeLayer(4.0, areaDefaultSediment, 2);
        Assert.assertEquals(4.0, eroded.getThickness(), 0.0);
        Assert.assertArrayEquals(new double[]{0.5, 3.5},
                eroded.getCompositionInThickness(), 0.001);
        Assert.assertEquals(96.0, cut.getTopHeight(), 0.0);
        data = cut.getLayerData(0);
        Assert.assertEquals(data.length, 0);

        cut = getCut();
        cut.setDefaultSediment(null);
        eroded = cut.erodeLayer(4.0, areaDefaultSediment, 2);
        Assert.assertEquals(4.0, eroded.getThickness(), 0.0);
        Assert.assertArrayEquals(new double[]{1.1, 2.9},
                eroded.getCompositionInThickness(), 0.001);
        Assert.assertEquals(96.0, cut.getTopHeight(), 0.0);
        data = cut.getLayerData(0);
        Assert.assertEquals(data.length, 0);
    }

    private Cut getCut() {
        DefaultCut cut = new DefaultCut(100.0);
        Assert.assertArrayEquals(new LayerData[]{}, cut.getLayerData(0));
        SedimentBuilder builder = new DefaultSediment.Builder(2);
        Layer layer = null;
        try {
            builder.addGrainPercentage(0, 25.0).
                    addGrainPercentage(1, 75.0);
            layer = new DefaultLayer(builder.getSediment(), 2.0);
        } catch(IOException ex) {
            Assert.fail();
        }
        cut.setLayerOnTop(layer);
        builder.clearComposition();
        try {
            builder.addGrainPercentage(1, 100.0);
            layer = new DefaultLayer(builder.getSediment(), 1.4);
            cut.setDefaultSediment(builder.getSediment());
        } catch(IOException ex) {
            Assert.fail();
        }
        cut.setLayerOnTop(layer);
        return cut;
    }
}