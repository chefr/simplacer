package ru.chefranov.simplacer;

import ru.chefranov.simplacer.domain.mechanics.ConstantHandler;
import ru.chefranov.simplacer.domain.mechanics.DefaultConstantHandler;

import java.util.logging.Logger;

/**
 * App common services such as logger
 * @author Chefranov R.M.
 */
public class Services {

    private static Logger logger = Logger.getLogger("ru.chefranov.simplacer");

    private static ConstantHandler constantHandler =
            new DefaultConstantHandler();

    /**
     * Returns the logger
     * @return Logger
     */
    public static Logger getLogger() {
        return logger;
    }

    /**
     * Returns the Constant Handler.
     * @return Constant Handler
     */
    public static ConstantHandler getConstantHandler() {
        return constantHandler;
    }
}
