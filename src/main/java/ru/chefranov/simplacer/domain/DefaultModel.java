package ru.chefranov.simplacer.domain;

import ru.chefranov.simplacer.domain.area.Area;

/**
 * Default Model.
 * @author Chefranov R.M.
 */
public class DefaultModel implements Model {

    private Area area;

    private int counter;

    /**
     * @param area Area
     */
    public DefaultModel(Area area) {
        this.area = area;
    }

    /**
     * Moves the model to the specified steps forward (if {@code steps}
     * is positive) or backward (if {@code steps} is negative). If
     * {@code counter + steps <= 0} the model will be returned to the initial
     * state
     * @param steps Number of steps to moving the model
     */
    @Override
    public void moveTo(int steps) {
        if(steps < 0) {
            int reqCounter = counter + steps;
            int newCounter = (reqCounter <= 0)? 0 : reqCounter;
            toNearestCounter(newCounter);
            steps = counter - newCounter;
        }
        for(int i = 0; i < steps; ++i) {
            area.next();
            counter++;
        }
    }

    /**
     * Returns a string representation of this object.
     * @return String representation of this object
     */
    @Override
    public String toString() {
        return String.format("%s[counter=%d, area=%s]",
                getClass().getSimpleName(), counter, area);
    }

    private void toNearestCounter(int requestedCount) {
        //  TODO
    }
}
