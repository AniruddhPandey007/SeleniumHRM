package org.selenium.java.test;

import org.selenium.java.base.BaseClass;
import org.selenium.java.pages.HomePage;
import org.selenium.java.pages.LoginPage;
import org.selenium.java.utilities.ExtentManager;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class LoginPageTest extends BaseClass{

        private LoginPage loginPage;
        private HomePage homePage;

        @BeforeMethod   //Creating Object Before every Test Case
        public void setUpPages()
        {
            loginPage = new LoginPage(getDriver());
            homePage = new HomePage(getDriver());
        }


/*    @Test    //Method to Text Login Page
    public void verifyValidLoginTest()
    {
        loginPage.login("Admin", "admin123");
        Assert.assertTrue(homePage.isAdminTabVisible(), "Admin tab should be visible after successful login");
        homePage.logout();
        staticWait(5);
    }

    @Test
    public void verifyInvalidLoginTest()
    {
        loginPage.login("Admin", "admin");
        String expectedErrMessage = "Invalid credentials";
        Assert.assertTrue(loginPage.verifyErrorMessage(expectedErrMessage),"Test Failed: Invalid Error Message");
    }*/


    @Test
    public void verifyValidLoginTest() {

        //ExtentManager.startTest("Valid Login Test"); --This has been implemented in TestListener
        System.out.println("Running testMethod1 on thread: " + Thread.currentThread().getId());
        ExtentManager.logStep("Navigating to Login Page entering username and password");
        loginPage.login("Admin", "admin123");
        ExtentManager.logStep("Verifying Admin tab is visible or not");
        Assert.assertTrue(homePage.isAdminTabVisible(),"Admin tab should be visible after successful login ");
        ExtentManager.logStep("Validation Successful");
        homePage.logout();
        ExtentManager.logStep("Logged out Successfully!");
        staticWait(3);
    }

    @Test
    public void verifyInvalidLoginTest() {
        //ExtentManager.startTest("In-valid Login Test!"); --This has been implemented in TestListener
        System.out.println("Running testMethod2 on thread: " + Thread.currentThread().getId());
        ExtentManager.logStep("Navigating to Login Page entering username and password");
        loginPage.login("Admin", "admin");
        String expectedErrorMessage = "Invalid credentials";
        Assert.assertTrue(loginPage.verifyErrorMessage(expectedErrorMessage),"Test Failed: Invalid error message");
        ExtentManager.logStep("Validation Successful");
    }

}
