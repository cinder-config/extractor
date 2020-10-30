package ch.uzh.ciclassifier.features;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NumberOfStagesTest {

    @Test
    public void extractTest() throws Exception {
        NumberOfStages feature = new NumberOfStages();
        feature.extract("src/test/java/ch/uzh/ciclassifier/features/.stages-travis-ci.yml");

        assertEquals(Integer.valueOf(2), feature.getStages());
    }
}