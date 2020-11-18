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
import java.util.List;
import java.util.Set;

public class UniqueInstructions implements Feature {
    private Integer size = null;

    @Override
    public void extract(Evaluation evaluation) throws IOException {
        JSONArray matrix = evaluation.getConfiguration().getMatrix();
        Set<String> instructions = new HashSet<>();
        String[] types = {"before_install", "install", "before_script", "script","before_cache"};

        for (Object o : matrix) {
            JSONObject job = (JSONObject) o;
            for (String type : types) {
                JSONArray el = (JSONArray) job.get(type);
                if (null != el) {
                    for (Object value : el) {
                        instructions.add((String) value);
                    }
                }
            }
        }

        this.size = instructions.size();
    }

    @Override
    public String getData() {
        return this.getSize().toString();
    }

    @Override
    public FeatureType supportedFeatureType() {
        return FeatureType.CONFIGURATION;
    }


    public Integer getSize() {
        return size;
    }
}
