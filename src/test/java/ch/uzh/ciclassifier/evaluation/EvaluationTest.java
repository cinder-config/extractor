package ch.uzh.ciclassifier.evaluation;

import ch.uzh.ciclassifier.features.BeforeScriptSize;
import ch.uzh.ciclassifier.features.FeatureType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EvaluationTest {

    @Test
    public void configurationTest() throws IOException {
        String configuration = "language: ruby\n" +
                "rvm:\n" +
                " - 2.2\n" +
                " - jruby";

        Evaluation evaluation = Evaluation.createFromConfiguration(configuration);

        Assertions.assertNotNull(evaluation.getConfiguration());
        Assertions.assertNotNull(evaluation.getRawConfiguration());
        Assertions.assertTrue(evaluation.getTypes().contains(FeatureType.CONFIGURATION));
    }

    @Test
    public void gitUrlTest() throws IOException {
        String gitUrl = "https://github.com/OCA/sale-workflow.git";

        Evaluation evaluation = Evaluation.createFromGitUrl(gitUrl);

        Assertions.assertNotNull(evaluation.getConfiguration());
        Assertions.assertNotNull(evaluation.getRawConfiguration());
        Assertions.assertNotNull(evaluation.getGitUrl());
        Assertions.assertNotNull(evaluation.getRepository());

        Assertions.assertTrue(evaluation.getTypes().contains(FeatureType.CONFIGURATION));
        Assertions.assertTrue(evaluation.getTypes().contains(FeatureType.REPOSITORY));
        Assertions.assertTrue(evaluation.getTypes().contains(FeatureType.TRAVISCI));
    }
}