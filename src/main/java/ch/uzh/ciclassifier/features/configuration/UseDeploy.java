package ch.uzh.ciclassifier.features.configuration;

import ch.uzh.ciclassifier.evaluation.Evaluation;
import ch.uzh.ciclassifier.features.Feature;
import ch.uzh.ciclassifier.features.FeatureType;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;

public class UseDeploy implements Feature {
    private boolean use;

    @Override
    public void extract(Evaluation evaluation) throws IOException {
        JSONObject parsed = evaluation.getConfiguration().getParsed();
        JSONObject config = (JSONObject) parsed.get("config");

        if (!config.containsKey("deploy")) {
            this.use = false;
            return;
        } else {
            JSONArray deploy = (JSONArray) config.get("deploy");
            if (deploy.size() == 1) {
                if (deploy.get(0) instanceof Boolean) {
                    if (!((Boolean) deploy.get(0))) {
                        this.use = false;
                        return;
                    }
                }
            }
        }

        this.use = true;
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
