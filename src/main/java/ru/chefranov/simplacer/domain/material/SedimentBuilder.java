package ru.chefranov.simplacer.domain.material;

import java.io.IOException;

/**
 * Sediment Builder
 * @author Chefranov R.M.
 */
public interface SedimentBuilder {

    /**
     * Sets the admissible deviation of the sum of grain percentages
     * from 100.0, %.
     * @param admissibleDeviation Admissible deviation. If < 0.0 an arbitrarily
     *                            large deviation is permissible
     * @return Previous admissible deviation
     */
    double setAdmissibleDeviation(double admissibleDeviation);

    /**
     * Returns the admissible deviation.
     * @return Admissible deviation
     */
    double getAdmissibleDeviation();

    /**
     * Returns the Sediment with formed composition or null if the composition
     * is empty.
     * @return Sediment
     * @throws IOException if the sum of Grain percentages deviates
     * from 100.0(%) more than the admissible deviation
     */
    Sediment getSediment() throws IOException;

    /**
     * Adds the Grain percentage.
     * @param grain Grain
     * @param percentage Percentage, %, > 0.0
     * @return This
     * @throws IOException if the percentage of the same Grain already have been
     * added
     */
    SedimentBuilder addGrainPercentage(Grain grain, double percentage)
            throws IOException;

    /**
     * Clears the composition.
     */
    void clearComposition();
}
