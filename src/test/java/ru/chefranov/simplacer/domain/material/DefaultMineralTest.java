package ru.chefranov.simplacer.domain.material;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests DefaultMineral class.
 * @author Chefranov R.M.
 */
public class DefaultMineralTest {

    /**
     * Tests the getName method.
     */
    @Test
    public void testGetName() {
        Mineral zircon = new DefaultMineral("Zircon", 4.6);
        Assert.assertEquals("Zircon", zircon.getName());
        Mineral quartz = new DefaultMineral("Quartz", 2.6);
        Assert.assertEquals("Quartz", quartz.getName());
    }

    /**
     * Tests the getDensity method.
     */
    @Test
    public void testGetDensity() {
        Mineral zircon = new DefaultMineral("Zircon", 4.6);
        Assert.assertEquals(4.6, zircon.getDensity(), 0.0);
        Mineral quartz = new DefaultMineral("Quartz", 2.6);
        Assert.assertEquals(2.6, quartz.getDensity(), 0.0);
    }

    /**
     * Tests the equals method.
     */
    @Test
    public void testEquals() {
        Mineral zircon = new DefaultMineral("Zircon", 4.6);
        Assert.assertNotEquals(zircon, null);
        Assert.assertEquals(zircon, zircon);
        Mineral quartz = new DefaultMineral("Quartz", 2.6);
        Assert.assertNotEquals(zircon, quartz);
        Mineral zirconDuplicate =  new DefaultMineral("Zircon", 4.6);
        Assert.assertEquals(zircon, zirconDuplicate);
        Mineral fakeZirconDuplicate =  new DefaultMineral("Zircon", 2.6);
        Assert.assertEquals(zircon, fakeZirconDuplicate);
    }

    /**
     * Tests the hashCode method.
     */
    @Test
    public void testHashCode() {
        Mineral zircon = new DefaultMineral("Zircon", 4.6);
        Assert.assertEquals("Zircon".hashCode(), zircon.hashCode(), 0.0);
        Mineral quartz = new DefaultMineral("Quartz", 2.6);
        Assert.assertEquals("Quartz".hashCode(), quartz.hashCode(), 0.0);
    }

    /**
     * Tests the toString method.
     */
    @Test
    public void testToString() {
        Mineral zircon = new DefaultMineral("Zircon", 4.6);
        Assert.assertEquals(String.format("DefaultMineral[name=Zircon, " +
                "density=%.2f]", 4.6), zircon.toString());
        Mineral quartz = new DefaultMineral("Quartz", 2.6);
        Assert.assertEquals(String.format("DefaultMineral[name=Quartz, " +
                "density=%.2f]", 2.6), quartz.toString());
    }
}
