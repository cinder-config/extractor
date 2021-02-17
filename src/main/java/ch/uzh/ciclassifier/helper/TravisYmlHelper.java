package ch.uzh.ciclassifier.helper;

import io.github.cdimascio.dotenv.Dotenv;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class TravisYmlHelper {

    public static final String API = "http://127.0.0.1:9292";

    public static JSONObject parse(String configuration) throws IOException, ParseException {
        Dotenv dotenv = Dotenv.load();

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost request = new HttpPost(dotenv.get("TRAVIS_YML_API_URL") + "/v1/parse");
        request.setEntity(new StringEntity(configuration));

        CloseableHttpResponse response = httpClient.execute(request);
        HttpEntity entity = response.getEntity();

        JSONParser parser = new JSONParser();
        return (JSONObject) parser.parse(EntityUtils.toString(entity));
    }

    public static JSONArray expand(JSONObject configuration) throws IOException, ParseException {
        Dotenv dotenv = Dotenv.load();

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost request = new HttpPost(dotenv.get("TRAVIS_YML_API_URL") + "/v1/expand");
        request.setEntity(new StringEntity(configuration.toJSONString()));

        CloseableHttpResponse response = httpClient.execute(request);
        HttpEntity entity = response.getEntity();

        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(EntityUtils.toString(entity));

        return (JSONArray) jsonObject.get("matrix");
    }
}
