package ch.uzh.ciclassifier.features.configuration;

import ch.uzh.ciclassifier.evaluation.Evaluation;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LintScoreTest {

    @Test
    public void noWarningsTest() throws IOException {
        Evaluation evaluation = Evaluation.createFromFilePath("src/test/java/ch/uzh/ciclassifier/features/.test-travis-ci.yml");

        LintScore feature = new LintScore();
        feature.extract(evaluation);

        assertEquals(Integer.valueOf(0), feature.getScore());
    }

    @Test
    public void warningsTest() throws IOException {
        String configuration = "language: bar\nsudo: false"; // NO warnings, wtf?

        Evaluation evaluation = Evaluation.createFromConfiguration(configuration);

        LintScore feature = new LintScore();
        feature.extract(evaluation);

        assertEquals(Integer.valueOf(2), feature.getScore());
    }
}