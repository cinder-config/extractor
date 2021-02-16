package ch.uzh.ciclassifier.features.configuration;

import ch.uzh.ciclassifier.evaluation.Evaluation;
import ch.uzh.ciclassifier.features.Feature;
import ch.uzh.ciclassifier.features.FeatureType;
import ch.uzh.ciclassifier.helper.JsonHelper;
import ch.uzh.ciclassifier.helper.TravisYmlHelper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.diff.JsonDiff;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class TemplateSimilarity implements Feature {
    private String TEMPLATE_FOLDER = "src/main/java/ch/uzh/ciclassifier/templates/";

    private Integer differences = 0;

    @Override
    public void extract(Evaluation evaluation) throws IOException {
        try {
            JsonNode config = JsonHelper.toJsonNode((JSONObject) evaluation.getConfiguration().getParsed().get("config"));
            JsonNode template = this.getTemplateConfig(config.get("language").asText().toLowerCase());

            JsonPatch patch = JsonDiff.asJsonPatch(config, template);

            this.differences = patch.getOperations().size();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getData() {
        return differences.toString();
    }

    @Override
    public FeatureType supportedFeatureType() {
        return FeatureType.CONFIGURATION;
    }

    public Integer getDifferences() {
        return differences;
    }

    public JsonNode getTemplateConfig(String language) throws IOException, ParseException {
        language = language.toLowerCase();
        String template = Files.readString(Path.of(TEMPLATE_FOLDER + language + ".yml"));

        JSONObject parsed = TravisYmlHelper.parse(template);

        return JsonHelper.toJsonNode((JSONObject) parsed.get("config"));
    }
}
