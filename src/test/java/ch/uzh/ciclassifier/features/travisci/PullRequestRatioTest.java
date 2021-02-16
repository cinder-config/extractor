package ch.uzh.ciclassifier.features.travisci;

import ch.uzh.ciclassifier.evaluation.Evaluation;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PullRequestRatioTest {

    @Test
    public void zeroTest() throws IOException {
        Evaluation evaluation = Evaluation.createFromGitUrl("https://github.com/tzemp/ciclassifier-sample-project.git");

        PullRequestRatio feature = new PullRequestRatio();
        feature.extract(evaluation);

        assertEquals("0.0", feature.getData());
    }
}