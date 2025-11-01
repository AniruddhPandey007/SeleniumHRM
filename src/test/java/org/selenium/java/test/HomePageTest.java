package org.selenium.java.test;

import org.selenium.java.base.BaseClass;
import org.selenium.java.pages.HomePage;
import org.selenium.java.pages.LoginPage;
import org.selenium.java.utilities.ExtentManager;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class HomePageTest extends BaseClass{

        private LoginPage loginPage;
        private HomePage homePage;

        @BeforeMethod   //Creating Object Before every Test Case
        public void setUpPages()
        {
            loginPage = new LoginPage(getDriver());
            homePage = new HomePage(getDriver());
        }


    @Test    //Method to Test Home Page
    public void verifyOrangeHRMLogo() {
        ExtentManager.logStep("Navigating to Login Page entering username and password");
        loginPage.login("Admin", "admin123");
        ExtentManager.logStep("Verifying Logo is visible or not");
        Assert.assertTrue(homePage.verifyOrangeHRMLogo(), "Logo is not Visible");
        ExtentManager.logStep("Validation Successful");
        homePage.logout();
        ExtentManager.logStep("Logged out Successfully!");


    }



/*    @Test
    public void verifyOrangeHRMLogo(String username, String password) {
        //ExtentManager.startTest("Home Page Verify Logo Test"); --This has been implemented in TestListener
        ExtentManager.logStep("Navigating to Login Page entering username and password");
        loginPage.login(username, password);
        ExtentManager.logStep("Verifying Logo is visible or not");
        Assert.assertTrue(homePage.verifyOrangeHRMLogo(),"Logo is not visible");
        ExtentManager.logStep("Validation Successful");
        homePage.logout();
        ExtentManager.logStep("Logged out Successfully!");
    }*/

}
