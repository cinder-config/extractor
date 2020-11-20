package ch.uzh.ciclassifier.features.repository;

import ch.uzh.ciclassifier.evaluation.Evaluation;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommitsUntilConfigAddedTest {

    @Test
    public void extractTest() throws IOException {
        Evaluation evaluation = Evaluation.createFromGitUrl("https://github.com/tzemp/ciclassifier-sample-project.git");

        CommitsUntilConfigAdded feature = new CommitsUntilConfigAdded();
        feature.extract(evaluation);

        assertEquals("2", feature.getData());
    }
}