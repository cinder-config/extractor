package ch.uzh.ciclassifier.features;

import ch.uzh.ciclassifier.evaluation.Evaluation;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EnvSizeTest {

    @Test
    public void extractionTest() throws IOException {
        Evaluation evaluation = Evaluation.createFromFilePath("src/test/java/ch/uzh/ciclassifier/features/.test-travis-ci.yml");

        EnvSize feature = new EnvSize();
        feature.extract(evaluation);

        assertEquals(Integer.valueOf(5), feature.getSize());
    }

    @Test
    public void emptyTest() throws IOException {
        String configuration = "language: bar\nsudo: false";
        Evaluation evaluation = Evaluation.createFromConfiguration(configuration);

        EnvSize feature = new EnvSize();
        feature.extract(evaluation);

        assertEquals(Integer.valueOf(0), feature.getSize());
    }

    @Test
    public void noGlobalOrJobs() throws IOException {
        String configuration = "env:\n" +
                "  - PXT_ENV=production CXX=g++-4.8\n";
        Evaluation evaluation = Evaluation.createFromConfiguration(configuration);

        EnvSize feature = new EnvSize();
        feature.extract(evaluation);

        assertEquals(Integer.valueOf(1), feature.getSize());
    }
}