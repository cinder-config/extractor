package ch.uzh.ciclassifier.features.github;

import ch.uzh.ciclassifier.evaluation.Evaluation;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OwnerTypeTest {

    @Test
    public void userTest() throws IOException {
        Evaluation evaluation = Evaluation.createFromGitUrl("https://github.com/tzemp/ciclassifier-sample-project.git");

        OwnerType feature = new OwnerType();
        feature.extract(evaluation);

        assertEquals("User", feature.getData());
    }

    @Test
    public void organizationTest() throws IOException {
        Evaluation evaluation = Evaluation.createFromGitUrl("https://github.com/OCA/sale-workflow.git");

        OwnerType feature = new OwnerType();
        feature.extract(evaluation);

        assertEquals("Organization", feature.getData());
    }
}