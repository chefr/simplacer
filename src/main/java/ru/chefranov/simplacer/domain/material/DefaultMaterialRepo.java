package ru.chefranov.simplacer.domain.material;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Default Material Repo.
 * @author Chefranov R.M.
 */
public class DefaultMaterialRepo implements MaterialRepo, Serializable {

    private static final long serialVersionUID = -2647493743001677054L;

    private Mineral[] minerals;

    private Grain[] grains;

    private Map<String, Integer> grainToIndex = new HashMap<>();

    /**
     * @param minerals Minerals
     * @param grains Grains
     */
    public DefaultMaterialRepo(Mineral[] minerals, Grain[] grains) {
        this.minerals = minerals;
        this.grains = grains;
        for(int i = 0; i < grains.length; ++i) {
            grainToIndex.put(grains[i].getIdentifier(), i);
        }
    }

    /**
     * Returns minerals.
     * @return Minerals
     */
    @Override
    public Mineral[] getMinerals() {
        return minerals.clone();
    }

    /**
     * Returns grains.
     * @return Grains
     */
    @Override
    public Grain[] getGrains() {
        return grains.clone();
    }

    /**
     * Returns the number of grains.
     */
    public int getNumberOfGrains() {
        return grains.length;
    }

    /**
     * Returns the index of the grain specified by identifier.
     * @param identifier Identifier
     * @return Index or -1 if there is no grain with the specified identifier
     */
    public int getGrainIndex(String identifier) {
        return grainToIndex.getOrDefault(identifier, -1);
    }
}
