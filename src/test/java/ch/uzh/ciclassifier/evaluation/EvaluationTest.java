package ch.uzh.ciclassifier.evaluation;

import ch.uzh.ciclassifier.exception.EvaluationNotPossibleException;
import ch.uzh.ciclassifier.features.FeatureType;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

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
    public void invalidConfigurationTest() throws IOException {
        Exception exception = Assertions.assertThrows(EvaluationNotPossibleException.class, () -> {
            String configuration = "language2: \"asdf'";
            Evaluation evaluation = Evaluation.createFromConfiguration(configuration);
        });
    }

    @Test
    public void gitUrlTest() throws IOException {
        String gitUrl = "https://github.com/tzemp/ciclassifier-sample-project.git";

        Evaluation evaluation = Evaluation.createFromGitUrl(gitUrl);

        Assertions.assertNotNull(evaluation.getConfiguration());
        Assertions.assertNotNull(evaluation.getRawConfiguration());
        Assertions.assertNotNull(evaluation.getGitUrl());
        Assertions.assertNotNull(evaluation.getRepository());
        Assertions.assertNotNull(evaluation.getGitHub());

        Assertions.assertTrue(evaluation.getTypes().contains(FeatureType.CONFIGURATION));
        Assertions.assertTrue(evaluation.getTypes().contains(FeatureType.REPOSITORY));
        Assertions.assertTrue(evaluation.getTypes().contains(FeatureType.TRAVISCI));
        Assertions.assertTrue(evaluation.getTypes().contains(FeatureType.GITHUB));
    }

    @Test
    public void invalidGitUrlTest() throws IOException, ParseException {
        Exception exception = Assertions.assertThrows(EvaluationNotPossibleException.class, () -> {
            Evaluation evaluation = Evaluation.createFromGitUrl("https://github.com/tzemp/ciclassifier-sample-projecta.git");
        });
    }
}