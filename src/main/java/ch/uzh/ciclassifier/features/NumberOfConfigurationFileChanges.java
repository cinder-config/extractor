package ch.uzh.ciclassifier.features;

import ch.uzh.ciclassifier.evaluation.Evaluation;
import ch.uzh.ciclassifier.helper.Repository;
import org.eclipse.jgit.api.Git;

import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;

public class NumberOfConfigurationFileChanges implements Feature {
    private Integer changes = null;

    @Override
    public void extract(Evaluation evaluation) throws IOException {
        this.changes = evaluation.getRepository().getNumberOfFileChanges(".travis.yml");
    }

    @Override
    public String getData() {
        return this.changes.toString();
    }

    @Override
    public FeatureType supportedFeatureType() {
        return FeatureType.REPOSITORY;
    }

    public Integer getChanges() {
        return changes;
    }
}
