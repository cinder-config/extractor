package ch.uzh.ciclassifier.features.configuration;

import ch.uzh.ciclassifier.evaluation.Evaluation;
import ch.uzh.ciclassifier.features.Feature;
import ch.uzh.ciclassifier.features.FeatureType;

public class NumberOfConfigurationChars implements Feature {
    private Integer chars = 0;

    @Override
    public void extract(Evaluation evaluation) {
        String cleaned = evaluation.getRawConfiguration().replaceAll("\n|\r","");
        this.chars = cleaned.length();
    }

    public Integer getChars() {
        return chars;
    }

    @Override
    public String getData() {
        return this.chars.toString();
    }

    @Override
    public FeatureType supportedFeatureType() {
        return FeatureType.CONFIGURATION;
    }
}
