package ch.uzh.ciclassifier.features;

import ch.uzh.ciclassifier.evaluation.Evaluation;
import ch.uzh.ciclassifier.helper.Configuration;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class UseCache implements Feature {
    private boolean use;

    @Override
    public void extract(Evaluation evaluation) throws IOException {
        Configuration configuration = evaluation.getConfiguration();
        this.use = false;

        if (configuration.getParsed().containsKey("cache")) {
            // There is some cache in the config, now we only need to check if the cache is disabled with (cache: false)
            if (configuration.getParsed().get("cache") instanceof Boolean) {
                Boolean value = (Boolean) configuration.getParsed().get("cache");
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
