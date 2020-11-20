package ch.uzh.ciclassifier.features.travisci;

import ch.uzh.ciclassifier.evaluation.Evaluation;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TimeToFixAverageTest {

    @Test
    public void extractTest() throws IOException {
        Evaluation evaluation = Evaluation.createFromGitUrl("https://github.com/tzemp/ciclassifier-sample-project.git");

        TimeToFixAverage feature = new TimeToFixAverage();
        feature.extract(evaluation);

        assertEquals("107.5", feature.getData());
    }

    @Test
    public void extract2Test() throws IOException {
        Evaluation evaluation = Evaluation.createFromGitUrl("https://github.com/OCA/sale-workflow.git");

        TimeToFixAverage feature = new TimeToFixAverage();
        feature.extract(evaluation);

        assertEquals("100085.67567567568", feature.getData());
    }
}