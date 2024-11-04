package org.example;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GithubRepoFetcher {

    private static final String GITHUB_API_URL = "https://api.github.com/repos/";
    private static final String ACCEPT_HEADER = "application/vnd.github+json";
    private static final String API_VERSION_HEADER = "2022-11-28";
    private static final Gson gson = new Gson();

    public static void main(String[] args) {
        String owner = "OWNER";
        String repo = "REPO";
        String token = "YOUR-TOKEN";

        try {
            JsonObject repoDetails = fetchRepoDetails(owner, repo, token);
            System.out.println(repoDetails);

            JsonObject repoActivity = fetchRepoActivity(owner, repo, token);
            System.out.println(repoActivity);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static JsonObject fetchRepoDetails(String owner, String repo, String token) throws IOException {
        String urlString = GITHUB_API_URL + owner + "/" + repo;
        return fetchFromGitHub(urlString, token);
    }

    public static JsonObject fetchRepoActivity(String owner, String repo, String token) throws IOException {
        String urlString = GITHUB_API_URL + owner + "/" + repo + "/activity";
        return fetchFromGitHub(urlString, token);
    }

    private static JsonObject fetchFromGitHub(String urlString, String token) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", ACCEPT_HEADER);
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

            return gson.fromJson(response.toString(), JsonObject.class);
        } else {
            throw new IOException("Failed : HTTP error code : " + responseCode);
        }
    }
}