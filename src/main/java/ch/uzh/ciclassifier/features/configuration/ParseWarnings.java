package ch.uzh.ciclassifier.features.configuration;

import ch.uzh.ciclassifier.evaluation.Evaluation;
import ch.uzh.ciclassifier.features.Feature;
import ch.uzh.ciclassifier.features.FeatureType;
import ch.uzh.ciclassifier.helper.Configuration;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;

public class ParseWarnings implements Feature {
    private Integer size;

    @Override
    public void extract(Evaluation evaluation) throws IOException {
        this.size = 0;

        Configuration configuration = evaluation.getConfiguration();
        JSONObject parsed = configuration.getParsed();

        JSONArray messages = (JSONArray) parsed.get("messages");
        if (null != messages) {
            this.size = messages.size();
        }
    }

    @Override
    public String getData() {
        return size.toString();
    }

    @Override
    public FeatureType supportedFeatureType() {
        return FeatureType.CONFIGURATION;
    }

    public Integer getSize() {
        return size;
    }
}
