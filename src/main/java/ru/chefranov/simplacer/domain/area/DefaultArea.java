package ru.chefranov.simplacer.domain.area;

import ru.chefranov.simplacer.domain.material.*;
import ru.chefranov.simplacer.domain.mechanics.DefaultMechanics;

import java.util.Arrays;

/**
 * Default Area.
 * @author Chefranov R.M.
 */
public class DefaultArea implements Area {

    private final Cut[][] cuts;

    private final Sediment defaultSediment;

    private final double distance;

    private Wave[] waves;

    private MaterialRepo repo;

    private DefaultMechanics mechanics;

    /**
     * @param cuts Two-dimensional array of the cuts
     * @param defaultSediment Default sediment
     * @param distance Distance between adjacent cuts, m, > 0.0
     * @param waves Waves
     * @param repo Material repo
     */
    public DefaultArea(Cut[][] cuts, Sediment defaultSediment,
                       double distance, Wave[] waves, MaterialRepo repo) {
        this.cuts = cuts;
        this.defaultSediment = defaultSediment;
        this.distance = distance;
        this.waves = waves;
        this.repo = repo;
        mechanics = new DefaultMechanics(repo);
    }

    /**
     * Returns the number of rows
     * @return Number of rows
     */
    public int getRows() {
        return cuts.length;
    }

    /**
     * Returns the number of columns
     * @return Number of columns
     */
    public int getColumns() {
        return cuts[0].length;
    }

    /**
     * Returns the Cut Data.
     * @param index Index of the Grain in the Grain Repository
     * @return Cut Data
     */
    @Override
    public CutData[][] getCutData(int index) {
        CutData[][] data = new CutData[cuts.length][];
        double defSediment = defaultSediment.getGrainPercentage(index);
        for(int i = 0; i < cuts.length; ++i) {
            data[i] = Arrays.stream(cuts[i]).map(cut -> {
                Sediment sediment = cut.getDefaultSediment();
                return new DefaultCutData(cut.getTopHeight(),
                        cut.getLayerData(index),
                        (sediment == null)? defSediment :
                                sediment.getGrainPercentage(index));
            }).toArray(CutData[]::new);
        }
        return data;
    }

    /**
     * Returns the relief
     * @return Relief
     */
    public double[][] getRelief() {
        int rows = getRows();
        double[][] relief = new double[rows][];
        for(int i = 0; i < rows; ++i) {
            relief[i] = Arrays.stream(cuts[i]).
                    mapToDouble(Cut::getTopHeight).toArray();
        }
        return relief;
    }

    /**
     * Calculates the next state of the field.
     */
    @Override
    public void next() {
        for(Wave wave : waves) {
            waveTransport(wave);
        }
        // tectonics etc. TODO
    }

    /**
     * Returns a string representation of this object.
     * @return String representation of this object
     */
    @Override
    public String toString() {
        return String.format(
                "%s[distance=%.1f, defaultSediment=%s, cuts=%s, waves=%s]",
                getClass().getSimpleName(), distance, defaultSediment,
                Arrays.deepToString(cuts), Arrays.toString(waves));
    }

