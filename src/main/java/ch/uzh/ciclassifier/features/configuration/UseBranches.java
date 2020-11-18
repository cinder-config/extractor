package ch.uzh.ciclassifier.features.configuration;

import ch.uzh.ciclassifier.evaluation.Evaluation;
import ch.uzh.ciclassifier.features.Feature;
import ch.uzh.ciclassifier.features.FeatureType;
import org.json.simple.JSONObject;

import java.io.IOException;

public class UseBranches implements Feature {
    private boolean use;

    @Override
    public void extract(Evaluation evaluation) throws IOException {
        JSONObject parsed = evaluation.getConfiguration().getParsed();
        JSONObject config = (JSONObject) parsed.get("config");

        this.use = config.containsKey("branches");
    }

    @Override
    public String getData() {
        return this.use ? "1" : "0";
    }

    @Override
    public FeatureType supportedFeatureType() {
        return FeatureType.CONFIGURATION;
    }

    public boolean isUse() {
        return use;
    }
}
