package ch.uzh.ciclassifier.features.travisci;

import ch.uzh.ciclassifier.evaluation.Evaluation;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BuildTimeTest {

    @Test
    public void buildTimeTest() throws IOException {
        Evaluation evaluation = Evaluation.createFromGitUrl("https://github.com/tzemp/ciclassifier-sample-project.git");

        BuildTime feature = new BuildTime();
        feature.extract(evaluation);

        assertEquals(Integer.valueOf(42), feature.getDuration());
    }
}