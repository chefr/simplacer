package ru.chefranov.simplacer.io;

import org.junit.Assert;
import org.junit.Test;
import ru.chefranov.simplacer.domain.Model;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * Test DefaultModelDataReaderTest.
 * @author Chefranov R.M.
 */
public class DefaultModelDataReaderTest {

    /**
     * Tests the readData methods.
     */
    @Test
    public void readData() {
        DefaultModelDataReader reader = new DefaultModelDataReader();
        try {
            Model model = reader.readData(Paths.get(
                    "src/test/resources/sample_area_data"));
            System.out.println(model);
        } catch(IOException ex) {
            Assert.fail();
        }
    }
}
