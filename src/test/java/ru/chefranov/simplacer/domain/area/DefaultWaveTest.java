package ru.chefranov.simplacer.domain.area;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests DefaultWave.
 * @author Chefranov R.M.
 */
public class DefaultWaveTest {

    /**
     * Tests the getDirection method.
     */
    @Test
    public void testGetDirection() {
        Wave wave = new DefaultWave(100.0, 30.0, 40.0);
        Assert.assertEquals(Math.toRadians(30.0), wave.getDirection(), 0.0);
        wave = new DefaultWave(100.0, 58.5, 40.0);
        Assert.assertEquals(Math.toRadians(58.5), wave.getDirection(), 0.0);
    }

    /**
     * Tests the getVelocityByHeight method.
     */
    @Test
    public void testGetVelocityByHeight() {
        Wave wave = new DefaultWave(100.0, 30.0, -40.0);
        Assert.assertEquals(0.0, wave.getVelocityByHeight(10.0), 0.0);
        Assert.assertEquals(0.0, wave.getVelocityByHeight(-41.0), 0.0);
        Assert.assertEquals(100.0, wave.getVelocityByHeight(0.0), 0.0);
        Assert.assertEquals(50.0, wave.getVelocityByHeight(-20.0), 0.0);
        Assert.assertEquals(75.0, wave.getVelocityByHeight(-10.0), 0.0);
        Assert.assertEquals(12.5, wave.getVelocityByHeight(-35.0), 0.0);
    }
}
