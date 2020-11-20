package ch.uzh.ciclassifier.features.travisci;

import ch.uzh.ciclassifier.evaluation.Evaluation;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BuildTimeLatestAverageTest {

    @Test
    public void extractTest() throws IOException {
        Evaluation evaluation = Evaluation.createFromGitUrl("https://github.com/tzemp/ciclassifier-sample-project.git");

        BuildTimeLatestAverage feature = new BuildTimeLatestAverage();
        feature.extract(evaluation);

        assertEquals("39.75", feature.getData());
    }

    @Test
    public void extract2Test() throws IOException {
        Evaluation evaluation = Evaluation.createFromGitUrl("https://github.com/OCA/sale-workflow.git");

        BuildTimeLatestAverage feature = new BuildTimeLatestAverage();
        feature.extract(evaluation);

        assertEquals("879.36", feature.getData());
    }
}