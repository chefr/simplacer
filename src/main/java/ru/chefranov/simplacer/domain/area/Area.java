package ru.chefranov.simplacer.domain.area;

/**
 * Area.
 * @author Chefranov R.M.
 */
public interface Area {

    /**
     * Returns the Cut Data.
     * @param index Index of the Grain in the Grain Repository
     * @return Cut Data
     */
    CutData[][] getCutData(int index);

    /**
     * Calculates the next state of the area.
     */
    void next();
}
