package ch.uzh.ciclassifier.features.travisci;

import ch.uzh.ciclassifier.evaluation.Evaluation;
import ch.uzh.ciclassifier.features.Feature;
import ch.uzh.ciclassifier.features.FeatureType;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;

public class BuildTimeAverage implements Feature {
    private Double average;


    @Override
    public void extract(Evaluation evaluation) throws IOException {
        List<JSONObject> builds = evaluation.getTravisCI().getBuilds();
        List<Long> buildTimes = new ArrayList<>();

        for (JSONObject build : builds) {
            String state = (String) build.get("state");
            if (state.equals("passed")) {
                Long duration = (Long) build.get("duration");
                if (duration != null) {
                    buildTimes.add(duration);
                }
            }
        }

        OptionalDouble average = buildTimes
                .stream()
                .mapToDouble(a -> a)
                .average();

        this.average = average.orElse(0.0);
    }

    @Override
    public String getData() {
        return this.average.toString();
    }

    @Override
    public FeatureType supportedFeatureType() {
        return FeatureType.TRAVISCI;
    }
}
