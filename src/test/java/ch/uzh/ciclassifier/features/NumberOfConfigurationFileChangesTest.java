package ch.uzh.ciclassifier.features;

import ch.uzh.ciclassifier.evaluation.Evaluation;
import ch.uzh.ciclassifier.helper.Repository;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NumberOfConfigurationFileChangesTest {

    @Test
    public void extractTest() throws IOException {
        Evaluation evaluation = Evaluation.createFromGitUrl("https://github.com/OCA/sale-workflow.git");

        NumberOfConfigurationFileChanges feature = new NumberOfConfigurationFileChanges();
        feature.extract(evaluation);

        assertEquals(Integer.valueOf(4), feature.getChanges());
    }
}