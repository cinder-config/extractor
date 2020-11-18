package ch.uzh.ciclassifier.features.configuration;

import ch.uzh.ciclassifier.evaluation.Evaluation;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UseBranchesTest {

    @Test
    public void disabledTest() throws IOException {
        String configuration = "langauge: java";
        Evaluation evaluation = Evaluation.createFromConfiguration(configuration);

        UseBranches feature = new UseBranches();
        feature.extract(evaluation);

        assertFalse(feature.isUse());
    }

    @Test
    public void simpleTest() throws IOException {
        String configuration = "branches: master";
        Evaluation evaluation = Evaluation.createFromConfiguration(configuration);

        UseBranches feature = new UseBranches();
        feature.extract(evaluation);

        assertTrue(feature.isUse());
    }

    @Test
    public void complexTest() throws IOException {
        String configuration = "branches:\n" +
                "  only:\n" +
                "  - master\n" +
                "  except:\n" +
                "  - develop";
        Evaluation evaluation = Evaluation.createFromConfiguration(configuration);

        UseBranches feature = new UseBranches();
        feature.extract(evaluation);

        assertTrue(feature.isUse());
    }
}