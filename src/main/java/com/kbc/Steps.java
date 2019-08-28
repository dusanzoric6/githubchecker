package com.kbc;

import com.jayway.restassured.response.Response;

import static com.jayway.restassured.RestAssured.get;
import static com.jayway.restassured.RestAssured.given;

class Steps {

    static Response getReposBySearchParam(String searchParam) {
        Response response = given().
                param("q", searchParam).
                param("sort", "stars").
                param("order", "desc").
                when().
                get("https://api.github.com/search/repositories");

        return response;
    }

    static String getLatestReleaseTagByFullName(String topRepoFullName) throws Exception {
        Response tagsForGivenRepo = get("https://api.github.com/repos/" + topRepoFullName + "/releases/latest");

//  extracting latest tag name
        String tagName = tagsForGivenRepo.then().extract().path("tag_name");

        if (tagName == null) {
            throw new Exception("GutHub API is not publishing releases under repository : "+ topRepoFullName);
        }
        return tagName;
    }
}
