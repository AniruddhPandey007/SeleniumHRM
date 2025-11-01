package org.selenium.java.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.selenium.java.actionDriver.ActionDriver;
import org.selenium.java.base.BaseClass;

public class HomePage {

    private ActionDriver actionDriver;

    // Define locators using By class
    private By adminTab = By.xpath("//span[text()='Admin']");
    private By userIDButton = By.className("oxd-userdropdown-name");
    private By logoutButton = By.xpath("//a[text()='Logout']");
    private By orangeHRMLogo = By.xpath("//div[@class='oxd-brand-banner']//img");

/*    //Init the AD object by passing the WD instance
    public HomePage(WebDriver driver)
    {
        this.actionDriver = new ActionDriver(driver);
    }*/

    //Instead of creating the object here again we are calling driver from base class - Singleton method
    public HomePage(WebDriver driver)
    {
        this.actionDriver = BaseClass.getActionDriver();
    }

    public boolean isAdminTabVisible()
    {
        return actionDriver.isDisplayed(adminTab);
    }

    public boolean verifyOrangeHRMLogo()
    {
        return actionDriver.isDisplayed(orangeHRMLogo);
    }

    public void logout()
    {
        actionDriver.clickOnElement(userIDButton);
        actionDriver.clickOnElement(logoutButton);
    }

}
