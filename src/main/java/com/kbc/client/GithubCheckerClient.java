package com.kbc.client;

import com.jayway.restassured.response.Response;

import java.util.Map;

import static com.jayway.restassured.RestAssured.get;
import static com.jayway.restassured.RestAssured.given;

public class GithubCheckerClient {

    private static final String REPOS_SEARCH_URL = "https://api.github.com/search/repositories";
    private static final String REPO_LATEST_RELEASE = "https://api.github.com/repos/%s/releases/latest";

    public Map<String, Object> getReposBySearchParam(String searchString) {
        Response response = given()
                .param("q", searchString)
                .param("sort", "stars")
                .param("order", "desc")
                .when()
                .get(REPOS_SEARCH_URL);

        if ((Integer) response.then().extract().path("total_count") == 0) {
            throw new IllegalArgumentException("Repository is not found by search string: " + searchString);
        }

        //  Extracting top repos data from response
        String topRepoName = response.then().extract().path("items[0].name");
        String topRepoFullName = response.then().extract().path("items[0].full_name");
        int topRepoStars = response.then().extract().path("items[0].stargazers_count");

        return Map.of(
                "name", topRepoName,
                "stars", topRepoStars,
                "fullName", topRepoFullName
        );
    }

    public String getLatestReleaseTagByFullName(String topRepoFullName) {
        Response response = get(String.format(REPO_LATEST_RELEASE, topRepoFullName));

        if (response.statusCode() == 404) {
            throw new IllegalStateException("GutHub API is not publishing releases under this repository");
        }

        //  extracting latest tag name
        return response.then().extract().path("tag_name");
    }
}
