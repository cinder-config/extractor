package ch.uzh.ciclassifier.features;

import ch.uzh.ciclassifier.helper.Repository;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NumberOfConfigurationFileChangesTest {

    @Test
    public void extractTest() throws IOException {
        Repository repository = new Repository("sale-workflow","https://github.com/OCA/sale-workflow.git");

        NumberOfConfigurationFileChanges feature = new NumberOfConfigurationFileChanges();
        feature.extract(repository);

        assertEquals(Integer.valueOf(4), feature.getChanges());
    }
}