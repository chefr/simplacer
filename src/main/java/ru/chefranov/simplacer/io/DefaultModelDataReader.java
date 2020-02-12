package ru.chefranov.simplacer.io;

import ru.chefranov.simplacer.Services;
import ru.chefranov.simplacer.domain.DefaultModel;
import ru.chefranov.simplacer.domain.Model;
import ru.chefranov.simplacer.domain.area.*;
import ru.chefranov.simplacer.domain.material.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Default Model Data Reader.
 * @author Chefranov R.M.
 */
public class DefaultModelDataReader implements ModelDataReader {

    private static Logger logger = Services.getLogger();

    /**
     * Reads the Model data and returns new Model with read data.
     * @param path Path
     * @return Model Model
     * @throws java.io.IOException if an I/O error occurs
     */
    @Override
    public Model readData(Path path) throws IOException {
        Map<DataSections, String[]> dataMap = parseDataToMap(path);
        Map<Parameters, Double> parameters =
                readParameters(dataMap.get(DataSections.PARAMETERS));
        double[][] relief = readRelief(dataMap.get(DataSections.RELIEF));
        Map<String, Mineral> minerals =
                readMinerals(dataMap.get(DataSections.MINERALS));
        Map<String, Grain> grains = readGrains(dataMap.get(DataSections.GRAINS),
                minerals);
        MaterialRepo repo = new DefaultMaterialRepo(
                minerals.values().toArray(new Mineral[0]),
                grains.values().toArray(new Grain[0]));
        Double admDeviation = parameters.get(Parameters.ADMISSIBLE_DEVIATION);
        Sediment defSediment = readDefaultSediment(
                dataMap.get(DataSections.DEFAULT_SEDIMENT), repo, admDeviation);
        Cut[][] cuts = readCuts(dataMap.get(DataSections.COMPOSITION), repo,
                relief, admDeviation);
        Wave[] waves = readWaves(dataMap.get(DataSections.WAVES));
        Area area = new DefaultArea(cuts, defSediment,
                parameters.get(Parameters.DISTANCE), waves, repo);
        return new DefaultModel(area);
    }

    private Map<Parameters, Double> readParameters(String[] dataLines)
            throws IOException {
        Map<Parameters, Double> parameters = new HashMap<>();
        for(String line : dataLines) {
            String[] arr = line.split("\\s+");
            if(arr.length != 2) {
                logger.log(Level.FINE,
                        "ERROR_MODEL_DATA_READER_PARAMETERS_DATA_INCORRECT");
                throw new IOException();
            }
            try {
                Parameters parameter = Parameters.valueOf(arr[0].toUpperCase());
                double value = Double.parseDouble(arr[1]);
                if(!parameter.validate(value)) {
                    logger.log(Level.FINE, "ERROR_MODEL_DATA_READER_" +
                            "PARAMETER_VALUE_INCORRECT");
                    throw new IOException();
                }
                parameters.put(parameter, value);
            } catch(NumberFormatException ex) {
                logger.log(Level.FINE,
                        "ERROR_MODEL_DATA_READER_PARAMETER_VALUE_INCORRECT");
                throw new IOException(ex);
            } catch(IllegalArgumentException ex) {
                logger.log(Level.FINE,
                        "ERROR_MODEL_DATA_READER_PARAMETER_UNKNOWN");
                throw new IOException(ex);
            }
        }
        for(Parameters parameter : Parameters.values()) {
            if(parameter.isRequired && !parameters.containsKey(parameter)) {
                logger.log(Level.FINE,
                        "ERROR_MODEL_DATA_READER_REQUIRED_PARAMETER_MISSING");
                throw new IOException();
            }
        }
        return parameters;
    }

    private double[][] readRelief(String[] dataLines) throws IOException {
        double[][] relief = new double[dataLines.length][];
        try {
            for(int i = 0; i < dataLines.length; ++i) {
                relief[i] = Arrays.stream(dataLines[i].split("\\s+")).
                        mapToDouble(Double::parseDouble).toArray();
            }
        } catch(NumberFormatException ex) {
            logger.log(Level.FINE,
                    "ERROR_MODEL_DATA_READER_RELIEF_HEIGHT_INCORRECT");
            throw new IOException();
        }
        return relief;
    }

