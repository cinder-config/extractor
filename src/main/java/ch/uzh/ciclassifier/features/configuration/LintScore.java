package ch.uzh.ciclassifier.features.configuration;

import ch.uzh.ciclassifier.evaluation.Evaluation;
import ch.uzh.ciclassifier.features.Feature;
import ch.uzh.ciclassifier.features.FeatureType;
import ch.uzh.ciclassifier.helper.GitHub;
import ch.uzh.ciclassifier.helper.TravisCI;
import ch.uzh.ciclassifier.helper.TravisCIHelper;
import org.json.simple.JSONObject;

import java.io.IOException;

public class LintScore implements Feature {
    private Integer score;

    public Integer getScore() {
        return score;
    }

    @Override
    public void extract(Evaluation evaluation) throws IOException {
        TravisCI tempTravisCI = new TravisCI(TravisCIHelper.API_COM, new GitHub(new JSONObject()));
        this.score = tempTravisCI.getLinting(evaluation.getRawConfiguration());
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
