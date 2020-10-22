package ch.uzh.ciclassifier.helper;

import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class Configuration {
    private Map<String, Object> configuration;

    public Configuration(String path) throws IOException {
        Yaml yaml = new Yaml();
        InputStream input = new FileInputStream(new File(path));

        this.configuration = yaml.load(input);
    }

    public Map<String, Object> getConfiguration() {
        return configuration;
    }
}
