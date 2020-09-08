package ch.uzh.ciclassifier.features;

import java.io.*;

public class NumberOfConfigurationLines implements BaseFeature {
    private Integer lines = null;

    @Override
    public void extract(String configPath) {
        try (LineNumberReader reader = new LineNumberReader(new FileReader(configPath))) {
            reader.skip(Integer.MAX_VALUE);
            this.lines = reader.getLineNumber() + 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Integer getLines() {
        return lines;
    }
}
