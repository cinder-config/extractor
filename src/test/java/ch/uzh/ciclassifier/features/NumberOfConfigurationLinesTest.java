package ch.uzh.ciclassifier.features;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NumberOfConfigurationLinesTest {

    @Test
    public void extractTest() {
        NumberOfConfigurationLines feature = new NumberOfConfigurationLines();
        feature.extract("src/test/java/ch/uzh/ciclassifier/features/.test-travis-ci.yml");

        assertEquals(Integer.valueOf(63), feature.getLines());
    }
}