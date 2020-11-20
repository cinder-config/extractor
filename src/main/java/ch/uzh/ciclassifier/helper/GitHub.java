package ch.uzh.ciclassifier.helper;

import org.json.simple.JSONObject;

public class GitHub {
    private JSONObject data = null;

    public GitHub(JSONObject data) {
        this.data = data;
    }

    public JSONObject getData() {
        return data;
    }

    public String getName() {
        return (String) this.data.get("full_name");
    }

    public String getDefaultBranch() {
        return (String) this.data.get("default_branch");
    }
}