package org.selenium.java.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.selenium.java.actionDriver.ActionDriver;
import org.selenium.java.base.BaseClass;

public class LoginPage {

    private ActionDriver actionDriver;

    //Define locators using By class
    private By userNameField = By.name("username");
    private By passWordField = By.cssSelector("input[type='password']");
    private By loginButton = By.cssSelector("button[type='submit']");
    private By errorMessage = By.xpath("//p[text()='Invalid credentials']");

    //Init the AD object by passing the WD instance
/*    public LoginPage(WebDriver driver)
    {
        this.actionDriver = new ActionDriver(driver);
    }*/

    //Instead of creating the object here again we are calling driver from base class - Singleton method
    public LoginPage(WebDriver driver)
    {
         this.actionDriver = BaseClass.getActionDriver();
    }

    //Method to Perform Login
    public void login(String username, String password)
    {
        actionDriver.enterText(userNameField,username);
        actionDriver.enterText(passWordField,password);
        actionDriver.clickOnElement(loginButton);
    }

    //Method to check if error message is displayed
    public boolean checkErrorMessage()
    {
       return actionDriver.isDisplayed(errorMessage);
    }

    //Method to get the error message text
    public String getErrorMessageText()
    {
       return actionDriver.getText(errorMessage);
    }

    //Method to check if error message is correct
    public boolean verifyErrorMessage(String expectedErrorMessage)
    {
       return actionDriver.compareText(errorMessage,expectedErrorMessage);
    }

}
