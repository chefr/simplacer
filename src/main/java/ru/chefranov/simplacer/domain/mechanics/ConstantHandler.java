package ru.chefranov.simplacer.domain.mechanics;

import java.io.IOException;

/**
 * Constant Handler.
 * @author Chefranov R.M.
 */
public interface ConstantHandler {

    /**
     * Associates the specified constant with the specified key.
     * @param key Kye
     * @param value Value
     * @throws IOException if trying to set an unknown constant
     */
    void setConstant(String key, double value) throws IOException;

    /**
     * Returns the constant value to which the specified key is mapped, or null
     * if there is no constant for the key.
     * @param key Kye
     * @return Constant value to which the specified key is mapped or null
     */
    Double getConstant(String key);

    /**
     * Sets constant to which the specified key is mapped to default value
     * @param key Kye
     */
    void toDefault(String key);
}
