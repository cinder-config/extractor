package ch.uzh.ciclassifier.features;

import ch.uzh.ciclassifier.helper.Repository;
import ch.uzh.ciclassifier.helper.TravisCI;

public class BuildSuccessRatio {
    private Double ratio;

    public void extract(String repository) {
        this.ratio = TravisCI.getSuccessRatio(repository);
    }

    public Double getRatio() {
        return ratio;
    }
}
