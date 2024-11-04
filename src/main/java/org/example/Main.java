package org.example;

public class Main {
    public static void main(String[] args) {

        GithubLastCommonCommitsFinderFactory factory = new GithubLastCommonCommitsFinderFactory();
        LastCommonCommitsFinder finder = factory.create("google", "gson", null);
        try {
            finder.findLastCommonCommits("main", "dev");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}