    private Map<String, Mineral> readMinerals(String[] dataLines)
            throws IOException {
        Map<String, Mineral> minerals = new HashMap<>();
        double density;
        for(String line : dataLines) {
            String[] arr = line.split("\\s+");
            if(arr.length != 2) {
                logger.log(Level.FINE,
                        "ERROR_MODEL_DATA_READER_MINERAL_DATA_INCORRECT");
                throw new IOException();
            }
            try {
                density = Double.parseDouble(arr[1]);
            } catch(NumberFormatException ex) {
                logger.log(Level.FINE, "ERROR_MODEL_DATA_READER_MINERAL_" +
                                "DENSITY_INCORRECT", arr[1]);
                throw new IOException(ex);
            }
            if(density <= 0.0) {
                logger.log(Level.FINE, "ERROR_MODEL_DATA_READER_MINERAL_" +
                                "DENSITY_LESS_THAN_OR_EQUAL_TO_ZERO");
                throw new IOException();
            }
            minerals.put(arr[0], new DefaultMineral(arr[0], density));
        }
        return minerals;
    }

    private Map<String, Grain> readGrains(String[] dataLines,
                                          Map<String, Mineral> minerals)
            throws IOException {
        Map<String, Grain> grains = new HashMap<>();
        double diameter;
        for(String line : dataLines) {
            String[] arr = line.split("\\s+");
            if(arr.length != 3) {
                logger.log(Level.FINE,
                        "ERROR_MODEL_DATA_READER_GRAIN_DATA_INCORRECT");
                throw new IOException();
            }
            Mineral mineral = minerals.get(arr[1]);
            if(mineral == null) {
                logger.log(Level.FINE,
                        "ERROR_MODEL_DATA_READER_GRAIN_MINERAL_UNKNOWN");
                throw new IOException();
            }
            try {
                diameter = Double.parseDouble(arr[2]);
            } catch(NumberFormatException ex) {
                logger.log(Level.FINE, "ERROR_MODEL_DATA_READER_GRAIN_" +
                                "DIAMETER_INCORRECT", arr[2]);
                throw new IOException(ex);
            }
            if(diameter <= 0.0) {
                logger.log(Level.FINE, "ERROR_MODEL_DATA_READER_GRAIN_" +
                                "DIAMETER_LESS_THAN_OR_EQUAL_TO_ZERO");
                throw new IOException();
            }
            if(diameter > 0.1) {
                logger.log(Level.FINE,
                        "ERROR_MODEL_DATA_READER_GRAIN_DIAMETER_EXCEEDS_LIMIT");
                throw new IOException();
            }
            grains.put(arr[0], new DefaultGrain(arr[0], mineral, diameter));
        }
        return grains;
    }

    private Sediment readDefaultSediment(String[] dataLines, MaterialRepo repo,
                                         Double admissibleDeviation)
            throws IOException {
        if(dataLines.length != 1) {
            logger.log(Level.FINE,
                    "ERROR_MODEL_DATA_READER_DEFAULT_SEDIMENT_INCORRECT");
            throw new IOException();
        }
        String[] dataArray = dataLines[0].split("\\s+");
        if(dataArray.length % 2 != 0) {
            logger.log(Level.FINE,
                    "ERROR_MODEL_DATA_READER_DEFAULT_SEDIMENT_INCORRECT");
            throw new IOException();
        }
        SedimentBuilder builder =
                new DefaultSediment.Builder(repo.getNumberOfGrains());
        if(admissibleDeviation != null) {
            builder.setAdmissibleDeviation(admissibleDeviation);
        }
        return readSediment(dataArray, 0, builder, repo);
    }

