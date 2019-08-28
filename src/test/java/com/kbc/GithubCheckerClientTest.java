package com.kbc;

import com.kbc.client.GithubCheckerClient;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.UUID;

import static org.apache.commons.lang3.StringUtils.contains;
import static org.junit.jupiter.api.Assertions.*;

class GithubCheckerClientTest {

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
    void getReposBySearchParamNotFound() {
        String nonExistingRepoName = UUID.randomUUID().toString();
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> githubCheckerClient.getReposBySearchParam(nonExistingRepoName));

        assertEquals("Repository is not found by search string: " + nonExistingRepoName, exception.getMessage());
    }

    @Test
    void getLatestReleaseTagByFullName() {
        String latestTag = githubCheckerClient.getLatestReleaseTagByFullName("junit-team/junit5");

        assertNotNull(latestTag);
    }

    @Test
    void getLatestReleaseTagByFullNameNotFound() {
        IllegalStateException exception =
                assertThrows(IllegalStateException.class, () -> githubCheckerClient.getLatestReleaseTagByFullName("dusanzoric6/stolarija"));

        assertEquals("GutHub API is not publishing releases under this repository", exception.getMessage());

    }
}