package ch.uzh.ciclassifier.features.github;

import ch.uzh.ciclassifier.evaluation.Evaluation;
import ch.uzh.ciclassifier.features.Feature;
import ch.uzh.ciclassifier.features.FeatureType;
import org.json.simple.JSONObject;

import java.io.IOException;

public class OwnerType implements Feature {
    private String type = null;

    @Override
    public void extract(Evaluation evaluation) throws IOException {
        if (evaluation.getGitHub().getData().containsKey("owner")) {
            JSONObject owner = (JSONObject) evaluation.getGitHub().getData().get("owner");
            if (owner.containsKey("type")) {
                this.type = (String) owner.get("type");
            }
        }
    }

    @Override
    public String getData() {
        return this.type;
    }

    @Override
    public FeatureType supportedFeatureType() {
        return FeatureType.GITHUB;
    }
}