    private Cut[][] readCuts(String[] dataLines, MaterialRepo repo,
                             double[][] relief, Double admissibleDeviation)
            throws IOException {
        Cut[][] cuts = new DefaultCut[relief.length][];
        for(int i = 0; i < relief.length; ++i) {
            cuts[i] = new DefaultCut[relief[i].length];
            for(int j = 0; j < relief[i].length; ++j) {
                cuts[i][j] = new DefaultCut(relief[i][j]);
            }
        }
        for(String line : dataLines) {
            String[] dataArray = line.split("\\s+");
            if(dataArray.length < 4) {
                logger.log(Level.FINE,
                        "ERROR_MODEL_DATA_READER_CUT_DATA_INCORRECT");
                throw new IOException();
            }
            int column, row;
            try {
                column = Integer.parseInt(dataArray[0]);
                row = Integer.parseInt(dataArray[1]);
            } catch(NumberFormatException ex) {
                logger.log(Level.FINE,
                        "ERROR_MODEL_DATA_READER_CUT_POSITION_INCORRECT");
                throw new IOException(ex);
            }
            if(column < 0 || column > cuts.length || row < 0 ||
                    row > cuts[column].length ) {
                logger.log(Level.FINE,
                        "ERROR_MODEL_DATA_READER_CUT_POSITION_INCORRECT");
                throw new IOException();
            }
            SedimentBuilder builder =
                    new DefaultSediment.Builder(repo.getNumberOfGrains());
            if(admissibleDeviation != null) {
                builder.setAdmissibleDeviation(admissibleDeviation);
            }
            if((dataArray.length - 2) % 2 == 0) {
                cuts[column][row].setDefaultSediment(
                        readSediment(dataArray, 2, builder, repo));
            } else {
                double thickness;
                try {
                    thickness = Double.parseDouble(dataArray[2]);
                } catch(NumberFormatException ex) {
                    logger.log(Level.FINE,
                            "ERROR_MODEL_DATA_READER_CUT_LAYER_THICKNESS_" +
                                    "INCORRECT");
                    throw new IOException(ex);
                }
                if(thickness <= 0.0) {
                    logger.log(Level.FINE, "ERROR_MODEL_DATA_READER_CUT_" +
                            "LAYER_THICKNESS_LESS_THAN_OR_EQUAL_TO_ZERO");
                    throw new IOException();
                }
                cuts[column][row].setLayerOnTop(new DefaultLayer(
                        readSediment(dataArray, 3, builder, repo), thickness));
            }
        }
        return cuts;
    }

    private Wave[] readWaves(String[] dataLines) throws IOException {
        Wave[] waves = new DefaultWave[dataLines.length];
        for(int i = 0; i < dataLines.length; ++i) {
            String[] arr = dataLines[i].split("\\s+");
            if (arr.length != 3) {
                logger.log(Level.FINE,
                        "ERROR_MODEL_DATA_READER_WAVES_DATA_INCORRECT");
                throw new IOException();
            }
            double maxVelocity;
            try {
                maxVelocity = Double.parseDouble(arr[0]);
            } catch (NumberFormatException ex) {
                logger.log(Level.FINE, "ERROR_MODEL_DATA_READER_WAVE_MAX_" +
                        "VELOCITY_INCORRECT", arr[0]);
                throw new IOException(ex);
            }
            double direction;
            try {
                direction = Double.parseDouble(arr[1]);
            } catch (NumberFormatException ex) {
                logger.log(Level.FINE, "ERROR_MODEL_DATA_READER_WAVE_" +
                        "DIRECTION_INCORRECT", arr[1]);
                throw new IOException(ex);
            }
            if (direction < 0.0 || direction >= 360.0) {
                logger.log(Level.FINE, "ERROR_MODEL_DATA_READER_WAVE_" +
                        "DIRECTION_INCORRECT", arr[1]);
                throw new IOException();
            }
            double criticalDepth;
            try {
                criticalDepth = Double.parseDouble(arr[2]);
            } catch (NumberFormatException ex) {
                logger.log(Level.FINE, "ERROR_MODEL_DATA_READER_WAVE_" +
                        "CRITICAL_DEPTH_INCORRECT", arr[1]);
                throw new IOException(ex);
            }
            if (criticalDepth <= 0.0) {
                logger.log(Level.FINE, "ERROR_MODEL_DATA_READER_WAVE_" +
                        "CRITICAL_DEPTH_INCORRECT", arr[1]);
            }
            waves[i] = new DefaultWave(maxVelocity, direction, criticalDepth);
        }
        return waves;
    }

