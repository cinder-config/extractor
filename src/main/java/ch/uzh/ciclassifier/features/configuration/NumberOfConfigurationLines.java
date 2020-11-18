package ch.uzh.ciclassifier.features.configuration;

import ch.uzh.ciclassifier.evaluation.Evaluation;
import ch.uzh.ciclassifier.features.Feature;
import ch.uzh.ciclassifier.features.FeatureType;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumberOfConfigurationLines implements Feature {
    private Integer lines = null;

    @Override
    public void extract(Evaluation evaluation) {
        Matcher m = Pattern.compile("\r\n|\r|\n").matcher(evaluation.getRawConfiguration());
        int lines = 1;
        while (m.find()) {
            lines++;
        }
        this.lines = lines;
    }

    public Integer getLines() {
        return lines;
    }

    @Override
    public String getData() {
        return this.lines.toString();
    }

    @Override
    public FeatureType supportedFeatureType() {
        return FeatureType.CONFIGURATION;
    }
}
