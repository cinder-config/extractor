package ch.uzh.ciclassifier.features.configuration;

import ch.uzh.ciclassifier.evaluation.Evaluation;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UniqueInstructionsTest {

    @Test
    public void simpleTest() throws Exception {
        String configuration = "language: ruby\n" +
                "script:\n" +
                "- script\n" +
                "before_script:\n" +
                "- rm -rf cache\n" +
                "before_install:\n" +
                "- download composer\n" +
                "install:\n" +
                "- composer install";
        Evaluation evaluation = Evaluation.createFromConfiguration(configuration);

        UniqueInstructions feature = new UniqueInstructions();
        feature.extract(evaluation);

        assertEquals(Integer.valueOf(4), feature.getSize());
    }

    @Test
    public void missingTypesTest() throws Exception {
        String configuration = "language: ruby\n" +
                "script:\n" +
                "- script\n";
        Evaluation evaluation = Evaluation.createFromConfiguration(configuration);

        UniqueInstructions feature = new UniqueInstructions();
        feature.extract(evaluation);

        assertEquals(Integer.valueOf(1), feature.getSize());
    }

    @Test
    public void duplicateTest() throws Exception {
        String configuration = "jobs:\n" +
                "  include:\n" +
                "    - stage: test\n" +
                "      script: \n" +
                "      - ./test 1\n" +
                "      - ./test 2\n" +
                "    - # stage name not required, will continue to use `test`\n" +
                "      script: ./test 2\n" +
                "    - stage: deploy\n" +
                "      script: ./deploy";
        Evaluation evaluation = Evaluation.createFromConfiguration(configuration);

        UniqueInstructions feature = new UniqueInstructions();
        feature.extract(evaluation);

        assertEquals(Integer.valueOf(3), feature.getSize());
    }

    @Test
    public void complexTest() throws Exception {
        Evaluation evaluation = Evaluation.createFromFilePath("src/test/java/ch/uzh/ciclassifier/features/.test-travis-ci.yml");

        UniqueInstructions feature = new UniqueInstructions();
        feature.extract(evaluation);

        assertEquals(Integer.valueOf(20), feature.getSize());
    }
}