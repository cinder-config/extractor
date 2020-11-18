package ch.uzh.ciclassifier.features.configuration;

import ch.uzh.ciclassifier.evaluation.Evaluation;
import ch.uzh.ciclassifier.features.Feature;
import ch.uzh.ciclassifier.features.FeatureType;
import ch.uzh.ciclassifier.helper.Configuration;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Set;

public class EnvSize implements Feature {
    private Integer size;

    @Override
    public void extract(Evaluation evaluation) throws IOException {
        this.size = 0;

        Set<String> envs = new HashSet<String>();
        Configuration configuration = evaluation.getConfiguration();
        JSONArray matrix = configuration.getMatrix();

        for (int i = 0; i < matrix.size(); i++) {
            JSONObject job = (JSONObject) configuration.getMatrix().get(i);
            JSONArray env = (JSONArray) job.get("env");
            if (env != null) {
                for (int j = 0; j < env.size(); j++) {
                    JSONObject envEntry = (JSONObject) env.get(j);
                    envEntry.keySet().forEach(keyStr -> {
                        envs.add((String) keyStr);
                    });
                }
            }
        }
        this.size = envs.size();
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
