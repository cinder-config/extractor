package ch.uzh.ciclassifier.features.github;

import ch.uzh.ciclassifier.evaluation.Evaluation;
import ch.uzh.ciclassifier.features.repository.ConfigChangeFrequency;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PrimaryLanguageTest {

    @Test
    public void extractTest() throws IOException {
        Evaluation evaluation = Evaluation.createFromGitUrl("https://github.com/tzemp/ciclassifier-sample-project.git");

        PrimaryLanguage feature = new PrimaryLanguage();
        feature.extract(evaluation);

        assertEquals("Java", feature.getData());
    }
}