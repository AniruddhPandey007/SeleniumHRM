package org.selenium.java.actionDriver;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.selenium.java.base.BaseClass;
import org.selenium.java.utilities.ExtentManager;

import java.time.Duration;

public class ActionDriver {

        private WebDriver driver;
        private WebDriverWait wait;
        public final static Logger logger = BaseClass.logger;

      public ActionDriver(WebDriver driver)
      {
            this.driver = driver;
            int explicitWait = Integer.parseInt(BaseClass.getProp().getProperty("explicitWait"));
            this.wait = new WebDriverWait(driver, Duration.ofSeconds(explicitWait));
            logger.info("@ ActionDriver Constructor() - WebDriver instance is created");
      }


      //Method to click on an Element
        public void clickOnElement(By by)
        {
            try {
                applyBorder(by, "green");
                waitForElementToBeClickable(by);
                driver.findElement(by).click();
                logger.info("clicked on Element");
                ExtentManager.logStep("clicked an element: ");
            } catch (Exception e) {
                applyBorder(by, "red");
               // System.out.println("Unable to Click on Element: "+ e.getMessage());
                logger.error("Unable to Click on Element: "+ e.getMessage());
            }
        }


       //Method to Enter text into input Fields
        public void enterText(By by, String value)
        {
            try {
                applyBorder(by, "green");
                waitForElementToBeVisible(by);
                WebElement element = driver.findElement(by);
                element.clear();
                element.sendKeys(value);
                logger.info("Entered value as: "+value);
            } catch (Exception e) {
                applyBorder(by, "red");
                //System.out.println("Unable to Enter Text: " +value+ " into element: "+by+ " Exception As:  "+ e.getMessage());
                logger.error("Unable to Enter Text: " +value+ " into element: "+by+ " Exception As:  "+ e.getMessage());
                ExtentManager.logFailure(BaseClass.getDriver(), "Unable to click element:", "_unable to click");

            }
        }


        //Method to get Text from an Input Field
        public String getText(By by)
        {
            try {
                applyBorder(by, "green");
                waitForElementToBeVisible(by);
                return driver.findElement(by).getText();
            } catch (Exception e) {
                applyBorder(by, "red");
                //System.out.println("Not able to Get Text: " +e.getMessage());
                logger.error("Not able to Get Text: " +e.getMessage());
                return "";
            }
        }

        //Method to compare Text
        public boolean compareText(By by, String expectedText)
        {
            try {
                waitForElementToBeVisible(by);
                String actualText = driver.findElement(by).getText();
                if(expectedText.equals(actualText))
                {
                    applyBorder(by, "green");
                  //  System.out.println(" Actual Text: "+actualText +" Matches with Expected Text: "+ expectedText);
                    logger.info(" Actual Text: "+actualText +" Matches with Expected Text: "+ expectedText);
                    ExtentManager.logStepWithScreenshot(BaseClass.getDriver(), "Compare Text", "Text Verified Successfully! "+actualText+ " equals "+expectedText);
                    return true;
                }
                else
                {
                    applyBorder(by, "red");
                    //System.out.println(" Actual Text: "+actualText +" Does not Match with Expected Text: "+ expectedText);
                    logger.error(" Actual Text: "+actualText +" Does not Match with Expected Text: "+ expectedText);
                    ExtentManager.logFailure(BaseClass.getDriver(), "Text Comparison Failed!", "Text Comparison Failed! "+actualText+ " not equals "+expectedText);
                    return false;
                }
            } catch (Exception e) {
                applyBorder(by, "red");
               // System.out.println("Unable to Compare Values: " + e.getMessage());
                logger.error("Unable to Compare Values: " + e.getMessage());
            }
            return false;
        }



    //Method to check if Element is Displayed
    public boolean isDisplayed(By by) {
        try {
            applyBorder(by, "green");
            waitForElementToBeVisible(by);
            logger.info("Element is displayed ");
            return driver.findElement(by).isDisplayed();
        } catch (Exception e) {
            applyBorder(by, "red");
            //System.out.println("Unable to Display Element" + e.getMessage());
            logger.error("Unable to Display Element" + e.getMessage());
            return false;
        }
    }


