package ch.uzh.ciclassifier.features.configuration;

import ch.uzh.ciclassifier.evaluation.Evaluation;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TemplateSimilarityTest {

    @Test
    public void similarTest() throws IOException {
        String configuration = "language: node_js\n" +
                "\n" +
                "node_js:\n" +
                "  - node\n" +
                "  - 'lts/*'\n" +
                "\n" +
                "cache: npm";
        Evaluation evaluation = Evaluation.createFromConfiguration(configuration);

        TemplateSimilarity feature = new TemplateSimilarity();
        feature.extract(evaluation);

        assertEquals(Integer.valueOf(0), feature.getDifferences());
    }

    @Test
    public void smallSimilarityTest() throws IOException {
        String configuration = "language: node_js\n" +
                "\n" +
                "node_js:\n" +
                "  - node\n" +
                "  - 'lts/*'\n" +
                "\n" +
                "cache: yolo";
        Evaluation evaluation = Evaluation.createFromConfiguration(configuration);

        TemplateSimilarity feature = new TemplateSimilarity();
        feature.extract(evaluation);

        assertEquals(Integer.valueOf(2), feature.getDifferences());
    }

    @Test
    public void notSimilarTest() throws IOException {
        Evaluation evaluation = Evaluation.createFromFilePath("src/test/java/ch/uzh/ciclassifier/features/.test-travis-ci.yml");

        TemplateSimilarity feature = new TemplateSimilarity();
        feature.extract(evaluation);

        assertEquals(Integer.valueOf(11), feature.getDifferences());
    }
}