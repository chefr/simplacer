package ru.chefranov.simplacer.domain.material;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Tests DefaultSedimentBuilder class.
 * @author Chefranov R.M.
 */
public class DefaultSedimentBuilderTest {

    /**
     * Tests the setAdmissibleDeviation and getAdmissibleDeviation methods.
     */
    @Test
    public void testSetGetAdmissibleDeviation() {
        SedimentBuilder builder = new DefaultSediment.Builder();
        Assert.assertEquals(-1.0, builder.getAdmissibleDeviation(), 0.0);
        double prevDev = builder.setAdmissibleDeviation(10.0);
        Assert.assertEquals(-1.0, prevDev, 0.0);
        Assert.assertEquals(10.0, builder.getAdmissibleDeviation(), 0.0);
        prevDev = builder.setAdmissibleDeviation(0.0);
        Assert.assertEquals(10.0, prevDev, 0.0);
        Assert.assertEquals(0.0, builder.getAdmissibleDeviation(), 0.0);
    }

    /**
     * Tests the addGrainPercentage and getSediment methods.
     */
    @Test
    public void testAddGrainPercentageGetSediment() {
        Mineral zircon = new DefaultMineral("Zircon", 4.6);
        Mineral quartz = new DefaultMineral("Quartz", 2.6);
        Grain zir02 = new DefaultGrain("Zir02", zircon, 0.02);
        Grain zir01 = new DefaultGrain("Zir01", zircon, 0.01);
        Grain qz025 = new DefaultGrain("Qz025", quartz, 0.025);
        Grain qz01 = new DefaultGrain("Qz01", quartz, 0.01);
        SedimentBuilder builder = new DefaultSediment.Builder();
        try {
            Assert.assertNull(builder.getSediment());
        } catch(IOException ex) {
            Assert.fail();
        }
        try {
            builder.addGrainPercentage(zir02, 15.0).
                    addGrainPercentage(zir01, 10.0).
                    addGrainPercentage(qz025, 25.0).
                    addGrainPercentage(qz01, 50.0);
            Map<Grain, Double> composition = new HashMap<>();
            composition.put(zir02, 15.0);
            composition.put(zir01, 10.0);
            composition.put(qz025, 25.0);
            composition.put(qz01, 50.0);
            Assert.assertEquals(composition, builder.getSediment().
                    getComposition());
        } catch(IOException ex) {
            Assert.fail();
        }

        builder.clearComposition();
        try {
            builder.addGrainPercentage(zir02, 15.0).
                    addGrainPercentage(qz025, 25.0);
            Map<Grain, Double> composition = new HashMap<>();
            composition.put(zir02, 37.5);
            composition.put(qz025, 62.5);
            Assert.assertEquals(composition, builder.getSediment().
                    getComposition());
            builder.setAdmissibleDeviation(60.0);
            Assert.assertEquals(composition, builder.getSediment().
                    getComposition());
            try {
                builder.setAdmissibleDeviation(59.9);
                builder.getSediment();
                Assert.fail();
            } catch(IOException ex) {
                Assert.assertEquals("ERROR_SEDIMENT_BUILDER_COMPOSITION_" +
                        "INCORRECT", ex.getMessage());
            }
            builder.setAdmissibleDeviation(-10.0);
            Assert.assertEquals(composition, builder.getSediment().
                    getComposition());
            try {
                builder.setAdmissibleDeviation(0.0);
                builder.getSediment();
                Assert.fail();
            } catch(IOException ex) {
                Assert.assertEquals("ERROR_SEDIMENT_BUILDER_COMPOSITION_" +
                        "INCORRECT", ex.getMessage());
            }
        } catch(IOException ex) {
            Assert.fail();
        }

        builder.clearComposition();
        builder.setAdmissibleDeviation(-0.1);
        try {
            builder.addGrainPercentage(zir02, 25.0).
                    addGrainPercentage(qz025, 65.0).
                    addGrainPercentage(qz01, 35.0);
            Map<Grain, Double> composition = new HashMap<>();
            composition.put(zir02, 20.0);
            composition.put(qz025, 52.0);
            composition.put(qz01, 28.0);
            Assert.assertEquals(composition, builder.getSediment().
                    getComposition());
            builder.setAdmissibleDeviation(30.0);
            Assert.assertEquals(composition, builder.getSediment().
                    getComposition());
            builder.setAdmissibleDeviation(25.0);
            Assert.assertEquals(composition, builder.getSediment().
                    getComposition());
            try {
                builder.setAdmissibleDeviation(24.9);
                builder.getSediment();
                Assert.fail();
            } catch (IOException ex) {
                Assert.assertEquals("ERROR_SEDIMENT_BUILDER_COMPOSITION_" +
                        "INCORRECT", ex.getMessage());
            }
        } catch(IOException ex) {
            Assert.fail();
        }

        builder.clearComposition();
        try {
            builder.addGrainPercentage(zir02, 15.0).
                    addGrainPercentage(zir02, 25.0);
            Assert.fail();
        } catch(IOException ex) {
            Assert.assertEquals("ERROR_SEDIMENT_BUILDER_GRAIN_PERCENTAGE_" +
                            "COLLISION",
                    ex.getMessage());
        }
    }

    /**
     * Tests the clearComposition method.
     */
    @Test
    public void testClearComposition() {
        Mineral zircon = new DefaultMineral("Zircon", 4.6);
        Grain zir02 = new DefaultGrain("Zir02", zircon, 0.02);
        Grain zir01 = new DefaultGrain("Zir01", zircon, 0.01);
        SedimentBuilder builder = new DefaultSediment.Builder();
        try {
            builder.addGrainPercentage(zir02, 25.0).
                    addGrainPercentage(zir01, 75.0);
            builder.clearComposition();
            Assert.assertNull(builder.getSediment());
        } catch(IOException ex) {
            Assert.fail();
        }
    }
}
