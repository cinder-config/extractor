package ch.uzh.ciclassifier.features.configuration;

import ch.uzh.ciclassifier.evaluation.Evaluation;
import ch.uzh.ciclassifier.features.Feature;
import ch.uzh.ciclassifier.features.FeatureType;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;

public class NumberOfNotifications implements Feature {
    private Integer size = 0;

    @Override
    public void extract(Evaluation evaluation) throws IOException {
        JSONObject parsed = evaluation.getConfiguration().getParsed();
        JSONObject config = (JSONObject) parsed.get("config");
        String[] notificationTypes = {"campfire", "email", "flowdock", "hipchat", "irc", "pushover", "slack", "webhooks"};

        if (config.containsKey("notifications")) {
            JSONObject notifications = (JSONObject) config.get("notifications");
            for (String type : notificationTypes) {
                JSONArray entries = (JSONArray) notifications.get(type);
                if (null != entries) {
                    for (Object value : entries) {
                        JSONObject entr = (JSONObject) value;
                        if (entr.containsKey("enabled")) {
                            if (!((Boolean) entr.get("enabled"))) {
                            } else {
                                this.size++;
                            }
                        } else {
                            this.size++;
                        }
                    }
                }
            }
        }
    }


    @Override
    public String getData() {
        return this.size.toString();
    }

    @Override
    public FeatureType supportedFeatureType() {
        return FeatureType.CONFIGURATION;
    }

    public Integer getSize() {
        return size;
    }
}
