package com.kbc.service;

import com.kbc.client.GithubCheckerClient;
import org.apache.maven.artifact.versioning.DefaultArtifactVersion;

import java.util.Map;

public class GithubChecker {

    private GithubCheckerClient githubCheckerClient;

    public GithubChecker(GithubCheckerClient githubCheckerClient) {
        this.githubCheckerClient = githubCheckerClient;
    }

    public void start(String searchString, String inputTag) {
        //  Search Github for repo with the most stars that match the given search string
        Map<String, Object> topRepo = githubCheckerClient.getReposBySearchParam(searchString);

        System.out.println("**************************************************************************************************************************");
        System.out.println("Top repository name : " + topRepo.get("name"));
        System.out.println("Star count for top repository : " + topRepo.get("stars"));
        System.out.println("**************************************************************************************************************************");

        //  Search Github for latest tag
        checkLatestReleaseTag(inputTag, (String) topRepo.get("fullName"));
    }

    private void checkLatestReleaseTag(String inputTag, String topRepoFullName) {
        String latestTag = githubCheckerClient.getLatestReleaseTagByFullName(topRepoFullName);

        DefaultArtifactVersion latestTagVersion = new DefaultArtifactVersion(latestTag);
        DefaultArtifactVersion inputTagVersion = new DefaultArtifactVersion(inputTag);

        if (latestTagVersion.compareTo(inputTagVersion) == 0) {
            System.out.println("The tag you entered is of the latest version");
        } else if (latestTagVersion.compareTo(inputTagVersion) < 0) {
            System.out.println("Version of the tag you entered is not supported");
        } else {
            System.out.println("The tag you entered is NOT of the latest version");
        }
    }
}
