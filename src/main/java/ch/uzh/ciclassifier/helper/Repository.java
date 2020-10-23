package ch.uzh.ciclassifier.helper;

import ch.uzh.ciclassifier.exception.ConfigurationMissingException;
import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.revwalk.RevCommit;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;

public class Repository {
    private final String TEMP_STORAGE = "tmp/";
    private Git git = null;
    private File location = null;
    private String name = null;

    public Repository(String url) throws IOException {
        String repoName = url.replace("https://github.com/", "");
        if (repoName.endsWith(".git")) {
            repoName = repoName.substring(0, repoName.length() - 4);
        }
        if (!url.endsWith(".git")) {
            url = url + ".git";
        }
        this.name = repoName;
        String[] parts = repoName.split("/");
        String name = parts[1];
        this.location = new File(TEMP_STORAGE + name);
        Path pathToBeDeleted = Path.of(TEMP_STORAGE + name);

        if (this.location.exists()) {
            Files.walk(pathToBeDeleted)
                    .sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(File::delete);
            FileUtils.deleteDirectory(this.location);
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

    public Git getGit() {
        return git;
    }

    public String getName() {
        return this.name;
    }

    public String getConfiguration() throws IOException {

        File file = new File(this.location.getPath() + "/.travis.yml");
        if (!file.exists()) {
            throw new ConfigurationMissingException();
        }

        return Files.readString(Path.of(file.toURI()));
    }
}
