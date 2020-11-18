package ch.uzh.ciclassifier.features.github;

import ch.uzh.ciclassifier.evaluation.Evaluation;
import ch.uzh.ciclassifier.features.Feature;
import ch.uzh.ciclassifier.features.FeatureType;

import java.io.IOException;

public class PrimaryLanguage implements Feature {
    private String language = null;

    @Override
    public void extract(Evaluation evaluation) throws IOException {
        if (evaluation.getGitHub().getData().containsKey("language")) {
            this.language = (String) evaluation.getGitHub().getData().get("language");
        }
    }

    @Override
    public String getData() {
        return this.language;
    }

    @Override
    public FeatureType supportedFeatureType() {
        return FeatureType.GITHUB;
    }
}
