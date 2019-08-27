import com.jayway.restassured.response.Response;
import org.apache.maven.artifact.versioning.DefaultArtifactVersion;

public class Main {

    public static void main(String[] args) throws Exception {

        String searchName = args[0];
        String inputTag = args[1];

//  Search Github for repositories that match the given search string “TestNG”
        Response allSearchedRepos = Steps.getReposBySearchParam(searchName);

//  Extracting top repos data from response
        String topRepoName = allSearchedRepos.then().extract().path("items[0].name");
        Integer topRepoStars = allSearchedRepos.then().extract().path("items[0].stargazers_count");
        String topRepoFullName = allSearchedRepos.then().extract().path("items[0].full_name");

        System.out.println("Top repository name : " + topRepoName);
        System.out.println("Star count for top repository : " + topRepoStars);

//  Search Github for latest tag
        String latestTag = Steps.getLatestReleaseTagByFullName(topRepoFullName);

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
