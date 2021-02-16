package ch.uzh.ciclassifier.features.travisci;

import ch.uzh.ciclassifier.evaluation.Evaluation;
import ch.uzh.ciclassifier.features.Feature;
import ch.uzh.ciclassifier.features.FeatureType;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.List;

public class ManualInteractionRatio implements Feature {
    private Double ratio = 0.0;

    @Override
    public void extract(Evaluation evaluation) throws IOException {
        List<JSONObject> builds = evaluation.getTravisCI().getBuilds();

        if (builds.size() == 0) {
            this.ratio = 0.0;
            return;
        }

        int counter = 0;

        for (JSONObject build : builds) {
            String eventType = (String) build.get("event_type");
            if (eventType.equals("api")) {
                counter++;
            }
        }

        this.ratio = (double) counter / builds.size();
    }

    @Override
    public String getData() {
        return this.ratio.toString();
    }

    @Override
    public FeatureType supportedFeatureType() {
        return FeatureType.TRAVISCI;
    }
}
