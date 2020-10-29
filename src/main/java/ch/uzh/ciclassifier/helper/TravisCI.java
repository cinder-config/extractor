package ch.uzh.ciclassifier.helper;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.net.URLEncoder;

public class TravisCI {
    private String api;

    public TravisCI(String api) {
        this.api = api;
    }

    public String getApi() {
        return api;
    }

    public Integer getBuildTime(String repository) {
        JSONObject request = TravisCIHelper.travisCall("repo/" + URLEncoder.encode(repository) + "/builds?state=passed&limit=1", "GET", "", this.getApi());

        if (request.get("@type").equals("builds")) {
            JSONObject latestBuild = (JSONObject) ((JSONArray) request.get("builds")).get(0);

            Long duration = (Long) latestBuild.get("duration");

            return duration != null ? duration.intValue() : null;
        }

        return 0;
    }

    public Integer getLinting(String configuration) {
        JSONObject result = TravisCIHelper.travisCall("lint", "POST", configuration, this.getApi());

        return ((JSONArray) result.get("warnings")).size();
    }

    public double getSuccessRatio(String repository) {
        JSONObject totalBuilds = TravisCIHelper.travisCall("repo/" + URLEncoder.encode(repository) + "/builds", "GET", "", this.getApi());
        JSONObject successfulBuilds = TravisCIHelper.travisCall("repo/" + URLEncoder.encode(repository) + "/builds?state=passed", "GET", "", this.getApi());

        if (null != totalBuilds && null != successfulBuilds) {
            Long successful = (Long) ((JSONObject) successfulBuilds.get("@pagination")).get("count");
            Long total = (Long) ((JSONObject) totalBuilds.get("@pagination")).get("count");

            return successful / (double) total;
        }
        return Double.parseDouble(null);
    }
}
