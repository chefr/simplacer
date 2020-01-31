package ru.chefranov.simplacer.domain.material;

/**
 * Default Layer Data.
 * @author Chefranov R.M.
 */
public class DefaultLayerData implements LayerData {

    private double thickness;

    private double percentage;

    /**
     * @param thickness Thickness, m, > 0.0
     * @param percentage Grain percentage, %, >= 0.0 & <= 100.0
     */
    public DefaultLayerData(double thickness, double percentage) {
        this.thickness = thickness;
        this.percentage = percentage;
    }

    /**
     * Returns the thickness, m.
     * @return Thickness, > 0.0
     */
    @Override
    public double getThickness() {
        return thickness;
    }

    /**
     * Returns the percentage of a grain, %.
     * @return Percentage of a grain, >= 0.0 & <= 100.0
     */
    @Override
    public double getPercentage() {
        return percentage;
    }

    /**
     * Returns the result of comparing DefaultLayerData objects for equality.
     * @param otherObject Other object, presumably DefaultLayerData class
     * @return {@code true} if objects are equal to each other, {@code false}
     * otherwise
     */
    @Override
    public boolean equals(Object otherObject) {
        if(this == otherObject) {
            return true;
        }
        if(otherObject == null) {
            return false;
        }
        if(getClass() != otherObject.getClass()) {
            return false;
        }
        DefaultLayerData other = (DefaultLayerData)otherObject;
        return thickness == other.thickness && percentage == other.percentage;
    }

    /**
     * Returns a string representation of this object.
     * @return String representation of this object
     */
    @Override
    public String toString() {
        return String.format("%s[thickness=%.2f, percentage=%.2f]",
                getClass().getSimpleName(), thickness, percentage);
    }
}
