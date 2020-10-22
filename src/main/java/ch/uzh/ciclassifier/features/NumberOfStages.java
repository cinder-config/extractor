package ch.uzh.ciclassifier.features;

import ch.uzh.ciclassifier.helper.Configuration;

import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Set;

public class NumberOfStages implements BaseFeature {
    private Integer stages = null;

    @Override
    public void extract(String configPath) throws IOException {
        Configuration configuration = new Configuration(configPath);
        Set<String> stages = new HashSet<String>();

        LinkedHashMap jobs = (LinkedHashMap) configuration.getConfiguration().get("jobs");
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

        System.out.println(configuration.getConfiguration());
    }

    public Integer getStages() {
        return stages;
    }
}
