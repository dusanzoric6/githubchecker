package com.kbc;

import com.kbc.client.GithubCheckerClient;
import org.testng.annotations.Test;

import java.util.Map;
import java.util.UUID;

import static org.codehaus.plexus.util.StringUtils.contains;
import static org.testng.Assert.*;


public class GithubCheckerClientTest {

    private GithubCheckerClient githubCheckerClient = new GithubCheckerClient();

    @Test
    void getReposBySearchParam() {
        String searchString = "junit";
        Map<String, Object> response = githubCheckerClient.getReposBySearchParam(searchString);

        assertTrue(contains((String) response.get("name"), searchString));
        assertTrue(contains((String) response.get("fullName"), searchString));
        assertTrue((int) response.get("stars") > 0);
    }

    @Test
    public void getTest() {
        String nonExistingRepo = UUID.randomUUID().toString();
        assertThrows(IllegalArgumentException.class, () -> githubCheckerClient.getReposBySearchParam(nonExistingRepo));
    }


    @Test
    void getLatestReleaseTagByFullName() {
        String latestTag = githubCheckerClient.getLatestReleaseTagByFullName("SeleniumHQ/selenium");
        assertNotNull(latestTag);
    }

    @Test
    void getLatestReleaseTagByFullNameNotFound() {
        assertThrows(IllegalStateException.class, () -> githubCheckerClient.getLatestReleaseTagByFullName("cbeust/testng"));
    }
}