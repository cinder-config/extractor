package ch.uzh.ciclassifier.helper;

import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.util.Map;

public class Configuration {
    private String raw;
    private Map<String, Object> parsed;

    public Configuration(String configuration) throws IOException {
        Yaml yaml = new Yaml();
        this.parsed = yaml.load(configuration);
        this.raw = configuration;
    }

    public Map<String, Object> getParsed() {
        return parsed;
    }

    public void setRaw(String raw) {
        this.raw = raw;
    }
}
