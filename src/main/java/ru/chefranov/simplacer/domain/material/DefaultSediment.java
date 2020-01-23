package ru.chefranov.simplacer.domain.material;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Default Sediment.
 * Use DefaultSediment.Builder to creates objects of this type.
 * @author Chefranov R.M.
 */
public class DefaultSediment implements Sediment {

    private final Map<Grain, Double> composition;

    /**
     * @param composition Composition
     */
    private DefaultSediment(Map<Grain, Double> composition) {
        this.composition = composition;
    }

    /**
     * Returns the Grain percentage, %.
     * @param grain Grain
     * @return Grain percentage, >= 0.0 && <= 100.0
     */
    @Override
    public double getGrainPercentage(Grain grain) {
        return composition.getOrDefault(grain, 0.0);
    }

    /**
     * Returns the composition as (grain -> percentage) map.
     * @return Composition
     */
    @Override
    public Map<Grain, Double> getComposition() {
        return new HashMap<>(composition);
    }

    /**
     * Returns a string representation of this object.
     * @return String representation of this object
     */
    @Override
    public String toString() {
        return String.format("%s[composition=%s]", getClass().getSimpleName(), composition);
    }

    /**
     * Default Sediment Builder.
     * @author Chefranov R.M.
     */
    public static class Builder implements SedimentBuilder {

        private final Map<Grain, Double> composition = new HashMap<>();

        private double admissibleDeviation = -1.0;

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
        public Sediment getSediment() throws IOException {
            if (composition.isEmpty()) {
                return null;
            }
            double sum = composition.values().stream().reduce(0.0, Double::sum);
            if (admissibleDeviation >= 0.0 &&
                    (sum < (100.0 - admissibleDeviation) ||
                            sum > (100.0 + admissibleDeviation))) {
                throw new IOException("ERROR_SEDIMENT_BUILDER_COMPOSITION_" +
                        "INCORRECT");
            }
            Map<Grain, Double> sedimentComposition = new HashMap<>(composition);
            if (sum != 100.0) {
                double k = 100.0 / sum;
                for (Map.Entry<Grain, Double> entry :
                        sedimentComposition.entrySet()) {
                    sedimentComposition.put(entry.getKey(),
                            entry.getValue() * k);
                }
            }
            return new DefaultSediment(sedimentComposition);
        }

        /**
         * Adds the Grain percentage.
         * @param grain Grain
         * @param percentage Percentage, %, > 0.0
         * @return This
         * @throws IOException if the percentage of the same Grain already have
         * been added
         */
        @Override
        public SedimentBuilder addGrainPercentage(Grain grain,
                                                  double percentage)
                throws IOException {
            if(composition.containsKey(grain)) {
                throw new IOException("ERROR_SEDIMENT_BUILDER_GRAIN_PERCENTAGE_COLLISION");
            }
            composition.put(grain, percentage);
            return this;
        }

        /**
         * Clears the composition.
         */
        public void clearComposition() {
            composition.clear();
        }

        /**
         * Returns a string representation of this object.
         * @return String representation of this object
         */
        @Override
        public String toString() {
            return String.format("%s[admissibleDeviation=%.1f, composition=%s]",
                    getClass().getSimpleName(), admissibleDeviation, composition);
        }
    }
}
