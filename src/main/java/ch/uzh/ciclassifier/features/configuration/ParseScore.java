package ch.uzh.ciclassifier.features.configuration;

import ch.uzh.ciclassifier.evaluation.Evaluation;
import ch.uzh.ciclassifier.features.Feature;
import ch.uzh.ciclassifier.features.FeatureType;
import ch.uzh.ciclassifier.helper.Configuration;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.HashMap;

public class ParseScore implements Feature {
    private Integer score = 0;

    @Override
    public void extract(Evaluation evaluation) throws IOException {
        HashMap<String, Integer> mapping = new HashMap<>();
        mapping.put("info", 1);
        mapping.put("warn", 10);
        mapping.put("error", 100);
        mapping.put("alert", 100);

        Configuration configuration = evaluation.getConfiguration();
        JSONObject parsed = configuration.getParsed();
        JSONArray messages = (JSONArray) parsed.get("messages");

        for (Object tmp : messages) {
            JSONObject message = (JSONObject) tmp;
            String level = (String) message.get("level");

            this.score += mapping.get(level);
        }
    }

    @Override
    public String getData() {
        return score.toString();
    }

    @Override
    public FeatureType supportedFeatureType() {
        return FeatureType.CONFIGURATION;
    }
}
