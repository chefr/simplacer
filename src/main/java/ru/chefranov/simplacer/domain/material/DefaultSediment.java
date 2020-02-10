package ru.chefranov.simplacer.domain.material;

import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;

/**
 * Default Sediment.
 * Use DefaultSediment.Builder to creates objects of this type.
 * @author Chefranov R.M.
 */
public class DefaultSediment implements Sediment, Serializable {

    private static final long serialVersionUID = -6182651062081594376L;

    private double[] composition;

    /**
     * @param composition Composition
     */
    private DefaultSediment(double[] composition) {
        this.composition = composition;
    }

    /**
     * Returns the Grain percentage, %.
     * @param index Index of the Grain in the Grain Repository
     * @return Grain percentage, >= 0.0 && <= 100.0
     */
    @Override
    public double getGrainPercentage(int index) {
        return composition[index];
    }

    /**
     * Returns the array [grain index in the Grain Repository] ->
     * percentage of this Grain.
     * @return Composition
     */
    @Override
    public double[] getComposition() {
        return composition.clone();
    }

    /**
     * Returns a string representation of this object.
     * @return String representation of this object
     */
    @Override
    public String toString() {
        return String.format("%s[composition=%s]", getClass().getSimpleName(),
                Arrays.toString(composition));
    }

    /**
     * Default Sediment Builder.
     * @author Chefranov R.M.
     */
    public static class Builder implements SedimentBuilder {

        private double[] composition;

        private double admissibleDeviation = -1.0;

        /**
         * @param numberOfComponents Number of Grains in Grain Repo
         */
        public Builder(int numberOfComponents) {
            composition = new double[numberOfComponents];
        }

        /**
         * Sets the admissible deviation of the sum of grain percentages
         * from 100.0, %.
         * @param admissibleDeviation Admissible deviation. If < 0.0 an
         *                            arbitrarily large deviation is permissible
         * @return Previous admissible deviation
         */
        @Override
        public double setAdmissibleDeviation(double admissibleDeviation) {
            double prevAdmissibleDeviation = this.admissibleDeviation;
            this.admissibleDeviation = admissibleDeviation;
            return prevAdmissibleDeviation;
        }

        /**
         * Returns the admissible deviation.
         * @return Admissible deviation
         */
        @Override
        public double getAdmissibleDeviation() {
            return admissibleDeviation;
        }

        /**
         * Returns the Sediment with formed composition or null if the composition
         * is empty.
         * @return Sediment
         * @throws IOException if the sum of Grain percentages deviates
         * from 100.0(%) more than the admissible deviation
         */
        @Override
        public DefaultSediment getSediment() throws IOException {
            double sum = Arrays.stream(composition).reduce(0.0, Double::sum);
            if(sum == 0.0) {
                return null;
            }
            if(admissibleDeviation >= 0.0 &&
                    (sum < (100.0 - admissibleDeviation) ||
                            sum > (100.0 + admissibleDeviation))) {
                throw new IOException("ERROR_SEDIMENT_BUILDER_COMPOSITION_" +
                        "INCORRECT");
            }
            double[] sedimentComposition = composition.clone();
            if(sum != 100.0) {
                double k = 100.0 / sum;
                sedimentComposition = Arrays.stream(sedimentComposition).
                        map(val -> val * k).toArray();
            }
            return new DefaultSediment(sedimentComposition);
        }

        /**
         * Adds the Grain percentage.
         * @param index Index of the Grain in the Grain Repository
         * @param percentage Percentage, %, > 0.0
         * @return This
         * @throws IOException if the percentage of the same Grain already have
         * been added
         */
        @Override
        public SedimentBuilder addGrainPercentage(int index, double percentage)
                throws IOException {
            if(composition[index] != 0.0) {
                throw new IOException("ERROR_SEDIMENT_BUILDER_GRAIN_PERCENTAGE_COLLISION");
            }
            composition[index] = percentage;
            return this;
        }

        /**
         * Clears the composition.
         */
        @Override
        public void clearComposition() {
            Arrays.fill(composition, 0.0);
        }

        /**
         * Returns a string representation of this object.
         * @return String representation of this object
         */
        @Override
        public String toString() {
            return String.format("%s[admissibleDeviation=%.1f, composition=%s]",
                    getClass().getSimpleName(), admissibleDeviation,
                    Arrays.toString(composition));
        }
    }
}
