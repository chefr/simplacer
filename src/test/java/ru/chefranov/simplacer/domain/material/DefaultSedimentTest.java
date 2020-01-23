package ru.chefranov.simplacer.domain.material;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Tests DefaultSediment class.
 * @author Chefranov R.M.
 */
public class DefaultSedimentTest {

    /**
     * Tests the getGrainPercentage method.
     */
    @Test
    public void testGetGrainPercentage() {
        Mineral zircon = new DefaultMineral("Zircon", 4.6);
        Mineral quartz = new DefaultMineral("Quartz", 2.6);
        Grain zir02 = new DefaultGrain("Zir02", zircon, 0.02);
        Grain zir01 = new DefaultGrain("Zir01", zircon, 0.01);
        Grain qz025 = new DefaultGrain("Qz025", quartz, 0.025);
        Grain qz01 = new DefaultGrain("Qz01", quartz, 0.025);
        SedimentBuilder builder = new DefaultSediment.Builder();
        try {
            builder.addGrainPercentage(zir01, 10).
                    addGrainPercentage(qz025, 70).
                    addGrainPercentage(qz01, 20);
            Sediment sediment = builder.getSediment();
            Assert.assertEquals(10.0, sediment.getGrainPercentage(zir01), 0.0);
            Assert.assertEquals(70.0, sediment.getGrainPercentage(qz025), 0.0);
            Assert.assertEquals(20.0, sediment.getGrainPercentage(qz01), 0.0);
            Assert.assertEquals(0.0, sediment.getGrainPercentage(zir02), 0.0);
        } catch(IOException ex) {
            Assert.fail();
        }
    }

    /**
     * Tests the getComposition method.
     */
    @Test
    public void testGetComposition() {
        Mineral zircon = new DefaultMineral("Zircon", 4.6);
        Mineral quartz = new DefaultMineral("Quartz", 2.6);
        Grain zir01 = new DefaultGrain("Zir01", zircon, 0.01);
        Grain qz025 = new DefaultGrain("Qz025", quartz, 0.025);
        Grain qz01 = new DefaultGrain("Qz01", quartz, 0.01);
        SedimentBuilder builder = new DefaultSediment.Builder();
        try {
            builder.addGrainPercentage(zir01, 10).
                    addGrainPercentage(qz025, 70).
                    addGrainPercentage(qz01, 20);
            Sediment sediment = builder.getSediment();
            Map<Grain, Double> composition = new HashMap<>();
            composition.put(zir01, 10.0);
            composition.put(qz025, 70.0);
            composition.put(qz01, 20.0);
            Map<Grain, Double> sedComposition = sediment.getComposition();
            Assert.assertEquals(composition, sedComposition);
            sedComposition.put(zir01, 15.0);  // sediment is independent
            Assert.assertNotEquals(sedComposition, sediment.getComposition());
            Assert.assertEquals(10.0, sediment.getGrainPercentage(zir01), 0.0);
        } catch(IOException ex) {
            Assert.fail();
        }
    }
}
