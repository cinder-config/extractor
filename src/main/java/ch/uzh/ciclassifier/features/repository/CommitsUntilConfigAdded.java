package ch.uzh.ciclassifier.features.repository;

import ch.uzh.ciclassifier.evaluation.Evaluation;
import ch.uzh.ciclassifier.features.Feature;
import ch.uzh.ciclassifier.features.FeatureType;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.revwalk.RevCommit;

import java.io.IOException;

public class CommitsUntilConfigAdded implements Feature {
    private Integer counter = 0;

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
            boolean foundLastConfigCommit = false;

            Iterable<RevCommit> logs = git.log().call();
            for (RevCommit revCommit : logs) {
                if (foundLastConfigCommit) {
                    counter++;
                }
                if (lastConfigCommit.toString().equals(revCommit.toString())) {
                    foundLastConfigCommit = true;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getData() {
        return this.counter.toString();
    }

    @Override
    public FeatureType supportedFeatureType() {
        return FeatureType.REPOSITORY;
    }
}