    private Sediment readSediment(String[] dataArray, int offset,
                                  SedimentBuilder builder, MaterialRepo repo)
            throws IOException {
        double percentage;
        for(int i = offset; i < dataArray.length; i += 2) {
            int index = repo.getGrainIndex(dataArray[i]);
            if(index == -1) {
                logger.log(Level.FINE,
                        "ERROR_MODEL_DATA_READER_SEDIMENT_GRAIN_NOT_FOUND");
                throw new IOException();
            }
            try {
                percentage = Double.parseDouble(dataArray[i + 1]);
            } catch(NumberFormatException ex) {
                logger.log(Level.FINE, "ERROR_MODEL_DATA_READER_SEDIMENT_" +
                                "GRAIN_PERCENTAGE_INCORRECT");
                throw new IOException(ex);
            }
            if(percentage < 0.0) {
                logger.log(Level.FINE, "ERROR_MODEL_DATA_READER_SEDIMENT_" +
                                "GRAIN_PERCENTAGE_LESS_THAN_ZERO");
                throw new IOException();
            }
            builder.addGrainPercentage(index, percentage);
        }
        return builder.getSediment();
    }

    private Map<DataSections, String[]> parseDataToMap(Path path)
            throws IOException {
        String data = new String(Files.readAllBytes(path));
        Map<DataSections, String[]> dataMap = new HashMap<>();
        String[] sections = data.trim().split("#");
        DataSections section;
        for(int i = 1; i < sections.length; ++i) {
            String curSection = sections[i].trim();
            if(!Character.isAlphabetic(curSection.charAt(0))) {
                logger.log(Level.FINE,
                        "ERROR_MODEL_DATA_READER_SECTION_TITLE_INCORRECT");
                throw new IOException();
            }
            String[] lines = Arrays.stream(curSection.split("\\R")).
                    map(String::trim).filter(el -> !el.isEmpty()).
                    toArray(String[]::new);
            if(lines.length < 2) {
                logger.log(Level.FINE, "ERROR_MODEL_DATA_READER_SECTION_EMPTY");
                throw new IOException();
            }
            String sectionTitle = lines[0].toUpperCase();
            try {
                section = DataSections.valueOf(sectionTitle);
            } catch(IllegalArgumentException ex) {
                logger.log(Level.FINE, "ERROR_MODEL_DATA_READER_SECTION_" +
                                "TITLE_UNKNOWN", sectionTitle);
                throw new IOException();
            }
            if(dataMap.containsKey(section)) {
                logger.log(Level.FINE, "ERROR_MODEL_DATA_READER_SECTION_" +
                                "COLLISION", sectionTitle);
                throw new IOException();
            }
            dataMap.put(section, Arrays.copyOfRange(lines, 1, lines.length));
        }
        for(DataSections ds : DataSections.values()) {
            if(!dataMap.containsKey(ds)) {
                logger.log(Level.FINE, "ERROR_MODEL_DATA_READER_SECTION_NOT_" +
                                "FOUND", ds.toString());
                throw new IOException();
            }
        }
        return dataMap;
    }

    private enum DataSections {
        PARAMETERS, RELIEF, MINERALS, GRAINS, DEFAULT_SEDIMENT, COMPOSITION,
        WAVES
    }

    private enum Parameters {
        ADMISSIBLE_DEVIATION(false, val -> true),
        DISTANCE(true, val -> val > 0.0);

        private boolean isRequired;

        private final Predicate<Double> validator;

        Parameters(boolean isRequired, Predicate<Double> validator) {
            this.isRequired = isRequired;
            this.validator = validator;
        }

        boolean validate(double value) {
            return validator.test(value);
        }
    }
}
