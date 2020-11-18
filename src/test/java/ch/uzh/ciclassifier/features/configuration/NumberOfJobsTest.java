package ch.uzh.ciclassifier.features.configuration;

import ch.uzh.ciclassifier.evaluation.Evaluation;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NumberOfJobsTest {

    @Test
    public void oneJobTest() throws Exception {
        String configuration = "language: ruby\n" +
                "os: linux\n" +
                "dist: xenial\n" +
                "rvm:\n" +
                " - jruby";
        Evaluation evaluation = Evaluation.createFromConfiguration(configuration);

        NumberOfJobs feature = new NumberOfJobs();
        feature.extract(evaluation);

        assertEquals(Integer.valueOf(1), feature.getSize());
    }

    @Test
    public void multipleJobTest() throws Exception {
        Evaluation evaluation = Evaluation.createFromFilePath("src/test/java/ch/uzh/ciclassifier/features/.test-travis-ci.yml");

        NumberOfJobs feature = new NumberOfJobs();
        feature.extract(evaluation);

        assertEquals(Integer.valueOf(7), feature.getSize());
    }

    @Test
    public void expansionTest() throws Exception {
        String configuration = "language: python\n" +
                "os:\n" +
                "- linux\n" +
                "- osx\n" +
                "\n" +
                "python:\n" +
                "- 2.7\n" +
                "- 3.6\n" +
                "- 3.7";

        Evaluation evaluation = Evaluation.createFromConfiguration(configuration);

        NumberOfJobs feature = new NumberOfJobs();
        feature.extract(evaluation);

        assertEquals(Integer.valueOf(6), feature.getSize());
    }
}