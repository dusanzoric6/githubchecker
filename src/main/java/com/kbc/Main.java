package com.kbc;

import org.apache.maven.artifact.versioning.DefaultArtifactVersion;

import java.util.Map;

import static com.kbc.Steps.getLatestReleaseTagByFullName;
import static com.kbc.Steps.getTopRepoData;

public class Main {

    public static void main(String[] args) throws Exception {

        String searchName = System.getProperty("search");
        String inputTag = System.getProperty("tag");

        Map topRepo = getTopRepoData(searchName);

        System.out.println("**************************************************************************************************************************");
        System.out.println("Top repository name : " + topRepo.get("name"));
        System.out.println("Star count for top repository : " + topRepo.get("stars"));
        System.out.println("**************************************************************************************************************************");

//  Search Github for latest tag
        String latestTag = getLatestReleaseTagByFullName((String) topRepo.get("fullName"));

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
