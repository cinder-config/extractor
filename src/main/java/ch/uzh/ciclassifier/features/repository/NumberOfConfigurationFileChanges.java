package ch.uzh.ciclassifier.features.repository;

import ch.uzh.ciclassifier.evaluation.Evaluation;
import ch.uzh.ciclassifier.features.Feature;
import ch.uzh.ciclassifier.features.FeatureType;

import java.io.IOException;

public class NumberOfConfigurationFileChanges implements Feature {
    private Integer changes = 0;

    @Override
    public void extract(Evaluation evaluation) throws IOException {
        this.changes = evaluation.getRepository().getNumberOfFileChanges(".travis.yml");
    }

    @Override
    public String getData() {
        return this.changes.toString();
    }

    @Override
    public FeatureType supportedFeatureType() {
        return FeatureType.REPOSITORY;
    }

    public Integer getChanges() {
        return changes;
    }
}
