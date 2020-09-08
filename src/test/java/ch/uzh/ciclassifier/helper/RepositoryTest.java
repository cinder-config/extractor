package ch.uzh.ciclassifier.helper;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RepositoryTest {

    @Test
    public void constructorTest() throws IOException {
        Repository repository = new Repository("sale-workflow","https://github.com/OCA/sale-workflow.git");

        assertTrue(repository.location.exists());
    }
}