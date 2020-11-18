package ch.uzh.ciclassifier.features.configuration;

import ch.uzh.ciclassifier.evaluation.Evaluation;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NumberOfCommentsTest {

    @Test
    public void extractTest() throws IOException {
        Evaluation evaluation = Evaluation.createFromFilePath("src/test/java/ch/uzh/ciclassifier/features/.comment-travis-ci.yml");

        NumberOfComments feature = new NumberOfComments();
        feature.extract(evaluation);

        assertEquals("9", feature.getData());
    }
}