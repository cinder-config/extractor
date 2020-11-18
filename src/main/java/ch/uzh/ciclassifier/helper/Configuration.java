package ch.uzh.ciclassifier.helper;

import ch.uzh.ciclassifier.exception.ConfigurationInvalidException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class Configuration {
    private String raw;
    private JSONObject parsed;
    private JSONArray matrix;

    public Configuration(String configuration) throws ConfigurationInvalidException, IOException, ParseException {
        this.raw = configuration;

        JSONObject parsed = TravisYmlHelper.parse(configuration);
        if (parsed.containsKey("error")) {
            throw new ConfigurationInvalidException();
        }
        this.parsed = parsed;
        this.matrix = TravisYmlHelper.expand(this.getParsed());
    }

    public String getRaw() {
        return raw;
    }

    public JSONObject getParsed() {
        return parsed;
    }

    public JSONArray getMatrix() {
        return matrix;
    }
}
