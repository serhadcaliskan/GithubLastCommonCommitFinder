package org.example;

import java.io.IOException;
import java.util.Collection;

public class GithubLastCommonCommitsFinderFactory implements LastCommonCommitsFinderFactory {

    @Override
    public LastCommonCommitsFinder create(String owner, String repo, String token){
        return new GithubLastCommonCommitsFinder(owner, repo, token);
    }

}