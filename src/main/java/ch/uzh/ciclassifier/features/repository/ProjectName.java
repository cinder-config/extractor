package ch.uzh.ciclassifier.features.repository;

import ch.uzh.ciclassifier.evaluation.Evaluation;
import ch.uzh.ciclassifier.features.Feature;
import ch.uzh.ciclassifier.features.FeatureType;

import java.io.IOException;

public class ProjectName implements Feature {
    private String name = null;

    @Override
    public void extract(Evaluation evaluation) throws IOException {
        this.name = evaluation.getRepository().getName();
    }

    @Override
    public String getData() {
        return this.name;
    }

    @Override
    public FeatureType supportedFeatureType() {
        return FeatureType.REPOSITORY;
    }
}
