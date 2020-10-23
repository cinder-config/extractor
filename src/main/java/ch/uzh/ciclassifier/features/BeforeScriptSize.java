package ch.uzh.ciclassifier.features;

import ch.uzh.ciclassifier.helper.Configuration;

import java.io.IOException;
import java.util.ArrayList;

public class BeforeScriptSize implements BaseFeature {
    private Integer size;

    @Override
    public void extract(String configPath) throws IOException {
        Configuration configuration = new Configuration(configPath);

        ArrayList beforeScript = (ArrayList) configuration.getParsed().get("before_script");

        if (null != beforeScript) {
            this.size = beforeScript.size();
        } else {
            this.size = 0;
        }
    }

    public Integer getSize() {
        return size;
    }
}
