package ch.uzh.ciclassifier.features;

import ch.uzh.ciclassifier.helper.Repository;
import org.eclipse.jgit.api.Git;

import java.io.FileReader;
import java.io.LineNumberReader;

public class NumberOfConfigurationFileChanges {
    private Integer changes = null;

    public void extract(Repository repository) {
        this.changes = repository.getNumberOfFileChanges(".travis.yml");
    }

    public Integer getChanges() {
        return changes;
    }
}
