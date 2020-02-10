package ru.chefranov.simplacer.io;

import ru.chefranov.simplacer.domain.Model;

import java.io.IOException;
import java.nio.file.Path;

/**
 * Model Data Reader.
 * @author Chefranov R.M.
 */
public interface ModelDataReader {

    /**
     * Reads the Model data and returns new Model with read data.
     * @param path Path
     * @return Model Model
     * @throws java.io.IOException if an I/O error occurs
     */
    Model readData(Path path) throws IOException;
}
