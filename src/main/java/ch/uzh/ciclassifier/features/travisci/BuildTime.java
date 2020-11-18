package ch.uzh.ciclassifier.features.travisci;

import ch.uzh.ciclassifier.evaluation.Evaluation;
import ch.uzh.ciclassifier.features.Feature;
import ch.uzh.ciclassifier.features.FeatureType;

import java.io.IOException;

public class BuildTime implements Feature {
    private Integer duration;


    @Override
    public void extract(Evaluation evaluation) throws IOException {
        this.duration = evaluation.getTravisCI().getBuildTime(evaluation.getRepository().getName());
    }

    @Override
    public String getData() {
        return this.duration.toString();
    }

    @Override
    public FeatureType supportedFeatureType() {
        return FeatureType.TRAVISCI;
    }

    public Integer getDuration() {
        return duration;
    }
}
