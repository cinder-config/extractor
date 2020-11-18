package ch.uzh.ciclassifier.features.configuration;

import ch.uzh.ciclassifier.evaluation.Evaluation;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NumberOfConfigurationLinesTest {

    @Test
    public void extractTest() throws IOException {
        Evaluation evaluation = Evaluation.createFromFilePath("src/test/java/ch/uzh/ciclassifier/features/.test-travis-ci.yml");
        NumberOfConfigurationLines feature = new NumberOfConfigurationLines();
        feature.extract(evaluation);

        assertEquals(Integer.valueOf(66), feature.getLines());
    }
}