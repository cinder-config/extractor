package ch.uzh.ciclassifier.helper;

import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.revwalk.RevCommit;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;

public class Repository {
    private final String TEMP_STORAGE = "tmp/";
    public File location;
    public Git git;

    public Repository(String name, String url) throws IOException {
        File location = new File(TEMP_STORAGE + name);
        Path pathToBeDeleted = Path.of(TEMP_STORAGE + name);

        Files.walk(pathToBeDeleted)
                .sorted(Comparator.reverseOrder())
                .map(Path::toFile)
                .forEach(File::delete);

        this.location = location;
        if (location.exists()) {
            FileUtils.deleteDirectory(location);
        }
        try {
            this.git = Git.cloneRepository()
                    .setURI(url)
                    .setDirectory(this.location)
                    .call();
        } catch (GitAPIException e) {
            e.printStackTrace();
        }
    }

    public int getNumberOfFileChanges(String file) {
        try {
            Iterable<RevCommit> logs = git.log().addPath(file).call();
            int count = 0;
            for (RevCommit revCommit : logs) {
                count++;
            }
            return count;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
