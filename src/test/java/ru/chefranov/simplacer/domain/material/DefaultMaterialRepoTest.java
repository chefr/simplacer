package ru.chefranov.simplacer.domain.material;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests DefaultMaterialRepo.
 * @author Chefranov R.M.
 */
public class DefaultMaterialRepoTest {

    /**
     * Tests the getMinerals method.
     */
    @Test
    public void getMinerals() {
        MaterialRepo repo = new DefaultMaterialRepo(new Mineral[]{
                new DefaultMineral("Zircon", 4.6),
                new DefaultMineral("Quartz", 2.6)}, new Grain[]{});
        Assert.assertArrayEquals(new Mineral[]{
                new DefaultMineral("Zircon", 4.6),
                new DefaultMineral("Quartz", 2.6)}, repo.getMinerals());
    }

    /**
     * Tests the getGrains method.
     */
    @Test
    public void getGrains() {
        Mineral zircon = new DefaultMineral("Zircon", 4.6);
        Mineral quartz = new DefaultMineral("Quartz", 2.6);
        MaterialRepo repo = new DefaultMaterialRepo(null, new Grain[]{
                new DefaultGrain("Zir01", zircon, 0.01),
                new DefaultGrain("Zir02", zircon, 0.02),
                new DefaultGrain("Qz025", quartz, 0.025)});
        Assert.assertArrayEquals(new Grain[]{
                new DefaultGrain("Zir01", zircon, 0.01),
                new DefaultGrain("Zir02", zircon, 0.02),
                new DefaultGrain("Qz025", quartz, 0.025)}, repo.getGrains());
    }
}
