package com.kbc;

import com.jayway.restassured.response.Response;

import java.util.Map;

import static com.jayway.restassured.RestAssured.get;
import static com.jayway.restassured.RestAssured.given;

class Steps {

    static Map getTopRepoData(String searchParam) {
        Response response = given().
                param("q", searchParam).
                param("sort", "stars").
                param("order", "desc").
                when().
                get("https://api.github.com/search/repositories");

//  Extracting top repos data from response
        String topRepoName = response.then().extract().path("items[0].name");
        Integer topRepoStars = response.then().extract().path("items[0].stargazers_count");
        String topRepoFullName = response.then().extract().path("items[0].full_name");

        return Map.of(
                "name", topRepoName,
                "stars", topRepoStars,
                "fullName", topRepoFullName
        );
    }

    static String getLatestReleaseTagByFullName(String topRepoFullName) throws Exception {
        Response tagsForGivenRepo = get("https://api.github.com/repos/" + topRepoFullName + "/releases/latest");
        if (tagsForGivenRepo.statusCode() == 404) {
            throw new Exception("GutHub API is not publishing releases under owner/repository : " + topRepoFullName);
        }

        //  extracting latest tag name
        return tagsForGivenRepo.then().extract().path("tag_name");
    }
}
