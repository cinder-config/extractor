package ch.uzh.ciclassifier.features.configuration;

import ch.uzh.ciclassifier.evaluation.Evaluation;
import ch.uzh.ciclassifier.features.Feature;
import ch.uzh.ciclassifier.features.FeatureType;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class NumberOfComments implements Feature {
    private Integer amount = 0;

    @Override
    public void extract(Evaluation evaluation) throws IOException {
        String[] lines = evaluation.getRawConfiguration().split("\\r?\\n");
        List<Character> escapeChars = Arrays.asList('"', '\'');

        for (String line : lines) {
            boolean metEscapeChar = false;
            Character escapeChar = null;
            for (Character c : line.toCharArray()) {
                if (escapeChars.contains(c)) {
                    if (metEscapeChar && escapeChar.equals(c)) {
                        metEscapeChar = false;
                    } else {
                        metEscapeChar = true;
                        escapeChar = c;
                    }
                }

                if (!metEscapeChar && c == '#') {
                    this.amount++;
                    break;
                }
            }
        }
    }

    @Override
    public String getData() {
        return this.amount.toString();
    }

    @Override
    public FeatureType supportedFeatureType() {
        return FeatureType.CONFIGURATION;
    }
}
