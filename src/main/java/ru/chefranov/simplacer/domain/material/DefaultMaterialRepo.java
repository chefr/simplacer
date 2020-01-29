package ru.chefranov.simplacer.domain.material;

/**
 * Default Material Repo.
 * @author Chefranov R.M.
 */
public class DefaultMaterialRepo implements MaterialRepo {

    private Mineral[] minerals;

    private Grain[] grains;

    /**
     * @param minerals Minerals
     * @param grains Grains
     */
    public DefaultMaterialRepo(Mineral[] minerals, Grain[] grains) {
        this.minerals = minerals;
        this.grains = grains;
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
}
