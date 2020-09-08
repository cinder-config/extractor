package ch.uzh.ciclassifier.features;

import ch.uzh.ciclassifier.helper.Repository;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BuildSuccessRatioTest {

    @Test
    public void extractTest() throws IOException {
        String repository = "OCA/sale-workflow";

        BuildSuccessRatio feature = new BuildSuccessRatio();
        feature.extract(repository);

        assertEquals(0.544, feature.getRatio(), 0.01);
    }
}