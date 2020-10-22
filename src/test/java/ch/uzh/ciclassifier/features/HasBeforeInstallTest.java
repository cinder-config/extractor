package ch.uzh.ciclassifier.features;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HasBeforeInstallTest {

    @Test
    public void extractPositiveTest() throws IOException {
        HasBeforeInstall feature = new HasBeforeInstall();
        feature.extract("src/test/java/ch/uzh/ciclassifier/features/.test-travis-ci.yml");

        assertEquals(true, feature.hasBeforeInstall());
    }

    @Test
    public void extractNegativeTest() throws IOException {
        HasBeforeInstall feature = new HasBeforeInstall();
        feature.extract("src/test/java/ch/uzh/ciclassifier/features/.stages-travis-ci.yml");

        assertEquals(false, feature.hasBeforeInstall());
    }
}