package ch.uzh.ciclassifier.helper;

import ch.uzh.ciclassifier.exception.ConfigurationMissingException;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class RepositoryTest {

    @Test
    public void constructorTest() throws IOException, GitAPIException {
        Repository repository = new Repository("https://github.com/tzemp/ciclassifier-sample-project.git");

        Assertions.assertNotNull(repository.getGit());
    }

    @Test
    public void getConfigurationTest() throws IOException, GitAPIException {
        Repository repository = new Repository("https://github.com/tzemp/ciclassifier-sample-project.git");
        String configuration = repository.getConfiguration();

        Assertions.assertNotNull(configuration);
    }

    @Test()
    public void missingConfigurationTest() throws IOException, GitAPIException {
        Repository repository = new Repository("https://github.com/tzemp/master-thesis-proposal.git");

        Exception exception = Assertions.assertThrows(ConfigurationMissingException.class, () -> {
            String configuration = repository.getConfiguration();
        });
    }
}