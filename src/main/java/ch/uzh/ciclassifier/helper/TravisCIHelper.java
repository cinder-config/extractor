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

public class TravisCIHelper {

    public static final String API_ORG = "https://api.travis-ci.org/";
    public static final String API_COM = "https://api.travis-ci.com/";

    public static JSONObject travisCall(String endpoint, String type, String requestBody, String api) {
        CloseableHttpClient httpClient = HttpClients.createDefault();

        try {
            HttpRequestBase request = TravisCIHelper.prepare(api, endpoint, type, requestBody);

            CloseableHttpResponse response = httpClient.execute(request);
            HttpEntity entity = response.getEntity();

            JSONParser parser = new JSONParser();

            return (JSONObject) parser.parse(EntityUtils.toString(entity));

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
            String token = api.equals(API_ORG) ? dotenv.get("TRAVIS_CI_ORG_API_TOKEN") : dotenv.get("TRAVIS_CI_COM_API_TOKEN");
            request.addHeader("Authorization", "token " + token);
            request.addHeader("Travis-API-Version", "3");

            return request;
        } else {
            HttpPost request = new HttpPost(api + endpoint);
            request.setEntity(new StringEntity(requestBody));

            // add request headers
            String token = api.equals(API_ORG) ? dotenv.get("TRAVIS_CI_ORG_API_TOKEN") : dotenv.get("TRAVIS_CI_COM_API_TOKEN");
            request.addHeader("Authorization", "token " + token);
            request.addHeader("Travis-API-Version", "3");

            return request;
        }
    }

    public static String getApiForRepository(String repository) {
        JSONObject apiOrgRequest = TravisCIHelper.travisCall("repo/" + URLEncoder.encode(repository), "GET", "", API_ORG);

        if (apiOrgRequest.get("@type").equals("error") || apiOrgRequest.get("active").equals(false)) {
            JSONObject apiComRequest = TravisCIHelper.travisCall("repo/" + URLEncoder.encode(repository), "GET", "", API_COM);
            if (apiComRequest.get("@type").equals("error") || apiComRequest.get("active").equals(false)) {
                return null;
            }
            return API_COM;
        }
        return API_ORG;
    }
}
