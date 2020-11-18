package ch.uzh.ciclassifier.helper;

import io.github.cdimascio.dotenv.Dotenv;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class GitHubHelper {

    public static final String API = "https://api.github.com/";

    public static JSONObject call(String endpoint, String type, String requestBody) {
        CloseableHttpClient httpClient = HttpClients.createDefault();

        try {
            HttpRequestBase request = GitHubHelper.prepare(endpoint, type, requestBody);

            CloseableHttpResponse response = httpClient.execute(request);
            HttpEntity entity = response.getEntity();

            JSONParser parser = new JSONParser();

            return (JSONObject) parser.parse(EntityUtils.toString(entity));

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static HttpRequestBase prepare(String endpoint, String type, String requestBody) throws UnsupportedEncodingException {
        Dotenv dotenv = Dotenv.load();

        if (type.equals("GET")) {
            HttpGet request = new HttpGet(GitHubHelper.API + endpoint);

            // add request headers
            request.addHeader("Authorization", "token " + dotenv.get("GITHUB_API_TOKEN"));

            return request;
        } else {
            HttpPost request = new HttpPost(GitHubHelper.API + endpoint);
            request.setEntity(new StringEntity(requestBody));

            // add request headers
            request.addHeader("Authorization", "token " + dotenv.get("GITHUB_API_TOKEN"));

            return request;
        }
    }

    public static JSONObject getRepositoryData(String name) {
        return GitHubHelper.call("repos/" + name, "GET", null);
    }
}
