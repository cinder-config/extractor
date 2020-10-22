package ch.uzh.ciclassifier.features;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BeforeScriptSizeTest {

    @Test
    public void extractFeature() throws IOException {
        BeforeScriptSize feature = new BeforeScriptSize();
        feature.extract("src/test/java/ch/uzh/ciclassifier/features/.test-travis-ci.yml");

        assertEquals(10, feature.getSize());
    }

    @Test
    public void extractZeroSize() throws IOException {
        BeforeScriptSize feature = new BeforeScriptSize();
        feature.extract("src/test/java/ch/uzh/ciclassifier/features/.stages-travis-ci.yml");

        assertEquals(0, feature.getSize());
    }
}