    // Scroll to an element
    public void scrollToElement(By by) {
        try {
            applyBorder(by, "green");
            JavascriptExecutor js = (JavascriptExecutor) driver;
            WebElement element = driver.findElement(by);
            js.executeScript("arguments[0],scrollIntoView(true);", element);
            logger.info("Scrolled to Element");
        } catch (Exception e) {
            applyBorder(by, "red");
            //System.out.println("Unable to locate element:" + e.getMessage());
            logger.error("Unable to locate element:" + e.getMessage());
        }
    }


    // Wait for the page to load
    public void waitForPageLoad(int timeOutInSec) {
        try {
            wait.withTimeout(Duration.ofSeconds(timeOutInSec)).until(
                    webDriver -> ((JavascriptExecutor) webDriver)
                            .executeScript("return document.readyState")
                            .equals("complete"));
           // System.out.println("Page loaded successfully.");
            logger.info("Page loaded successfully.");
        } catch (Exception e) {
           // System.out.println("Page did not load within " + timeOutInSec + " seconds");
            logger.error("Page did not load within " + timeOutInSec + " seconds");
        }
    }


      //waitForElementToBeClickable
      private void waitForElementToBeClickable(By by)
      {
          try {
              wait.until(ExpectedConditions.elementToBeClickable(by));
              logger.info("wait For Element To Be Clickable");
              ExtentManager.logStep("wait For Element To Be Clickable");
          } catch (Exception e) {
              //System.out.println("Element is not Clickable: "+ e.getMessage());
              logger.error("Element is not Clickable: "+ e.getMessage());
              ExtentManager.logStep("Element is not Clickable: "+ e.getMessage());
          }
      }

      //waitForElementToBeVisible
      private void waitForElementToBeVisible(By by)
      {
          try {
              wait.until(ExpectedConditions.visibilityOfElementLocated(by));
              logger.info("wait For Element To Be Visible");
              ExtentManager.logStep("wait For Element To Be Visible");
          } catch (Exception e) {
             // System.out.println("Element is not Visible :"+e.getMessage());
              logger.error("Element is not Visible :"+e.getMessage());
              ExtentManager.logStep("Element is not Visible: "+ e.getMessage());

          }
      }

    //Utility Method to Border an element
    public void applyBorder(By by,String color) {
        try {
            //Locate the element
            WebElement element = driver.findElement(by);
            //Apply the border
            String script = "arguments[0].style.border='3px solid "+color+"'";
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript(script, element);
            logger.info("Applied the border with color "+color+ " to element: "+getElementDescription(by));
        } catch (Exception e) {
            logger.warn("Failed to apply the border to an element: "+getElementDescription(by),e);
        }
    }


    // Method to get the description of an element using By locator
    public String getElementDescription(By locator) {
        // Check for null driver or locator to avoid NullPointerException
        if (driver == null) {
            return "Driver is not initialized.";
        }
        if (locator == null) {
            return "Locator is null.";
        }

        try {
            // Find the element using the locator
            WebElement element = driver.findElement(locator);

            // Get element attributes
            String name = element.getDomProperty("name");
            String id = element.getDomProperty("id");
            String text = element.getText();
            String className = element.getDomProperty("class");
            String placeholder = element.getDomProperty("placeholder");

            // Return a description based on available attributes
            if (isNotEmpty(name)) {
                return "Element with name: " + name;
            } else if (isNotEmpty(id)) {
                return "Element with ID: " + id;
            } else if (isNotEmpty(text)) {
                return "Element with text: " + truncate(text, 50);
            } else if (isNotEmpty(className)) {
                return "Element with class: " + className;
            } else if (isNotEmpty(placeholder)) {
                return "Element with placeholder: " + placeholder;
            } else {
                return "Element located using: " + locator.toString();
            }
        } catch (Exception e) {
            // Log exception for debugging
            e.printStackTrace(); // Replace with a logger in a real-world scenario
            return "Unable to describe element due to error: " + e.getMessage();
        }
    }

    // Utility method to check if a string is not null or empty
    private boolean isNotEmpty(String value) {
        return value != null && !value.isEmpty();
    }

    // Utility method to truncate long strings
    private String truncate(String value, int maxLength) {
        if (value == null || value.length() <= maxLength) {
            return value;
        }
        return value.substring(0, maxLength) + "...";
    }


}
