package ch.uzh.ciclassifier.features.configuration;

import ch.uzh.ciclassifier.evaluation.Evaluation;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NumberOfStagesTest {

    @Test
    public void oneStageTest() throws Exception {
        Evaluation evaluation = Evaluation.createFromFilePath("src/test/java/ch/uzh/ciclassifier/features/.test-travis-ci.yml");

        NumberOfStages feature = new NumberOfStages();
        feature.extract(evaluation);

        assertEquals(Integer.valueOf(1), feature.getSize());
    }

    @Test
    public void multipleStagesTest() throws Exception {
        Evaluation evaluation = Evaluation.createFromFilePath("src/test/java/ch/uzh/ciclassifier/features/.stages-travis-ci.yml");

        NumberOfStages feature = new NumberOfStages();
        feature.extract(evaluation);

        assertEquals(Integer.valueOf(2), feature.getSize());
    }
}