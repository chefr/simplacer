package ru.chefranov.simplacer.domain.material;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

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
        SedimentBuilder builder = new DefaultSediment.Builder(4);
        try {
            builder.addGrainPercentage(0, 10).
                    addGrainPercentage(2, 70).
                    addGrainPercentage(3, 20);
            Sediment sediment = builder.getSediment();
            Assert.assertEquals(10.0, sediment.getGrainPercentage(0), 0.0);
            Assert.assertEquals(70.0, sediment.getGrainPercentage(2), 0.0);
            Assert.assertEquals(20.0, sediment.getGrainPercentage(3), 0.0);
            Assert.assertEquals(0.0, sediment.getGrainPercentage(1), 0.0);
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
            builder.addGrainPercentage(0, 10).
                    addGrainPercentage(1, 70).
                    addGrainPercentage(2, 20);
            Sediment sediment = builder.getSediment();
            double[] composition = new double[]{10.0, 70.0, 20.0};
            double[] sedComposition = sediment.getComposition();
            Assert.assertArrayEquals(composition, sedComposition, 0.0);
            sedComposition[0] = 15.0;  // sediment is independent
            Assert.assertFalse(Arrays.equals(sedComposition,
                    sediment.getComposition()));
            Assert.assertEquals(10.0, sediment.getGrainPercentage(0), 0.0);
        } catch(IOException ex) {
            Assert.fail();
        }
    }
}
