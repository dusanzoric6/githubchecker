package com.kbc;

import com.kbc.client.GithubCheckerClient;
import com.kbc.service.GithubChecker;

public class Main {

    public static void main(String[] args) {
        String searchString = System.getProperty("search");
        String inputTag = System.getProperty("tag");

        if (searchString == null || inputTag == null) {
            throw new IllegalArgumentException("search string or tag is missing");
        }

        GithubChecker githubChecker = new GithubChecker(new GithubCheckerClient());
        githubChecker.start(searchString, inputTag);
    }
}
