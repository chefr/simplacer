package ru.chefranov.simplacer.domain.area;

import org.junit.Assert;
import org.junit.Test;
import ru.chefranov.simplacer.domain.material.*;

import java.io.IOException;

/**
 * Tests DefaultArea.
 * @author Chefranov R.M.
 */
public class DefaultAreaTest {

    /**
     * Tests the getRows method.
     */
    @Test
    public void testGetRows() {
        Area area = null;
        try {
            area = getArea();
        } catch(IOException ex) {
            Assert.fail();
        }
        Assert.assertEquals(2, area.getRows());
    }

    /**
     * Tests the getColumns method.
     */
    @Test
    public void testGetColumns() {
        Area area = null;
        try {
            area = getArea();
        } catch(IOException ex) {
            Assert.fail();
        }
        Assert.assertEquals(3, area.getColumns());
    }

    /**
     * Tests the getCutData method.
     */
    @Test
    public void testGetCutData() {
        Area area = null;
        try {
            area = getArea();
        } catch(IOException ex) {
            Assert.fail();
        }
        CutData[][] expected = new CutData[][]{
                {new DefaultCutData(1.0, new LayerData[]{
                        new DefaultLayerData(1.5, 10.0),
                        new DefaultLayerData(0.5, 25.0)}, 10.0),
                        new DefaultCutData(-2.0, new LayerData[]{
                                new DefaultLayerData(1.0, 10.0)}, 25.0),
                        new DefaultCutData(-4.5, new LayerData[]{
                                new DefaultLayerData(2.3, 25.0)}, 25.0)},
                {new DefaultCutData(-1.3, new LayerData[]{}, 25.0),
                        new DefaultCutData(-3.8, new LayerData[]{}, 25.0),
                        new DefaultCutData(-6.0, new LayerData[]{}, 10.0)}};
        CutData[][] actual = area.getCutData(0);
        Assert.assertArrayEquals(expected, actual);
    }

    /**
     * Tests the getRelief method.
     */
    @Test
    public void testGetRelief() {
        Area area = null;
        try {
            area = getArea();
        } catch(IOException ex) {
            Assert.fail();
        }
        Assert.assertArrayEquals(new double[][]{{1.0, -2.0, -4.5},
                {-1.3, -3.8, -6.0}}, area.getRelief());
    }

    /**
     * Tests the next method.
     */
    @Test
    public void next() {
        Area area = null;
        try {
            area = getArea();
        } catch(IOException ex) {
            Assert.fail();
        }
        System.out.println(area);
        area.next();
        System.out.println(area);
        for(int i = 0; i < 50; ++i) {
            area.next();
        }
        System.out.println(area);
    }

    private Area getArea() throws IOException {
        Mineral zircon = new DefaultMineral("Zircon", 4.6);
        Mineral quartz = new DefaultMineral("Quartz", 2.6);
        MaterialRepo repo = new DefaultMaterialRepo(null, new Grain[]{
                new DefaultGrain("Zir01", zircon, 0.01),
                new DefaultGrain("Zir02", zircon, 0.02),
                new DefaultGrain("Qz025", quartz, 0.025)});
        Cut[][] cuts = new Cut[][]{{
                new DefaultCut(1.0),
                new DefaultCut(-2.0),
                new DefaultCut(-4.5)}, {
                new DefaultCut(-1.3),
                new DefaultCut(-3.8),
                new DefaultCut(-6.0)}};
        SedimentBuilder builder = new DefaultSediment.Builder(3);
        builder.addGrainPercentage(0, 10).
                addGrainPercentage(1, 70).
                addGrainPercentage(2, 20);
        Sediment sediment = builder.getSediment();
        cuts[0][0].setDefaultSediment(sediment);
        cuts[1][2].setDefaultSediment(sediment);
        cuts[0][0].setLayerOnTop(new DefaultLayer(sediment, 1.5));
        cuts[0][1].setLayerOnTop(new DefaultLayer(sediment, 1.0));
        builder.clearComposition();
        builder.addGrainPercentage(0, 25).
                addGrainPercentage(1, 75);
        sediment = builder.getSediment();
        cuts[1][0].setDefaultSediment(sediment);
        cuts[0][0].setLayerOnTop(new DefaultLayer(sediment, 0.5));
        cuts[0][2].setLayerOnTop(new DefaultLayer(sediment, 2.3));
        return new DefaultArea(cuts, sediment, 500.0,
                new Wave[]{new DefaultWave(100.0, 280.0, -40.0)}, repo);
    }
}
