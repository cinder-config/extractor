package ch.uzh.ciclassifier.features.configuration;

import ch.uzh.ciclassifier.evaluation.Evaluation;
import ch.uzh.ciclassifier.features.Feature;
import ch.uzh.ciclassifier.features.FeatureType;
import ch.uzh.ciclassifier.helper.Configuration;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.*;

public class NumberOfStages implements Feature {
    private Integer size = null;

    @Override
    public void extract(Evaluation evaluation) throws IOException {
        JSONArray matrix = evaluation.getConfiguration().getMatrix();

        Set<String> stages = new HashSet<>();

        matrix.forEach(element -> {
            JSONObject job = (JSONObject) element;
            String stage = (String) job.get("stage");
            stages.add(Objects.requireNonNullElse(stage, ""));
        });

        this.size = stages.size();
    }

    @Override
    public String getData() {
        return this.size.toString();
    }

    @Override
    public FeatureType supportedFeatureType() {
        return FeatureType.CONFIGURATION;
    }

    public Integer getSize() {
        return size;
    }
}
