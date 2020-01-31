package ru.chefranov.simplacer.domain.material;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests DefaultLayerDataTest.
 * @author Chefranov R.M.
 */
public class DefaultLayerDataTest {

    /**
     * Tests the getThickness method.
     */
    @Test
    public void testGetThickness() {
        LayerData data = new DefaultLayerData(1.0, 3.5);
        Assert.assertEquals(1.0, data.getThickness(), 0.0);
    }

    /**
     * Tests the getPercentage method.
     */
    @Test
    public void testGetPercentage() {
        LayerData data = new DefaultLayerData(1.0, 3.5);
        Assert.assertEquals(3.5, data.getPercentage(), 0.0);
    }

    /**
     * Tests the equals method.
     */
    @Test
    public void testEquals() {
        DefaultLayerData data = new DefaultLayerData(1.5, 25.0);
        Assert.assertEquals(data, new DefaultLayerData(1.5, 25.0));
        Assert.assertNotEquals(data, new DefaultLayerData(1.5, 24.0));
        Assert.assertNotEquals(data, new DefaultLayerData(1.4, 25.0));
    }
}
