package org.example;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class GithubRepoFetcher {

    private static final String GITHUB_API_URL = "https://api.github.com/repos/";
    private static final String ACCEPT_HEADER = "application/vnd.github+json";
    private static final String API_VERSION_HEADER = "2022-11-28";
    private static final Gson gson = new Gson();

    private static final Map<String, JsonElement> cache = new HashMap<>();

    public static JsonObject fetchRepoDetails(String owner, String repo, String token) throws IOException {
        String urlString = GITHUB_API_URL + owner + "/" + repo;
        return fetchFromGitHub(urlString, token).getAsJsonObject();
    }

    public static JsonObject fetchBranchDetails(String owner, String repo, String branch, String token) throws IOException {
        String urlString = GITHUB_API_URL + owner + "/" + repo + "/branches/" + branch;
        return fetchFromGitHub(urlString, token).getAsJsonObject();
    }

    public static JsonArray fetchBranchCommits(String owner, String repo, String branch, String token) throws IOException {
        String urlString = GITHUB_API_URL + owner + "/" + repo + "/commits?sha=" + branch;
        return fetchFromGitHub(urlString, token).getAsJsonArray();
    }

    private static JsonElement fetchFromGitHub(String urlString, String token) throws IOException {
        if (cache.containsKey(urlString)) {
            return cache.get(urlString);
        }

        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", ACCEPT_HEADER);

        if (token != null)
            connection.setRequestProperty("Authorization", "Bearer " + token);

        connection.setRequestProperty("X-GitHub-Api-Version", API_VERSION_HEADER);

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            JsonElement jsonResponse = gson.fromJson(response.toString(), JsonElement.class);
            cache.put(urlString, jsonResponse);
            return jsonResponse;
        } else {
            throw new IOException("Failed : HTTP error code : " + responseCode);
        }
    }
}