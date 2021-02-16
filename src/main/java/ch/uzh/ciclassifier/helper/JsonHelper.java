package ch.uzh.ciclassifier.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;

public class JsonHelper {

    public static JsonNode toJsonNode(JSONObject jsonObject) {
        // Parse a JsonObject into a JSON string
        String json = jsonObject.toJSONString();

        // Parse a JSON string into a JsonNode
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = null;
        try {
            jsonNode = objectMapper.readTree(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return jsonNode;
    }
}
