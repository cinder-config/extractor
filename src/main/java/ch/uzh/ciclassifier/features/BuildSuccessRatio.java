package ch.uzh.ciclassifier.features;

import ch.uzh.ciclassifier.evaluation.Evaluation;
import ch.uzh.ciclassifier.helper.Repository;
import ch.uzh.ciclassifier.helper.TravisCI;

import java.io.IOException;

public class BuildSuccessRatio implements Feature {
    private Double ratio;

    public void extract(String repository) {
        this.ratio = TravisCI.getSuccessRatio(repository);
    }

    public Double getRatio() {
        return ratio;
    }

    @Override
    public void extract(Evaluation evaluation) throws IOException {
        this.ratio = TravisCI.getSuccessRatio(evaluation.getRepository().getName());
    }

    @Override
    public String getData() {
        return this.ratio.toString();
    }

    @Override
    public FeatureType supportedFeatureType() {
        return FeatureType.TRAVISCI;
    }
}
