package ru.chefranov.simplacer.domain.area;

/**
 * Area.
 * @author Chefranov R.M.
 */
public interface Area {

    /**
     * Returns the number of rows
     * @return Number of rows
     */
    int getRows();

    /**
     * Returns the number of columns
     * @return Number of columns
     */
    int getColumns();

    /**
     * Returns the Cut Data.
     * @param index Index of the Grain in the Grain Repository
     * @return Cut Data
     */
    CutData[][] getCutData(int index);

    /**
     * Returns the relief
     * @return Relief
     */
    double[][] getRelief();

    /**
     * Calculates the next state of the area.
     */
    void next();
}
