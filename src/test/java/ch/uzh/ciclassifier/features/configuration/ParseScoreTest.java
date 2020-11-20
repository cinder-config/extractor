package ch.uzh.ciclassifier.features.configuration;

import ch.uzh.ciclassifier.evaluation.Evaluation;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParseScoreTest {

    @Test
    public void everyLevelTest() throws IOException {
        String configuration = "language: phpp\n" +
                "language: php\n" +
                "os: linuxa\n" +
                "deploy:\n" +
                "  provider: pages\n" +
                "  token: \"MY TOKEN\"\n" +
                "  strategy: git";

        Evaluation evaluation = Evaluation.createFromConfiguration(configuration);

        ParseScore feature = new ParseScore();
        feature.extract(evaluation);

        assertEquals("211", feature.getData());
    }

    @Test
    public void zeroScoreTest() throws IOException {
        String configuration = "language: ruby\n" +
                "os: linux\n" +
                "dist: xenial\n" +
                "rvm:\n" +
                " - 2.2\n" +
                " - jruby";

        Evaluation evaluation = Evaluation.createFromConfiguration(configuration);

        ParseScore feature = new ParseScore();
        feature.extract(evaluation);

        assertEquals("0", feature.getData());
    }
}