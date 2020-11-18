package ch.uzh.ciclassifier.helper;

import ch.uzh.ciclassifier.evaluation.Evaluation;
import ch.uzh.ciclassifier.exception.ConfigurationMissingException;
import junit.framework.Assert;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class TravisYmlHelperTest {

    @Test
    public void parseTest() throws IOException, ParseException {
        Evaluation evaluation = Evaluation.createFromFilePath("src/test/java/ch/uzh/ciclassifier/features/.test-travis-ci.yml");

        JSONObject response = TravisYmlHelper.parse(evaluation.getConfiguration().getRaw());
        Assertions.assertNotNull(response);

        JSONObject config = (JSONObject) response.get("config");
        Assertions.assertEquals(12, config.size());
    }

    @Test
    public void expandTest() throws IOException, ParseException {
        Evaluation evaluation = Evaluation.createFromFilePath("src/test/java/ch/uzh/ciclassifier/features/.test-travis-ci.yml");

        JSONArray matrix = TravisYmlHelper.expand(TravisYmlHelper.parse(evaluation.getConfiguration().getRaw()));

        Assertions.assertNotNull(matrix);
        Assertions.assertEquals(7, matrix.size());
    }
}