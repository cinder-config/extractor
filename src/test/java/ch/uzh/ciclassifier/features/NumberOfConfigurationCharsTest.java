package ch.uzh.ciclassifier.features;

import ch.uzh.ciclassifier.evaluation.Evaluation;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NumberOfConfigurationCharsTest {

    @Test
    public void extractTest() throws IOException {
        String configuration = "language: bar\nsudo: false";
        Evaluation evaluation = Evaluation.createFromConfiguration(configuration);

        NumberOfConfigurationChars feature = new NumberOfConfigurationChars();
        feature.extract(evaluation);

        assertEquals(Integer.valueOf(24), feature.getChars());
    }
}