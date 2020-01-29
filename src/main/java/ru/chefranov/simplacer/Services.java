package ru.chefranov.simplacer;

import java.util.logging.Logger;

/**
 * App common services such as logger
 * @author Chefranov R.M.
 */
public class Services {

    private static Logger logger = Logger.getLogger("ru.chefranov.simplacer");

    /**
     * Returns the logger
     * @return Logger
     */
    public static Logger getLogger() {
        return logger;
    }
}