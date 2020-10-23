package ch.uzh.ciclassifier.features;

import ch.uzh.ciclassifier.evaluation.Evaluation;
import ch.uzh.ciclassifier.helper.Configuration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Set;

public class NumberOfJobs implements Feature {
    private Integer stages = null;

    @Override
    public void extract(Evaluation evaluation) throws IOException {
        LinkedHashMap jobs = (LinkedHashMap) evaluation.getConfiguration().getParsed().get("jobs");
        String previousStage = null;
        int counter = 0;
        if (null != jobs) {
            ArrayList include = (ArrayList) jobs.get("include");
            if (null != include) {
                for (Object i: include) {
                    LinkedHashMap el = (LinkedHashMap) i;
                    String stage = (String) el.get("stage");
                    if (null != stage) {
                        counter++;
                        previousStage = stage;
                    } else {
                        if (previousStage == null) {
                            counter++;
                        }
                    }
                }
            }
            this.stages = counter;
        } else {
            // This is default
            this.stages = 1;
        }
    }

    @Override
    public String getData() {
        return this.getStages().toString();
    }

    @Override
    public FeatureType supportedFeatureType() {
        return FeatureType.CONFIGURATION;
    }


    private Integer getStages() {
        return stages;
    }
}