    private void waveTransport(Wave wave) {
        double ACTIVE_LAYER_THICKNESS = 0.4;  // m, TODO
        int rows = getRows();
        int columns = getColumns();
        double waveDir= wave.getDirection();
        double sinWaveDir = Math.sin(waveDir);
        double cosWaveDir = Math.cos(waveDir);
        int row = (sinWaveDir > 0.0)? rows - 1 : 0;
        int dRow = (sinWaveDir > 0.0)? -1 : 1;
        int rowLimit = (sinWaveDir > 0.0)? -1 : rows;
        int col = (cosWaveDir > 0.0)? 0 : columns - 1;
        int dCol = (cosWaveDir > 0.0)? 1 : -1;
        int colLimit = (cosWaveDir > 0.0)? columns : -1;
        double[][][] angles = calculateAngles(row, dRow, rowLimit, col, dCol,
                colLimit);
        double[][][] waveVelocities = calculateWaveVelocities(wave);
        double[][][] grainVelocities = new double[rows][columns][2];
        double[][][] outDVelocities = new double[rows][columns][2];
        Layer[][] suspension = new DefaultLayer[rows][columns];
        Layer[][] sediment = new DefaultLayer[rows][columns];
        Grain[] grains = repo.getGrains();
        for(int index = 0; index < 1/*grains.length*/; ++index) {  // TODO
            calculateGrainVelocities(grains[index], index, waveVelocities,
                    angles, grainVelocities);
            for(int i = 0; i < rows + columns - 1; i++) {
                for(int curRow = (i >= rows)? rowLimit - dRow : row + i * dRow,
                    curCol = (i >= rows)? col + (i - rows + 1) * dCol : col;
                    (curRow != row - dRow) && (curCol != colLimit);
                    curRow -= dRow, curCol += dCol) {

                    Cut nextXCut = (curRow != rowLimit - dRow)?  // TODO try to create method
                            cuts[curRow + dRow][curCol] : null;
                    double inXDVelocity = (curRow != row)?
                            outDVelocities[curRow - dRow][curCol][0] : 0.0;
                    double outXDVelocity = (nextXCut != null)?
                            (grainVelocities[curRow + dRow][curCol][0] -
                                    grainVelocities[curRow][curCol][0]) : 0.0;
                    outDVelocities[curRow][curCol][0] = outXDVelocity;
                    double balance = calculateBalance(inXDVelocity,
                            outXDVelocity);
                    if(balance > 0) {
                        if(suspension[curRow][curCol] != null) {
                            double eroded = suspension[curRow][curCol].
                                    erode(index, 1.0 - balance);
                            if(nextXCut != null) {
                                if(suspension[curRow + dRow][curCol] == null) {
                                    suspension[curRow + dRow][curCol] =
                                            new DefaultLayer(grains.length);
                                }
                                suspension[curRow + dRow][curCol].
                                        accumulate(index, eroded);
                            }
                        }
                    } else if(balance < 0) {
                        if(sediment[curRow][curCol] == null) {
                            sediment[curRow][curCol] = cuts[curRow][curCol].
                                    erodeLayer(ACTIVE_LAYER_THICKNESS,
                                            defaultSediment, grains.length);
                        }
                        double eroded = sediment[curRow][curCol].
                                erode(index, -balance);
                        if(nextXCut != null) {
                            if(suspension[curRow + dRow][curCol] == null) {
                                suspension[curRow + dRow][curCol] =
                                        new DefaultLayer(grains.length);
                            }
                            suspension[curRow + dRow][curCol].
                                    accumulate(index, eroded);
                        }
                    }

                    Cut nextYCut = (curCol != colLimit - dCol)?
                            cuts[curRow][curCol + dCol] : null;
                    double inYDVelocity = (curCol != col)?
                            outDVelocities[curRow][curCol - dCol][1] : 0.0;
                    double outYDVelocity = (nextYCut != null)?
                            (grainVelocities[curRow][curCol + dCol][1] -
                                    grainVelocities[curRow][curCol][1]) : 0.0;
                    outDVelocities[curRow][curCol][1] = outYDVelocity;
                    balance = calculateBalance(inYDVelocity, outYDVelocity);
                    if(balance > 0) {if(balance > 0) {
                        if(suspension[curRow][curCol] != null) {
                            double eroded = suspension[curRow][curCol].
                                    erode(index, 1.0 - balance);
                            if(nextXCut != null) {
                                if(suspension[curRow + dRow][curCol] == null) {
                                    suspension[curRow + dRow][curCol] =
                                            new DefaultLayer(grains.length);
                                }
                                suspension[curRow + dRow][curCol].
                                        accumulate(index, eroded);
                            }
                        }
                    } else if(balance < 0) {
                        if(sediment[curRow][curCol] == null) {
                            sediment[curRow][curCol] = cuts[curRow][curCol].
                                    erodeLayer(ACTIVE_LAYER_THICKNESS,
                                            defaultSediment, grains.length);
                        }
                        double eroded = sediment[curRow][curCol].
                                erode(index, -balance);
                        if(nextXCut != null) {
                            if(suspension[curRow + dRow][curCol] == null) {
                                suspension[curRow + dRow][curCol] =
                                        new DefaultLayer(grains.length);
                            }
                            suspension[curRow + dRow][curCol].
                                    accumulate(index, eroded);
                        }
                    }
                        if(suspension[curRow][curCol] != null) {
                            double eroded = suspension[curRow][curCol].
                                    erode(index, 1.0 - balance);
                            if(nextYCut != null) {
                                if(suspension[curRow][curCol + dCol] == null) {
                                    suspension[curRow][curCol = dCol] =
                                            new DefaultLayer(grains.length);
                                }
                                suspension[curRow][curCol + dCol].
                                        accumulate(index, eroded);
                            }
                        }
                    } else if(balance < 0) {
                        if(sediment[curRow][curCol] == null) {
                            sediment[curRow][curCol] = cuts[curRow][curCol].
                                    erodeLayer(ACTIVE_LAYER_THICKNESS,
                                            defaultSediment, grains.length);
                        }
                        double eroded = sediment[curRow][curCol].
                                erode(index, -balance);
                        if(nextYCut != null) {
                            if(suspension[curRow][curCol + dCol] == null) {
                                suspension[curRow][curCol + dCol] =
                                        new DefaultLayer(grains.length);
                            }
                            suspension[curRow][curCol + dCol].
                                    accumulate(index, eroded);
                        }
                    }
                }
            }
        }
        accumulateLayers(sediment);
        accumulateLayers(suspension);
    }

