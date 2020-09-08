package ch.uzh.ciclassifier.helper;

import io.github.cdimascio.dotenv.Dotenv;
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.revwalk.RevCommit;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;

public class TravisCI {

    private static final String API_URL = "https://api.travis-ci.org/";

    public static double getSuccessRatio(String repository) {
        JSONObject totalBuilds = TravisCI.travisCall("repo/" + URLEncoder.encode(repository) + "/builds");
        JSONObject successfulBuilds = TravisCI.travisCall("repo/" + URLEncoder.encode(repository) + "/builds?state=passed");

        if (null != totalBuilds && null != successfulBuilds) {
            Long successful = (Long) ((JSONObject) successfulBuilds.get("@pagination")).get("count");
            Long total = (Long) ((JSONObject) totalBuilds.get("@pagination")).get("count");

            return successful / (double) total;
        }
        return Double.parseDouble(null);
    }

    public static JSONObject travisCall(String endpoint) {
        Dotenv dotenv = Dotenv.load();

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet(API_URL + endpoint);

        // add request headers
        request.addHeader("Authorization", "token " + dotenv.get("TRAVIS_CI_API_TOKEN"));
        request.addHeader("Travis-API-Version", "3");

        try {
            CloseableHttpResponse response = httpClient.execute(request);
            HttpEntity entity = response.getEntity();

            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(EntityUtils.toString(entity));

            return json;

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }
}
