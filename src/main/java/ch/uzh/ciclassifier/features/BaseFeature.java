package ch.uzh.ciclassifier.features;

import java.io.IOException;

public interface BaseFeature {
    public void extract(String configPath) throws IOException;
}
