package ch.uzh.ciclassifier.helper;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class TravisCI {
    private String api;
    private List<JSONObject> builds = new ArrayList<>();
    private GitHub gitHub;

    public TravisCI(String api, GitHub gitHub) {
        this.api = api;
        this.gitHub = gitHub;
    }

    public String getApi() {
        return api;
    }

    public GitHub getGitHub() {
        return gitHub;
    }

    public Integer getBuildTime(String repository) {
        JSONObject request = TravisCIHelper.travisCall("repo/" + URLEncoder.encode(repository) + "/builds?state=passed&limit=1", "GET", "", this.getApi());

        if (request.get("@type").equals("builds")) {
            try {
                JSONObject latestBuild = (JSONObject) ((JSONArray) request.get("builds")).get(0);

                Long duration = (Long) latestBuild.get("duration");

                return duration != null ? duration.intValue() : null;
            } catch (IndexOutOfBoundsException ignored) {
            }
        }

        return 0;
    }

    public Integer getLinting(String configuration) {
        JSONObject result = TravisCIHelper.travisCall("lint", "POST", configuration, this.getApi());

        try {
            return ((JSONArray) result.get("warnings")).size();
        } catch (Exception e) {
            return null;
        }
    }

    public Double getSuccessRatio(String repository) {
        try {
            JSONObject totalBuilds = TravisCIHelper.travisCall("repo/" + URLEncoder.encode(repository, StandardCharsets.UTF_8) + "/builds", "GET", "", this.getApi());
            JSONObject successfulBuilds = TravisCIHelper.travisCall("repo/" + URLEncoder.encode(repository, StandardCharsets.UTF_8) + "/builds?state=passed", "GET", "", this.getApi());

            if (null != totalBuilds && null != successfulBuilds) {
                Long successful = (Long) ((JSONObject) successfulBuilds.get("@pagination")).get("count");
                Long total = (Long) ((JSONObject) totalBuilds.get("@pagination")).get("count");

                return successful / (double) total;
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<JSONObject> getBuilds() {
        if (!builds.isEmpty()) {
            return this.builds;
        }
        try {
            JSONObject travisResponse;
            String next = "repo/" + URLEncoder.encode(this.getGitHub().getName(), StandardCharsets.UTF_8) + "/builds?branch.name=" + URLEncoder.encode(this.getGitHub().getDefaultBranch(), StandardCharsets.UTF_8);

            while (next != null) {
                travisResponse = TravisCIHelper.travisCall(next, "GET", "", this.getApi());
                JSONArray tmpBuilds = (JSONArray) travisResponse.get("builds");
                for (Object tmpBuild : tmpBuilds) {
                    this.builds.add((JSONObject) tmpBuild);
                }
                JSONObject pagination = (JSONObject) travisResponse.get("@pagination");
                JSONObject nextObject = (JSONObject) pagination.get("next");
                if (null != nextObject) {
                    next = (String) nextObject.get("@href");
                } else {
                    next = null;
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return this.builds;
    }
}
