package com.kbc;

import com.kbc.client.GithubCheckerClient;
import com.kbc.service.GithubChecker;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class GithubCheckerTest {

    private static final Map<String, Object> RESPONSE = Map.of("name", "junit5",
            "fullName", "junit-team/junit5",
            "stars", 32);

    private ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private PrintStream originalOut = System.out;

    @BeforeEach
    void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    void startNotLatestTag() {
        GithubCheckerClient githubCheckerClient = mock(GithubCheckerClient.class);
        when(githubCheckerClient.getReposBySearchParam(eq("junit5"))).thenReturn(RESPONSE);
        when(githubCheckerClient.getLatestReleaseTagByFullName(eq("junit-team/junit5"))).thenReturn("6.0.0");

        GithubChecker githubChecker = new GithubChecker(githubCheckerClient);
        githubChecker.start("junit5", "5.0.0");

        assertTrue(outContent.toString().contains("Top repository name : junit"));
        assertTrue(outContent.toString().contains("Star count for top repository : 32"));
        assertTrue(outContent.toString().contains("The tag you entered is NOT of the latest version"));
    }

    @Test
    void startLatestTag() {
        GithubCheckerClient githubCheckerClient = mock(GithubCheckerClient.class);
        when(githubCheckerClient.getReposBySearchParam(eq("junit5"))).thenReturn(RESPONSE);
        ;
        when(githubCheckerClient.getLatestReleaseTagByFullName(eq("junit-team/junit5"))).thenReturn("2.0.0");

        GithubChecker githubChecker = new GithubChecker(githubCheckerClient);
        githubChecker.start("junit5", "5.0.0");

        assertTrue(outContent.toString().contains("Top repository name : junit"));
        assertTrue(outContent.toString().contains("Star count for top repository : 32"));
        assertTrue(outContent.toString().contains("Version of the tag you entered is not supported"));
    }

    @Test
    void startLatestTagSame() {
        GithubCheckerClient githubCheckerClient = mock(GithubCheckerClient.class);
        when(githubCheckerClient.getReposBySearchParam(eq("junit5"))).thenReturn(RESPONSE);
        ;
        when(githubCheckerClient.getLatestReleaseTagByFullName(eq("junit-team/junit5"))).thenReturn("5.0.0");

        GithubChecker githubChecker = new GithubChecker(githubCheckerClient);
        githubChecker.start("junit5", "5.0.0");

        assertTrue(outContent.toString().contains("Top repository name : junit"));
        assertTrue(outContent.toString().contains("Star count for top repository : 32"));
        assertTrue(outContent.toString().contains("The tag you entered is of the latest version"));
    }

}