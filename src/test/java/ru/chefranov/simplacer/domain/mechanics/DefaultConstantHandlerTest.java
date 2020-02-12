package ru.chefranov.simplacer.domain.mechanics;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

/**
 * Tests DefaultConstantHandler.
 * @author Chefranov R.M.
 */
public class DefaultConstantHandlerTest {

    /**
     * Tests the constructor.
     */
    @Test
    public void testInit() {
        try {
            DefaultConstantHandler constants = new DefaultConstantHandler();
            Assert.assertEquals(constants.getConstant("GRAVITY"),980.665,
                    0.001);
        } catch(RuntimeException ex) {
            Assert.fail();
        }
    }

    /**
     * Tests the setConstant and getConstant methods.
     */
    @Test
    public void testSetGetConstant() {
        try {
            DefaultConstantHandler constants = new DefaultConstantHandler();
            Assert.assertEquals(constants.getConstant("WATER_DENSITY"), 1.0,
                    0.001);
            constants.setConstant("WATER_DENSITY", 1.2);
            Assert.assertEquals(constants.getConstant("WATER_DENSITY"), 1.2,
                    0.001);
            constants.setConstant("WATER_DENSITY", 0.8);
            Assert.assertEquals(constants.getConstant("WATER_DENSITY"), 0.8,
                    0.001);
            try {
                constants.setConstant("UNKNOWN_CONSTANT", 1.0);
            } catch(IOException ex) {
                Assert.assertEquals("ERROR_CONSTANT_HANDLER_CONSTANT_UNKNOWN",
                        ex.getMessage());
            }
        } catch(IOException ex) {
            Assert.fail();
        }
    }

    /**
     * Tests the toDefault method.
     */
    @Test
    public void testToDefault() {
        try {
            DefaultConstantHandler constants = new DefaultConstantHandler();
            Assert.assertEquals(constants.getConstant("WATER_DENSITY"), 1.0,
                    0.001);
            constants.setConstant("WATER_DENSITY", 1.2);
            Assert.assertEquals(constants.getConstant("WATER_DENSITY"), 1.2,
                    0.001);
            constants.toDefault("WATER_DENSITY");
            Assert.assertEquals(constants.getConstant("WATER_DENSITY"), 1.0,
                    0.001);
        } catch(IOException ex) {
            Assert.fail();
        }
    }
}
