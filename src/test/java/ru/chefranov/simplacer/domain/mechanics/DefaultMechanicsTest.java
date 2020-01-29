package ru.chefranov.simplacer.domain.mechanics;

import org.junit.Assert;
import org.junit.Test;
import ru.chefranov.simplacer.domain.material.*;

import java.io.IOException;

/**
 * Test DefaultMechanics.
 * @author Chefranov R.M.
 */
public class DefaultMechanicsTest {

    /**
     * Tests the calculateGrainInitialVelocity methods.
     */
    @Test
    public void testCalculateGrainInitialVelocity() {
        Mineral zircon = new DefaultMineral("Zircon", 4.6);
        Mineral quartz = new DefaultMineral("Quartz", 2.6);
        Grain[] grains = new Grain[]{
                new DefaultGrain("Zir01", zircon, 0.01),
                new DefaultGrain("Qz025", quartz, 0.025),
                new DefaultGrain("Qz01", quartz, 0.01)};
        MaterialRepo repo = new DefaultMaterialRepo(
                new Mineral[]{zircon, quartz}, grains);
        try {
            Mechanics mechanics = new DefaultMechanics(
                    new DefaultConstantHandler(), repo);
            Assert.assertEquals(16.945,
                    mechanics.calculateGrainInitialVelocity(grains[0], 0,
                            30.0,0.0), 0.01);
            Assert.assertEquals(12.1,
                    mechanics.calculateGrainInitialVelocity(grains[0], 0,
                            20.9,0.26), 0.01);
            Assert.assertEquals(3.705,
                    mechanics.calculateGrainInitialVelocity(grains[0], 0,
                            20.0,-0.26), 0.01);
            Assert.assertEquals(0.0,
                    mechanics.calculateGrainInitialVelocity(grains[0], 0,
                            15.0,-0.26), 0.01);
            Assert.assertEquals(19.832,
                    mechanics.calculateGrainInitialVelocity(grains[1], 1,
                            30.0,0.0), 0.01);
            Assert.assertEquals(20.171,
                    mechanics.calculateGrainInitialVelocity(grains[2], 2,
                            30.0,0.0), 0.01);
        } catch(IOException ex) {
            Assert.fail();
        }
    }
}
