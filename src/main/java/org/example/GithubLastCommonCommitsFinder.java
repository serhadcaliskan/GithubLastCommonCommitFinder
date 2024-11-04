package org.example;

import java.io.IOException;
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
        GithubRepoFetcher.fetchRepoDetails(repo, owner, token);

        return null;
    }

}
