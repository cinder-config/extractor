package ch.uzh.ciclassifier.features.travisci;

import ch.uzh.ciclassifier.evaluation.Evaluation;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BuildTimeAverageTest {

    @Test
    public void extractText() throws IOException {
        Evaluation evaluation = Evaluation.createFromGitUrl("https://github.com/tzemp/ciclassifier-sample-project.git");

        BuildTimeAverage feature = new BuildTimeAverage();
        feature.extract(evaluation);

        assertEquals("39.75", feature.getData());
    }

    @Test
    public void extract2Test() throws IOException {
        Evaluation evaluation = Evaluation.createFromGitUrl("https://github.com/OCA/sale-workflow.git");

        BuildTimeAverage feature = new BuildTimeAverage();
        feature.extract(evaluation);

        assertEquals("740.3148148148148", feature.getData());
    }
}