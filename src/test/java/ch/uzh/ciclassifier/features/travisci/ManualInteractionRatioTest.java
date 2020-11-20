package ch.uzh.ciclassifier.features.travisci;

import ch.uzh.ciclassifier.evaluation.Evaluation;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ManualInteractionRatioTest {

    @Test
    public void extractTest() throws IOException {
        Evaluation evaluation = Evaluation.createFromGitUrl("https://github.com/tzemp/ciclassifier-sample-project.git");

        ManualInteractionRatio feature = new ManualInteractionRatio();
        feature.extract(evaluation);

        assertEquals("0.125", feature.getData());
    }
}