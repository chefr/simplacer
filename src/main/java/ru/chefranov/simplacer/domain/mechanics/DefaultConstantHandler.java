package ru.chefranov.simplacer.domain.mechanics;

import ru.chefranov.simplacer.Services;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;
import java.util.logging.Level;

/**
 * Default Constant Handler.
 * @author Chefranov R.M.
 */
public class DefaultConstantHandler implements ConstantHandler {

    private HashMap<String, Double> defaultConstants = new HashMap<>();

    private HashMap<String, Double> customizedConstants;

    /**
     * @throws IOException if an I/O error occurs
     */
    public DefaultConstantHandler() throws IOException {
        try(InputStream is = getClass().
                getResourceAsStream("resources/constants")) {  // TODO
            Properties constants = new Properties();
            constants.load(is);
            constants.forEach((key, value) ->
                defaultConstants.put(key.toString(),
                        Double.parseDouble(value.toString())));
        }
    }

    /**
     * Associates the specified constant with the specified key.
     * @param key Kye
     * @param value Value
     * @throws IOException if trying to set an unknown constant
     */
    @Override
    public void setConstant(String key, double value) throws IOException {
        if(!defaultConstants.containsKey(key)) {
            String message = "ERROR_CONSTANT_HANDLER_CONSTANT_UNKNOWN";
            Services.getLogger().log(Level.FINE, message);
            throw new IOException(message);
        }
        if(customizedConstants == null) {
            customizedConstants = new HashMap<>();
        }
        customizedConstants.put(key, value);
    }

    /**
     * Returns the constant value to which the specified key is mapped, or null
     * if there is no constant for the key.
     * @param key Kye
     * @return Constant value to which the specified key is mapped or null
     */
    @Override
    public Double getConstant(String key) {
        Double defaultValue = defaultConstants.get(key);
        if(customizedConstants == null) {
            return defaultValue;
        }
        return customizedConstants.getOrDefault(key, defaultValue);
    }

    /**
     * Sets constant to which the specified key is mapped to default value
     * @param key Kye
     */
    @Override
    public void toDefault(String key) {
        customizedConstants.remove(key);
    }
}
