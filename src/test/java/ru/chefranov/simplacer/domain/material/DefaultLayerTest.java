package ru.chefranov.simplacer.domain.material;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

/**
 * Tests DefaultLayer
 * @author Chefranov R.M.
 */
public class DefaultLayerTest {

    /**
     * Tests the getThickness method.
     */
    @Test
    public void testGetThickness() {
        SedimentBuilder builder = new DefaultSediment.Builder(2);
        try {
            builder.addGrainPercentage(0, 25.0).
                    addGrainPercentage(1, 75.0);
            Layer layer = new DefaultLayer(builder.getSediment(), 2.0);
            Assert.assertEquals(2.0, layer.getThickness(), 0.0);
        } catch(IOException ex) {
            Assert.fail();
        }
    }

    /**
     * Tests the getGrainPercentage method.
     */
    @Test
    public void testGetGrainPercentage() {
        SedimentBuilder builder = new DefaultSediment.Builder(3);
        try {
            builder.addGrainPercentage(0, 25.0).
                    addGrainPercentage(2, 75.0);
            Layer layer = new DefaultLayer(builder.getSediment(), 2.0);
            Assert.assertEquals(25.0, layer.getGrainPercentage(0), 0.0);
            Assert.assertEquals(75.0, layer.getGrainPercentage(2), 0.0);
            Assert.assertEquals(0.0, layer.getGrainPercentage(1), 0.0);
        } catch(IOException ex) {
            Assert.fail();
        }
    }

    /**
     * Tests the getComposition method.
     */
    @Test
    public void testGetComposition() {
        SedimentBuilder builder = new DefaultSediment.Builder(3);
        try {
            builder.addGrainPercentage(0, 25.0).
                    addGrainPercentage(2, 75.0);
            Assert.assertArrayEquals(new double[]{25.0, 0.0, 75.0},
                    new DefaultLayer(builder.getSediment(), 2.0).
                            getComposition(), 0.0);
        } catch(IOException ex) {
            Assert.fail();
        }
    }

    /**
     * Tests the getCompositionInThickness method.
     */
    @Test
    public void testGetCompositionInThickness() {
        SedimentBuilder builder = new DefaultSediment.Builder(3);
        try {
            builder.addGrainPercentage(0, 25.0).
                    addGrainPercentage(2, 75.0);
            Assert.assertArrayEquals(new double[]{0.5, 0.0, 1.5},
                    new DefaultLayer(builder.getSediment(), 2.0).
                            getCompositionInThickness(), 0.0);
        } catch(IOException ex) {
            Assert.fail();
        }
    }

    /**
     * Tests the add method.
     */
    @Test
    public void testAdd() {
        SedimentBuilder builder = new DefaultSediment.Builder(3);
        try {
            builder.addGrainPercentage(0, 25.0).
                    addGrainPercentage(2, 75.0);
            Layer layer = new DefaultLayer(builder.getSediment(), 2.0);
            builder.clearComposition();
            builder.addGrainPercentage(0, 50.0).
                    addGrainPercentage(1, 50.0);
            Layer otherLayer = new DefaultLayer(builder.getSediment(), 1.0);
            Layer summaryLayer = layer.add(otherLayer);
            Assert.assertSame(summaryLayer, layer);
            Assert.assertArrayEquals(new double[]{1.0, 0.5, 1.5},
                    layer.getCompositionInThickness(), 0.0);
            Assert.assertEquals(3.0, layer.getThickness(), 0.0);
        } catch(IOException ex) {
            Assert.fail();
        }
    }

    /**
     * Tests the accumulate method.
     */
    @Test
    public void testAccumulate() {
        SedimentBuilder builder = new DefaultSediment.Builder(3);
        try {
            builder.addGrainPercentage(0, 25.0).
                    addGrainPercentage(1, 75.0);
            Layer layer = new DefaultLayer(builder.getSediment(), 2.0);
            Assert.assertEquals(2.0, layer.getThickness(), 0.0);
            layer.accumulate(0, 0.2);
            Assert.assertEquals(2.2, layer.getThickness(), 0.0);
            Assert.assertEquals(31.81, layer.getGrainPercentage(0), 0.01);
            Assert.assertEquals(68.18, layer.getGrainPercentage(1), 0.01);
            Assert.assertEquals(0.0, layer.getGrainPercentage(2), 0.01);
            layer.accumulate(2, 0.3);
            Assert.assertEquals(2.5, layer.getThickness(), 0.0);
            Assert.assertEquals(28.0, layer.getGrainPercentage(0), 0.01);
            Assert.assertEquals(60.0, layer.getGrainPercentage(1), 0.01);
            Assert.assertEquals(12.0, layer.getGrainPercentage(2), 0.00);
        } catch(IOException ex) {
            Assert.fail();
        }
    }

    /**
     * Tests the erode(double) method.
     */
    @Test
    public void testErode() {
        SedimentBuilder builder = new DefaultSediment.Builder(3);
        try {
            builder.addGrainPercentage(0, 25.0).
                    addGrainPercentage(1, 75.0);
            Layer layer = new DefaultLayer(builder.getSediment(), 2.0);
            Layer eroded = layer.erode(0.1);
            Assert.assertEquals(1.8, layer.getThickness(), 0.001);
            Assert.assertEquals(0.2, eroded.getThickness(), 0.001);
            Assert.assertArrayEquals(new double[]{0.45, 1.35, 0.0},
                    layer.getCompositionInThickness(), 0.001);
            Assert.assertArrayEquals(new double[]{0.05, 0.15, 0.0},
                    eroded.getCompositionInThickness(), 0.001);
            Assert.assertArrayEquals(new double[]{25.0, 75.0, 0.0},
                    layer.getComposition(), 0.001);
            Assert.assertArrayEquals(new double[]{25.0, 75.0, 0.0},
                    eroded.getComposition(), 0.001);
        } catch(IOException ex) {
            Assert.fail();
        }
    }

    /**
     * Tests the erode(int, double) method.
     */
    @Test
    public void testErodeGrain() {
        SedimentBuilder builder = new DefaultSediment.Builder(3);
        try {
            builder.addGrainPercentage(0, 25.0).
                    addGrainPercentage(1, 75.0);
            Layer layer = new DefaultLayer(builder.getSediment(), 2.0);
            Assert.assertEquals(2.0, layer.getThickness(), 0.0);
            double eroded = layer.erode(0, 0.2);
            Assert.assertEquals(0.1, eroded, 0.0);
            Assert.assertEquals(1.9, layer.getThickness(), 0.0);
            Assert.assertEquals(21.05, layer.getGrainPercentage(0), 0.01);
            Assert.assertEquals(78.94, layer.getGrainPercentage(1), 0.01);
            eroded = layer.erode(2, 0.2);
            Assert.assertEquals(0.0, eroded, 0.0);
            Assert.assertEquals(1.9, layer.getThickness(), 0.0);
            Assert.assertEquals(21.05, layer.getGrainPercentage(0), 0.01);
            Assert.assertEquals(78.94, layer.getGrainPercentage(1), 0.01);
            Assert.assertEquals(0.0, layer.getGrainPercentage(2), 0.0);
            eroded = layer.erode(1, 1.0);
            Assert.assertEquals(1.5, eroded, 0.0);
            Assert.assertEquals(0.4, layer.getThickness(), 0.01);
            Assert.assertEquals(100.0, layer.getGrainPercentage(0), 0.01);
            Assert.assertEquals(0.0, layer.getGrainPercentage(1), 0.0);
        } catch(IOException ex) {
            Assert.fail();
        }
    }
}
