package ch.uzh.ciclassifier.features.configuration;

import ch.uzh.ciclassifier.evaluation.Evaluation;
import ch.uzh.ciclassifier.features.Feature;
import ch.uzh.ciclassifier.features.FeatureType;
import ch.uzh.ciclassifier.helper.Configuration;
import org.json.simple.JSONObject;

import java.io.IOException;

public class UseCache implements Feature {
    private boolean use;

    @Override
    public void extract(Evaluation evaluation) throws IOException {
        JSONObject parsed = evaluation.getConfiguration().getParsed();
        JSONObject config = (JSONObject) parsed.get("config");

        this.use = false;
        if (config.containsKey("cache")) {
            // There is some cache in the config, now we only need to check if the cache is disabled with (cache: false)
            if (config.get("cache") instanceof Boolean) {
                Boolean value = (Boolean) config.get("cache");
                this.use = value;
            } else {
                this.use = true;
            }
        }
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
