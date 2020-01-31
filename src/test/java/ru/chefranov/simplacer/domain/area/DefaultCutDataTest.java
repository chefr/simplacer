package ru.chefranov.simplacer.domain.area;

import org.junit.Assert;
import org.junit.Test;
import ru.chefranov.simplacer.domain.material.DefaultLayerData;
import ru.chefranov.simplacer.domain.material.LayerData;

/**
 * Tests DefaultCutData.
 * @author Chefranov R.M.
 */
public class DefaultCutDataTest {

    /**
     * Tests the getTopHeight method.
     */
    @Test
    public void testGetTopHeight() {
        DefaultCutData data = new DefaultCutData(123.4, new DefaultLayerData[]{
                new DefaultLayerData(1.0, 3.5),
                new DefaultLayerData(0.5, 0.0)},
                12.0);
        Assert.assertEquals(123.4, data.getTopHeight(), 0.0);
    }

    /**
     * Tests the getLayerData method.
     */
    @Test
    public void testGetLayerData() {
        LayerData[] layers = new DefaultLayerData[]{
                new DefaultLayerData(1.0, 3.5),
                new DefaultLayerData(0.5, 0.0)};
        DefaultCutData data = new DefaultCutData(123.4, layers, 12.0);
        Assert.assertArrayEquals(layers, data.getLayerData());
    }

    /**
     * Tests the getUnderlying method.
     */
    @Test
    public void testGetUnderlying() {
        DefaultCutData data = new DefaultCutData(123.4, new DefaultLayerData[]{
                new DefaultLayerData(1.0, 3.5),
                new DefaultLayerData(0.5, 0.0)},
                12.0);
        Assert.assertEquals(12.0, data.getUnderlying(), 0.0);
    }

    /**
     * Tests the equals method.
     */
    @Test
    public void testEquals() {
        LayerData[] layers = new DefaultLayerData[]{
                new DefaultLayerData(1.0, 3.5),
                new DefaultLayerData(0.5, 0.0)};
        DefaultCutData data = new DefaultCutData(123.4, layers, 12.0);
        Assert.assertEquals(new DefaultCutData(123.4, layers, 12.0), data);
        Assert.assertNotEquals(new DefaultCutData(122.4, layers, 12.0), data);
        Assert.assertEquals(new DefaultCutData(123.4, new DefaultLayerData[]{
                new DefaultLayerData(1.0, 3.5),
                new DefaultLayerData(0.5, 0.0)}, 12.0), data);
        Assert.assertNotEquals(new DefaultCutData(123.4, new DefaultLayerData[]{
                new DefaultLayerData(1.5, 3.5),
                new DefaultLayerData(0.5, 0.0)}, 12.0), data);
        Assert.assertNotEquals(new DefaultCutData(123.4, layers, 11.0), data);
    }
}
