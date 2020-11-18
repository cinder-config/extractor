package ch.uzh.ciclassifier.features.repository;

import ch.uzh.ciclassifier.evaluation.Evaluation;
import ch.uzh.ciclassifier.features.Feature;
import ch.uzh.ciclassifier.features.FeatureType;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.IOException;

public class ConfigChangeFrequency implements Feature {
    private Float changeFrequency = null;

    @Override
    public void extract(Evaluation evaluation) throws IOException {
        try {
            Integer commits = evaluation.getRepository().getNumberOfCommits();
            Integer changes = evaluation.getRepository().getNumberOfFileChanges(".travis.yml");

            this.changeFrequency = (float) changes / commits;
        } catch (GitAPIException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getData() {
        return this.changeFrequency.toString();
    }

    @Override
    public FeatureType supportedFeatureType() {
        return FeatureType.REPOSITORY;
    }
}
