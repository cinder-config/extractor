package ch.uzh.ciclassifier.features;

import ch.uzh.ciclassifier.helper.Configuration;

import java.io.IOException;
import java.util.ArrayList;

public class HasBeforeInstall implements BaseFeature {
    private boolean beforeInstall = false;

    @Override
    public void extract(String configPath) throws Exception {
        Configuration configuration = new Configuration(configPath);

        ArrayList beforeInstall = (ArrayList) configuration.getParsed().get("before_install");

        if (null != beforeInstall) {
            this.beforeInstall = true;
        }
    }

    public boolean hasBeforeInstall() {
        return beforeInstall;
    }
}
