package com.kbc;

import com.kbc.client.GithubCheckerClient;
import com.kbc.service.GithubChecker;

public class Main {

    public static void main(String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException("search string or tag is missing");
        }

        String searchString = args[0];
        String inputTag = args[1];

        GithubChecker githubChecker = new GithubChecker(new GithubCheckerClient());
        githubChecker.start(searchString, inputTag);
    }
}
