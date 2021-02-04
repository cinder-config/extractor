package ch.uzh.ciclassifier.features.travisci;

import ch.uzh.ciclassifier.evaluation.Evaluation;
import ch.uzh.ciclassifier.features.Feature;
import ch.uzh.ciclassifier.features.FeatureType;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.OptionalDouble;

public class TimeToFixAverage implements Feature {
    private Double time;

    @Override
    public void extract(Evaluation evaluation) throws IOException {
        List<Long> differences = new ArrayList<>();

        List<JSONObject> builds = evaluation.getTravisCI().getBuilds();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        JSONObject failedBuild = null;

        // Backwards loop through the build
        for (int i = builds.size(); i-- > 0; ) {
            JSONObject build = builds.get(i);
            String state = (String) build.get("state");
            if ((state.equals("failed") || state.equals("errored")) && failedBuild == null) {
                failedBuild = build;
                continue;
            }
            if (state.equals("passed") && failedBuild != null) {
                try {
                    Date dateFailedBuild = dateFormat.parse((String) failedBuild.get("finished_at"));
                    Date datePassedBuild = dateFormat.parse((String) build.get("finished_at"));
                    Long seconds = (datePassedBuild.getTime() - dateFailedBuild.getTime()) / 1000;
                    differences.add(seconds);

                } catch (Exception e) {
                    e.printStackTrace();

                } finally {
                    failedBuild = null;
                }
            }
        }

        OptionalDouble average = differences
                .stream()
                .mapToDouble(a -> a)
                .average();

        this.time = average.orElse(0.0);
    }

    @Override
    public String getData() {
        return this.time.toString();
    }

    @Override
    public FeatureType supportedFeatureType() {
        return FeatureType.TRAVISCI;
    }
}
