package com.kbc;

import com.jayway.restassured.response.Response;
import org.apache.maven.artifact.versioning.DefaultArtifactVersion;

import static com.kbc.Steps.getLatestReleaseTagByFullName;
import static com.kbc.Steps.getReposBySearchParam;

public class Main {

    public static void main(String[] args) throws Exception {

        String searchName = System.getProperty("search");
        String inputTag = System.getProperty("tag");

//  Search Github for repositories that match the given search string “TestNG”
        Response allSearchedRepos = getReposBySearchParam(searchName);

//  Extracting top repos data from response
        String topRepoName = allSearchedRepos.then().extract().path("items[0].name");
        Integer topRepoStars = allSearchedRepos.then().extract().path("items[0].stargazers_count");
        String topRepoFullName = allSearchedRepos.then().extract().path("items[0].full_name");

        System.out.println("---------------------------------------------------");
        System.out.println("Top repository name : " + topRepoName);
        System.out.println("Star count for top repository : " + topRepoStars);
        System.out.println("---------------------------------------------------");

//  Search Github for latest tag
        String latestTag = getLatestReleaseTagByFullName(topRepoFullName);

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