    private double[][][] calculateAngles(int row, int dRow, int rowLimit,
                                         int column, int dColumn,
                                         int columnLimit) {
        int rows = getRows();
        int columns = getColumns();
        double[][][] angles = new double[rows][columns][2];
        for(int i = row; i != rowLimit ; i += dRow) {
            for(int j = column; j != columnLimit; j += dColumn) {
                double curH = cuts[i][j].getTopHeight();
                double xNext =  (j == columnLimit - dColumn)? curH :
                        cuts[i][j + dColumn].getTopHeight();
                angles[i][j][0] = Math.atan((xNext - curH) / distance);
                double yNext = (i == rowLimit - dRow)? curH  :
                        cuts[i + dRow][j].getTopHeight();
                angles[i][j][1] = Math.atan((yNext - curH) / distance);
            }
        }
        return angles;
    }

    private double[][][] calculateWaveVelocities(Wave wave) {
        int rows = getRows();
        int columns = getColumns();
        double[][][] waveVelocities = new double[rows][columns][2];
        double waveDir= wave.getDirection();
        double kX = Math.pow(Math.sin(waveDir), 2.0);
        for(int i = 0; i < rows; ++i) {
            for(int j = 0; j < columns; ++j) {
                double baseVelocity = wave.getVelocityByHeight(cuts[i][j].
                        getTopHeight());
                double xVelocity = baseVelocity * kX;
                waveVelocities[i][j][0] = xVelocity;
                waveVelocities[i][j][1] = baseVelocity - xVelocity;
            }
        }
        return waveVelocities;
    }

    private void calculateGrainVelocities(Grain grain, int index,
                                          double[][][] waveVelocities,
                                          double[][][] angles,
                                          double[][][] grainVelocities) {
        int rows = getRows();
        int columns = getColumns();
        for(int i = 0; i < rows; ++i) {
            for(int j = 0; j < columns; ++j) {
                for(int k = 0; k < 2; ++k) {
                    grainVelocities[i][j][k] =
                            mechanics.calculateGrainInitialVelocity(grain,
                                    index, waveVelocities[i][j][k],
                                    angles[i][j][k]);
                }
            }
        }
    }

    private double calculateBalance(double in, double out) {
        double den = Math.abs(in) + Math.abs(out);
        return (den != 0.0)? (in - out) / den : 0.0;
    }

    private void accumulateLayers(Layer[][] layers) {
        int columns = getColumns();
        for(int i = 0; i < getRows(); ++i) {
            for(int j = 0; j < columns; ++j) {
                if(layers[i][j] != null) {
                    cuts[i][j].addTopLayer(layers[i][j]);
                }
            }
        }
    }
}
