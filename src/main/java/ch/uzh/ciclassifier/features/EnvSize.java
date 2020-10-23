package ch.uzh.ciclassifier.features;

import ch.uzh.ciclassifier.evaluation.Evaluation;
import ch.uzh.ciclassifier.helper.Configuration;
import ch.uzh.ciclassifier.helper.TravisCI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class EnvSize implements Feature {
    private Integer size;

    @Override
    public void extract(Evaluation evaluation) throws IOException {
        Configuration configuration = evaluation.getConfiguration();

        if (!configuration.getParsed().containsKey("env")) {
            this.size = 0;
            return;
        }

        LinkedHashMap env = (LinkedHashMap) configuration.getParsed().get("env");
        int counter = 0;

        if (env.containsKey("global")) {
            counter += ((ArrayList) env.get("global")).size();
        }

        if (env.containsKey("jobs")) {
            counter += ((ArrayList) env.get("jobs")).size();
        }

        this.size = counter;
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
