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
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class TravisCI {

    private static final String API_URL = "https://api.travis-ci.org/";
    private static final String ALTERNATIVE_API_URL = "https://api.travis-ci.com/";

    public static double getSuccessRatio(String repository) {
        JSONObject totalBuilds = TravisCI.travisCall("repo/" + URLEncoder.encode(repository) + "/builds", "GET", "", null);
        JSONObject successfulBuilds = TravisCI.travisCall("repo/" + URLEncoder.encode(repository) + "/builds?state=passed", "GET", "", null);

        if (null != totalBuilds && null != successfulBuilds) {
            Long successful = (Long) ((JSONObject) successfulBuilds.get("@pagination")).get("count");
            Long total = (Long) ((JSONObject) totalBuilds.get("@pagination")).get("count");

            return successful / (double) total;
        }
        return Double.parseDouble(null);
    }

    public static JSONObject travisCall(String endpoint, String type, String requestBody, String api) {
        if (api == null) {
            api = API_URL;
        }
        Dotenv dotenv = Dotenv.load();

        CloseableHttpClient httpClient = HttpClients.createDefault();

        try {
            HttpRequestBase request = TravisCI.prepare(api,endpoint,type,requestBody);

            CloseableHttpResponse response = httpClient.execute(request);
            HttpEntity entity = response.getEntity();

            JSONParser parser = new JSONParser();

            JSONObject result = (JSONObject) parser.parse(EntityUtils.toString(entity));

            if (result.get("@type").equals("error") && !api.equals(ALTERNATIVE_API_URL)) {
                return TravisCI.travisCall(endpoint, type, requestBody, ALTERNATIVE_API_URL);
            }

            return result;

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static HttpRequestBase prepare(String api, String endpoint, String type, String requestBody) throws UnsupportedEncodingException {
        Dotenv dotenv = Dotenv.load();

        if (type.equals("GET")) {
            HttpGet request = new HttpGet(api + endpoint);

            // add request headers
            String token = api.equals(API_URL) ? dotenv.get("TRAVIS_CI_ORG_API_TOKEN") : dotenv.get("TRAVIS_CI_COM_API_TOKEN");
            request.addHeader("Authorization", "token " + token);
            request.addHeader("Travis-API-Version", "3");

            return request;
        } else {
            HttpPost request = new HttpPost(api + endpoint);
            request.setEntity(new StringEntity(requestBody));

            // add request headers
            String token = api.equals(API_URL) ? dotenv.get("TRAVIS_CI_ORG_API_TOKEN") : dotenv.get("TRAVIS_CI_COM_API_TOKEN");
            request.addHeader("Authorization", "token " + token);
            request.addHeader("Travis-API-Version", "3");

            return request;
        }
    }

    public static Integer getBuildTime(String repository) {
        JSONObject request = TravisCI.travisCall("repo/" + URLEncoder.encode(repository) + "/builds?state=passed&limit=1", "GET", "", null);

        if (request.get("@type").equals("builds")) {
            JSONObject latestBuild = (JSONObject) ((JSONArray) request.get("builds")).get(0);

            Long duration = (Long) latestBuild.get("duration");

            return duration != null ? duration.intValue() : null;
        }

        return 0;
    }

    public static Integer getLinting(String configuration) {
        JSONObject result = TravisCI.travisCall("lint", "POST", configuration, null);

        return ((JSONArray) result.get("warnings")).size();
    }

    public static Integer getEnv(String repo) {
        JSONObject result = TravisCI.travisCall("lint", "GET", "repo", null);


        return 1;
    }
}
