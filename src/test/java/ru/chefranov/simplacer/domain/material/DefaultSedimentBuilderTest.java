package ru.chefranov.simplacer.domain.material;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

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
        SedimentBuilder builder = new DefaultSediment.Builder(3);
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
        SedimentBuilder builder = new DefaultSediment.Builder(4);
        try {
            Assert.assertNull(builder.getSediment());
        } catch(IOException ex) {
            Assert.fail();
        }
        try {
            builder.addGrainPercentage(0, 15.0).
                    addGrainPercentage(1, 10.0).
                    addGrainPercentage(2, 25.0).
                    addGrainPercentage(3, 50.0);
            double[] composition = new double[]{15.0, 10.0, 25.0, 50.0};
            Assert.assertArrayEquals(composition, builder.getSediment().
                    getComposition(), 0.0);
        } catch(IOException ex) {
            Assert.fail();
        }

        builder.clearComposition();
        try {
            builder.addGrainPercentage(0, 15.0).
                    addGrainPercentage(2, 25.0);
            double[] composition = new double[]{37.5, 0.0, 62.5, 0.0};
            Assert.assertArrayEquals(composition, builder.getSediment().
                    getComposition(), 0.0);
            builder.setAdmissibleDeviation(60.0);
            Assert.assertArrayEquals(composition, builder.getSediment().
                    getComposition(), 0.0);
            try {
                builder.setAdmissibleDeviation(59.9);
                builder.getSediment();
                Assert.fail();
            } catch(IOException ex) {
                Assert.assertEquals("ERROR_SEDIMENT_BUILDER_COMPOSITION_" +
                        "INCORRECT", ex.getMessage());
            }
            builder.setAdmissibleDeviation(-10.0);
            Assert.assertArrayEquals(composition, builder.getSediment().
                    getComposition(), 0.0);
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
            builder.addGrainPercentage(0, 25.0).
                    addGrainPercentage(2, 65.0).
                    addGrainPercentage(3, 35.0);
            double[] composition = new double[]{20.0, 0.0, 52.0, 28.0};
            Assert.assertArrayEquals(composition, builder.getSediment().
                    getComposition(), 0.0);
            builder.setAdmissibleDeviation(30.0);
            Assert.assertArrayEquals(composition, builder.getSediment().
                    getComposition(), 0.0);
            builder.setAdmissibleDeviation(25.0);
            Assert.assertArrayEquals(composition, builder.getSediment().
                    getComposition(), 0.0);
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
            builder.addGrainPercentage(0, 15.0).
                    addGrainPercentage(0, 25.0);
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
        SedimentBuilder builder = new DefaultSediment.Builder(2);
        try {
            builder.addGrainPercentage(0, 25.0).
                    addGrainPercentage(1, 75.0);
            builder.clearComposition();
            Assert.assertNull(builder.getSediment());
        } catch(IOException ex) {
            Assert.fail();
        }
    }
}
