package ch.uzh.ciclassifier.features.configuration;

import ch.uzh.ciclassifier.evaluation.Evaluation;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UseDeployTest {

    @Test
    public void disabledTest() throws IOException {
        String configuration = "deploy: false";
        Evaluation evaluation = Evaluation.createFromConfiguration(configuration);

        UseDeploy feature = new UseDeploy();
        feature.extract(evaluation);

        assertFalse(feature.isUse());
    }

    @Test
    public void notAvailableTest() throws IOException {
        String configuration = "language: java";
        Evaluation evaluation = Evaluation.createFromConfiguration(configuration);

        UseDeploy feature = new UseDeploy();
        feature.extract(evaluation);

        assertFalse(feature.isUse());
    }

    @Test
    public void simpleTest() throws IOException {
        String configuration = "deploy:\n" +
                "  provider: heroku\n" +
                "  api_key:\n" +
                "    secure: \"YOUR ENCRYPTED API KEY\"";
        Evaluation evaluation = Evaluation.createFromConfiguration(configuration);

        UseDeploy feature = new UseDeploy();
        feature.extract(evaluation);

        assertTrue(feature.isUse());
    }
}