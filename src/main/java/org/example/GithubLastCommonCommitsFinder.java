package org.example;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class GithubLastCommonCommitsFinder implements LastCommonCommitsFinder {

    private final String owner;
    private final String repo;
    private final String token;

    public GithubLastCommonCommitsFinder(String owner, String repo, String token) {
        this.owner = owner;
        this.repo = repo;
        this.token = token;
    }

    @Override
    public Collection<String> findLastCommonCommits(String branchA, String branchB) throws IOException {
        Collection<String> branchACommits = findCommitsSHAs(branchA);
        Collection<String> branchBCommits = findCommitsSHAs(branchB);

        Collection<String> commonCommits = new ArrayList<String>(branchACommits);
        commonCommits.retainAll(branchBCommits);
        return commonCommits;
    }

    public Collection<String> findCommitsSHAs(String branchName) throws IOException {
        Collection<String> commitShas = new ArrayList<>();
        JsonArray commits;

        try {
            commits = GithubRepoFetcher.fetchBranchCommits(owner, repo, branchName, token);
        } catch (IOException e) {
            throw new IOException("Branch " + branchName + " does not exist");
        }

        for (JsonElement commit : commits) {
            JsonObject commitObject = commit.getAsJsonObject();
            String sha = commitObject.get("sha").getAsString();
            commitShas.add(sha);
        }
        return commitShas;
    }

}
