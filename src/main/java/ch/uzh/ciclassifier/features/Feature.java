package ch.uzh.ciclassifier.features;

import ch.uzh.ciclassifier.evaluation.Evaluation;

import java.io.IOException;

public interface Feature {
    public void extract(Evaluation evaluation) throws IOException;

    public String getData();

    public FeatureType supportedFeatureType();
}
