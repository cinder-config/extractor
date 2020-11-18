package ch.uzh.ciclassifier.features.configuration;

import ch.uzh.ciclassifier.evaluation.Evaluation;
import ch.uzh.ciclassifier.features.Feature;
import ch.uzh.ciclassifier.features.FeatureType;
import org.json.simple.JSONArray;

import java.io.IOException;

public class NumberOfJobs implements Feature {
    private Integer size = null;

    @Override
    public void extract(Evaluation evaluation) throws IOException {
        JSONArray matrix = evaluation.getConfiguration().getMatrix();

        this.size = matrix.size();
    }

    @Override
    public String getData() {
        return this.getSize().toString();
    }

    @Override
    public FeatureType supportedFeatureType() {
        return FeatureType.CONFIGURATION;
    }


    public Integer getSize() {
        return size;
    }
}
