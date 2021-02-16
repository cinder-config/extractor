package ch.uzh.ciclassifier.features.repository;

import ch.uzh.ciclassifier.evaluation.Evaluation;
import ch.uzh.ciclassifier.features.Feature;
import ch.uzh.ciclassifier.features.FeatureType;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.revwalk.RevCommit;

import java.io.IOException;

public class DaysUntilConfigAdded implements Feature {
    private Integer days = null;

    @Override
    public void extract(Evaluation evaluation) throws IOException {
        Git git = evaluation.getRepository().getGit();
        try {
            // Get First commit which reference .travis.yml File
            RevCommit lastConfigCommit = null;
            Iterable<RevCommit> configLogs = git.log().addPath(".travis.yml").call();
            for (RevCommit revCommit : configLogs) {
                // Loop over to get the  last commit (since sorting is not possible with LogCommand)
                lastConfigCommit = revCommit;
            }

            // Get first commit of repository

            int configAddedTime = lastConfigCommit.getCommitTime();

            RevCommit lastCommit = null;
            Iterable<RevCommit> logs = git.log().call();
            for (RevCommit revCommit : logs) {
                lastCommit = revCommit;
            }

            int firstCommitTime = lastCommit.getCommitTime();
            int diff = configAddedTime - firstCommitTime;

            this.days = Math.round(diff / 60f / 60f / 24f);
        } catch (Exception e) {
            this.days = 0;
        }
    }

    @Override
    public String getData() {
        return this.days.toString();
    }

    @Override
    public FeatureType supportedFeatureType() {
        return FeatureType.REPOSITORY;
    }
}
