package ch.uzh.ciclassifier.features;

import ch.uzh.ciclassifier.evaluation.Evaluation;
import ch.uzh.ciclassifier.helper.TravisCI;

import java.io.IOException;

public class LintScore implements Feature {
    private Integer score;

    public Integer getScore() {
        return score;
    }

    @Override
    public void extract(Evaluation evaluation) throws IOException {
        this.score = TravisCI.getLinting(evaluation.getRawConfiguration());
    }

    @Override
    public String getData() {
        return this.score.toString();
    }

    @Override
    public FeatureType supportedFeatureType() {
        return FeatureType.CONFIGURATION;
    }
}
