package ch.uzh.ciclassifier.features.configuration;

import ch.uzh.ciclassifier.evaluation.Evaluation;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UseCacheTest {

    @Test
    public void simpleUse() throws IOException {
        String configuration = "cache:\n" +
                "  bundler: false\n" +
                "  cocoapods: true";
        Evaluation evaluation = Evaluation.createFromConfiguration(configuration);

        UseCache feature = new UseCache();
        feature.extract(evaluation);

        assertTrue(feature.isUse());
    }

    @Test
    public void cacheKeyNotPresentTest() throws IOException {
        String configuration = "language: bar\nsudo: false";
        Evaluation evaluation = Evaluation.createFromConfiguration(configuration);

        UseCache feature = new UseCache();
        feature.extract(evaluation);

        assertFalse(feature.isUse());
    }

    @Test
    public void cacheDisabledTest() throws IOException {
        String configuration = "sudo: true\ncache: false";
        Evaluation evaluation = Evaluation.createFromConfiguration(configuration);

        UseCache feature = new UseCache();
        feature.extract(evaluation);

        assertFalse(feature.isUse());
    }
}