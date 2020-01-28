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
     * Tests the erode method.
     */
    @Test
    public void testErode() {
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
