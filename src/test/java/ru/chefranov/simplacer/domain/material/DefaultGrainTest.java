package ru.chefranov.simplacer.domain.material;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests DefaultGrain class.
 * @author Chefranov R.M.
 */
public class DefaultGrainTest {

    /**
     * Tests the getIdentifier method.
     */
    @Test
    public void testGetIdentifier() {
        Mineral zircon = new DefaultMineral("Zircon", 4.6);
        Grain zir01 = new DefaultGrain("Zir01", zircon, 0.01);
        Assert.assertEquals("Zir01", zir01.getIdentifier());
        Mineral quartz = new DefaultMineral("Quartz", 2.6);
        Grain qz025 = new DefaultGrain("Qz025", quartz, 0.025);
        Assert.assertEquals("Qz025", qz025.getIdentifier());
    }

    /**
     * Tests the getMineral method.
     */
    @Test
    public void testGetMineral() {
        Mineral zircon = new DefaultMineral("Zircon", 4.6);
        Grain zir01 = new DefaultGrain("Zir01", zircon, 0.01);
        Assert.assertEquals(zircon, zir01.getMineral());
        Assert.assertSame(zircon, zir01.getMineral());
        Mineral quartz = new DefaultMineral("Quartz", 2.6);
        Grain qz025 = new DefaultGrain("Qz025", quartz, 0.025);
        Assert.assertEquals(quartz, qz025.getMineral());
        Assert.assertSame(quartz, qz025.getMineral());
    }

    /**
     * Tests the getDiameter method.
     */
    @Test
    public void testGetDiameter() {
        Mineral zircon = new DefaultMineral("Zircon", 4.6);
        Grain zir01 = new DefaultGrain("Zir01", zircon, 0.01);
        Assert.assertEquals(0.01, zir01.getDiameter(), 0.0);
        Mineral quartz = new DefaultMineral("Quartz", 2.6);
        Grain qz025 = new DefaultGrain("Qz025", quartz, 0.025);
        Assert.assertEquals(0.025, qz025.getDiameter(), 0.0);
    }

    /**
     * Tests the equals method.
     */
    @Test
    public void testEquals() {
        Mineral zircon = new DefaultMineral("Zircon", 4.6);
        Grain zir01 = new DefaultGrain("Zir01", zircon, 0.01);
        Grain zir02 = new DefaultGrain("Zir02", zircon, 0.02);
        Assert.assertNotEquals(zir01, null);
        Assert.assertEquals(zir01, zir01);
        Assert.assertNotEquals(zir01, zir02);
        Mineral quartz = new DefaultMineral("Quartz", 2.6);
        Grain qz025 = new DefaultGrain("Qz025", quartz, 0.025);
        Assert.assertNotEquals(zir01, qz025);
        Grain zir01Duplicate =  new DefaultGrain("Zir01", zircon, 0.01);
        Assert.assertEquals(zir01, zir01Duplicate);
        Grain fakeZir01Duplicate = new DefaultGrain("Zir01", zircon, 0.02);
        Assert.assertEquals(zir01, fakeZir01Duplicate);
    }

    /**
     * Tests the hashCode method.
     */
    @Test
    public void testHashCode() {
        Mineral zircon = new DefaultMineral("Zircon", 4.6);
        Grain zir01 = new DefaultGrain("Zir01", zircon, 0.01);
        Assert.assertEquals("Zir01".hashCode(), zir01.hashCode(), 0.0);
        Mineral quartz = new DefaultMineral("Quartz", 2.6);
        Grain qz025 = new DefaultGrain("Qz025", quartz, 0.025);
        Assert.assertEquals("Qz025".hashCode(), qz025.hashCode(), 0.0);
    }

    /**
     * Test the toString method.
     */
    @Test
    public void testToString() {
        Mineral zircon = new DefaultMineral("Zircon", 4.6);
        Grain zir01 = new DefaultGrain("Zir01", zircon, 0.01);
        Assert.assertEquals(String.format("DefaultGrain[identifier=Zir01, " +
                        "mineral=%s, diameter=%.3f]", zircon, 0.01),
                zir01.toString());
        Mineral quartz = new DefaultMineral("Quartz", 2.6);
        Grain qz025 = new DefaultGrain("Qz025", quartz, 0.025);
        Assert.assertEquals(String.format("DefaultGrain[identifier=Qz025, " +
                        "mineral=%s, diameter=%.3f]", quartz, 0.025),
                qz025.toString());
    }
}
