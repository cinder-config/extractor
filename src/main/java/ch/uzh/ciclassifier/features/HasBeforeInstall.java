package ch.uzh.ciclassifier.features;

import ch.uzh.ciclassifier.helper.Configuration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Set;

public class HasBeforeInstall implements BaseFeature {
    private boolean beforeInstall = false;

    @Override
    public void extract(String configPath) throws IOException {
        Configuration configuration = new Configuration(configPath);

        ArrayList beforeInstall = (ArrayList) configuration.getConfiguration().get("before_install");

        if (null != beforeInstall) {
            this.beforeInstall = true;
        }
    }

    public boolean hasBeforeInstall() {
        return beforeInstall;
    }
}
