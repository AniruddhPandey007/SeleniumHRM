package org.selenium.java.test;

import org.selenium.java.base.BaseClass;
import org.selenium.java.utilities.ExtentManager;
import org.testng.annotations.Test;

public class DummyClass extends BaseClass {

    @Test
    public void dummyTest()
    {
        //String title = driver.getTitle();
        //Thread Local
        String title = getDriver().getTitle();
        //System.out.println(title);
        ExtentManager.logStep("verifying the title");
        assert title.equals("OrangeHRM"):"test Failed : Title is not a Match";

        System.out.println("Test Passed: Title is a match");

    }
}
