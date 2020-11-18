package ch.uzh.ciclassifier.features.repository;

import ch.uzh.ciclassifier.evaluation.Evaluation;
import ch.uzh.ciclassifier.features.Feature;
import ch.uzh.ciclassifier.features.FeatureType;

import java.io.IOException;

public class NumberOfContributors implements Feature {
    private Integer contributors = null;

    @Override
    public void extract(Evaluation evaluation) throws IOException {
        try {
            this.contributors = evaluation.getRepository().getNumberOfContributors();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getData() {
        return this.contributors.toString();
    }

    @Override
    public FeatureType supportedFeatureType() {
        return FeatureType.REPOSITORY;
    }
}
