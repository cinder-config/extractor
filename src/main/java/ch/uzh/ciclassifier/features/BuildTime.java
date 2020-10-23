package ch.uzh.ciclassifier.features;

import ch.uzh.ciclassifier.evaluation.Evaluation;
import ch.uzh.ciclassifier.helper.TravisCI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class BuildTime implements Feature {
    private Integer duration;


    @Override
    public void extract(Evaluation evaluation) throws IOException {
        this.duration = TravisCI.getBuildTime(evaluation.getRepository().getName());
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
