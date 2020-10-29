package ch.uzh.ciclassifier.features;

import ch.uzh.ciclassifier.evaluation.Evaluation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumberOfConfigurationChars implements Feature {
    private Integer chars = null;

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
