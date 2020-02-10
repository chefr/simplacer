package ru.chefranov.simplacer.domain;

/**
 * Model.
 * @author Chefranov R.M.
 */
public interface Model {

    /**
     * Moves the model to the specified steps forward (if {@code steps}
     * is positive) or backward (if {@code steps} is negative).
     * @param steps Number of steps to moving the model
     */
    void moveTo(int steps);
}
