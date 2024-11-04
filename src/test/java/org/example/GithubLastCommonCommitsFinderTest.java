package org.example;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Collection;


public class GithubLastCommonCommitsFinderTest {

    private GithubLastCommonCommitsFinder finder;

    @Before
    public void setUp() {
        finder = new GithubLastCommonCommitsFinder("google", "gson", null);
    }

    @Test
    public void testUnexistedBranchRequest(){
        try{
            finder.findCommitsSHAs("slash");
        }
        catch (IOException e){
            assert(true);
            return;
        }

        assert(false);
    }

    @Test
    public void testGetLastCommitsSizeCorrectly() {
        Collection<String> commits;
        try {
            commits = finder.findCommitsSHAs("main");
        } catch (IOException e) {
            assert (false);
            return;
        }
        Assert.assertEquals(30, commits.size());
    }

}