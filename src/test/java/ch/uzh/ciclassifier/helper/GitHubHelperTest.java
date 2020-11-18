package ch.uzh.ciclassifier.helper;

import ch.uzh.ciclassifier.evaluation.Evaluation;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class GitHubHelperTest {

    @Test
    public void constructorTest() throws IOException, ParseException {
        Evaluation evaluation = Evaluation.createFromGitUrl("https://github.com/tzemp/ciclassifier-sample-project.git");

        Assertions.assertNotNull(evaluation.getGitHub());
        Assertions.assertNotNull(evaluation.getGitHub().getData());

        Assertions.assertEquals("ciclassifier-sample-project", evaluation.getGitHub().getData().get("name"));
    }
}