package com.kbc;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Map;

public class TestSteps {

    @Test
    public void testGetTopRepoData() {
        Map topSeleniumRepo = Steps.getTopRepoData("selenium");

        Assert.assertEquals(topSeleniumRepo.get("name"), "selenium");
        Assert.assertEquals(topSeleniumRepo.get("stars"), 15408);
        Assert.assertEquals(topSeleniumRepo.get("fullName"), "SeleniumHQ/selenium");
    }

    @Test
    public void testGetLatestReleaseTagByFullName() throws Exception {
        String latestReleaseTag = Steps.getLatestReleaseTagByFullName("SeleniumHQ/selenium");
        Assert.assertEquals(latestReleaseTag, "selenium-3.141.59");
    }
}
