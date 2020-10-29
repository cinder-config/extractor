package ch.uzh.ciclassifier.features;

import ch.uzh.ciclassifier.evaluation.Evaluation;
import ch.uzh.ciclassifier.helper.Configuration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class EnvSize implements Feature {
    private Integer size;

    @Override
    public void extract(Evaluation evaluation) throws IOException {
        Configuration configuration = evaluation.getConfiguration();

        this.size = 0;
        if (!configuration.getParsed().containsKey("env")) {
            return;
        }

        if (configuration.getParsed().get("env") instanceof ArrayList) {
            this.size = ((ArrayList) configuration.getParsed().get("env")).size();
            return;
        }
        LinkedHashMap env = (LinkedHashMap) configuration.getParsed().get("env");

        if (env.containsKey("global")) {
            this.size += ((ArrayList) env.get("global")).size();
        }

        if (env.containsKey("jobs")) {
            this.size += ((ArrayList) env.get("jobs")).size();
        }
    }

    @Override
    public String getData() {
        return size.toString();
    }

    @Override
    public FeatureType supportedFeatureType() {
        return FeatureType.CONFIGURATION;
    }

    public Integer getSize() {
        return size;
    }
}
