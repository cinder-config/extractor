package ch.uzh.ciclassifier.features.configuration;

import ch.uzh.ciclassifier.evaluation.Evaluation;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParseWarningsTest {

    @Test
    public void warningTest() throws IOException {
        Evaluation evaluation = Evaluation.createFromFilePath("src/test/java/ch/uzh/ciclassifier/features/.test-travis-ci.yml");

        ParseWarnings feature = new ParseWarnings();
        feature.extract(evaluation);

        assertEquals(Integer.valueOf(3), feature.getSize());
    }

    @Test
    public void emptyWarningTest() throws IOException {
        String configuration = "language: ruby\n" +
                "os: linux\n" +
                "dist: xenial\n" +
                "rvm:\n" +
                " - 2.2\n" +
                " - jruby";

        Evaluation evaluation = Evaluation.createFromConfiguration(configuration);

        ParseWarnings feature = new ParseWarnings();
        feature.extract(evaluation);

        assertEquals(Integer.valueOf(0), feature.getSize());
    }
}