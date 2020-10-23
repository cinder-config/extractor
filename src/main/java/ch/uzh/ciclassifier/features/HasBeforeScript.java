package ch.uzh.ciclassifier.features;

import ch.uzh.ciclassifier.helper.Configuration;

import java.io.IOException;
import java.util.ArrayList;

public class HasBeforeScript implements BaseFeature {
    private boolean beforeScript = false;

    @Override
    public void extract(String configPath) throws IOException {
        Configuration configuration = new Configuration(configPath);

        ArrayList beforeScript = (ArrayList) configuration.getParsed().get("before_script");

        if (null != beforeScript) {
            this.beforeScript = true;
        }
    }

    public boolean hasBeforeScript() {
        return beforeScript;
    }
}
