package ch.uzh.ciclassifier.features;

import ch.uzh.ciclassifier.evaluation.Evaluation;
import ch.uzh.ciclassifier.helper.TravisCIHelper;

import java.io.IOException;

public class BuildSuccessRatio implements Feature {
    private Double ratio;

    public Double getRatio() {
        return ratio;
    }

    @Override
    public void extract(Evaluation evaluation) throws IOException {
        this.ratio = evaluation.getTravisCI().getSuccessRatio(evaluation.getRepository().getName());
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
