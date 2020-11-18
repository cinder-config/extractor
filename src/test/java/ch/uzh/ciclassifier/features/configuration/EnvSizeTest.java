package ch.uzh.ciclassifier.features.configuration;

import ch.uzh.ciclassifier.evaluation.Evaluation;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EnvSizeTest {

    @Test
    public void extractionTest() throws IOException {
        Evaluation evaluation = Evaluation.createFromFilePath("src/test/java/ch/uzh/ciclassifier/features/.test-travis-ci.yml");

        EnvSize feature = new EnvSize();
        feature.extract(evaluation);

        assertEquals(Integer.valueOf(7), feature.getSize());
    }

    @Test
    public void emptyTest() throws IOException {
        String configuration = "language: bar\nsudo: false";
        Evaluation evaluation = Evaluation.createFromConfiguration(configuration);

        EnvSize feature = new EnvSize();
        feature.extract(evaluation);

        assertEquals(Integer.valueOf(0), feature.getSize());
    }

    @Test
    public void noGlobalOrJobsTest() throws IOException {
        String configuration = "env:\n" +
                "  - PXT_ENV=production CXX=g++-4.8\n";
        Evaluation evaluation = Evaluation.createFromConfiguration(configuration);

        EnvSize feature = new EnvSize();
        feature.extract(evaluation);

        assertEquals(Integer.valueOf(2), feature.getSize());
    }

    @Test
    public void nullGlobalTest() throws IOException {
        String configuration = "env:\n" +
                "  jobs:\n" +
                "  global:\n" +
                "    - MADARA_ROOT=$TRAVIS_BUILD_DIR/madara\n" +
                "    - GAMS_ROOT=$TRAVIS_BUILD_DIR\n";

        Evaluation evaluation = Evaluation.createFromConfiguration(configuration);

        EnvSize feature = new EnvSize();
        feature.extract(evaluation);

        assertEquals(Integer.valueOf(2), feature.getSize());


    }

    @Test
    public void stringTest() throws IOException {
        String configuration = "env: COMPILER=g++ C_COMPILER=gcc BUILD_TYPE=Release\n";

        Evaluation evaluation = Evaluation.createFromConfiguration(configuration);

        EnvSize feature = new EnvSize();
        feature.extract(evaluation);

        assertEquals(Integer.valueOf(3), feature.getSize());
    }

    @Test
    public void linkedListTest() throws IOException {
        String configuration = "env:\n" +
                "  global:\n" +
                "    secure2: \"lA8kB2x48k7Y4fkdIX5ecOtD/DJgJWCTI6rSkSjshNYwaTTBN6rK4qeRGWZXueYW/P28oJ3A5wdb+JsK2OMlLM0iTnwoX3SwCuD7SI0QpzWn6xcX1+vQgOTMq3fsI7jIbVjjLXxi2vx7oTtXlH6icAr1rqB5lW45AYjTdORkXws=\"\n" +
                "    secure: \"asdfasdfasdf3dafaer33h38sxncxj=\"\n";

        Evaluation evaluation = Evaluation.createFromConfiguration(configuration);

        EnvSize feature = new EnvSize();
        feature.extract(evaluation);

        assertEquals(Integer.valueOf(2), feature.getSize());
    }
}