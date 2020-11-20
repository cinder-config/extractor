package ch.uzh.ciclassifier.features.travisci;

import ch.uzh.ciclassifier.evaluation.Evaluation;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TimeToFixLatestAverageTest {

    @Test
    public void extractTest() throws IOException {
        Evaluation evaluation = Evaluation.createFromGitUrl("https://github.com/tzemp/ciclassifier-sample-project.git");

        TimeToFixLatestAverage feature = new TimeToFixLatestAverage();
        feature.extract(evaluation);

        assertEquals("0.0", feature.getData());
    }

    @Test
    public void extract2Test() throws IOException {
        Evaluation evaluation = Evaluation.createFromGitUrl("https://github.com/OCA/sale-workflow.git");

        TimeToFixLatestAverage feature = new TimeToFixLatestAverage();
        feature.extract(evaluation);

        assertEquals("100085.67567567568", feature.getData());
    }
}