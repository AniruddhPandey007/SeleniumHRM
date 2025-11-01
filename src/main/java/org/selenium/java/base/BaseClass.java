package org.selenium.java.base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.selenium.java.actionDriver.ActionDriver;
import org.selenium.java.utilities.ExtentManager;
import org.selenium.java.utilities.LoggerManager;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.asserts.SoftAssert;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

public class BaseClass {

    /*
    made prop static so the same instance can be used by other tests as it gets initialized only once in before suite
    static not needed for driver coz in tear down it gets killed everytime in before method so, for every test it will be created again anyway, but using as its safe
    */
    protected static Properties prop;
   /* now using thread local so no need of these
    protected static WebDriver driver;
    private static ActionDriver actionDriver;
    */
    private static ThreadLocal<WebDriver> driver =  new ThreadLocal<>();
    private static ThreadLocal<ActionDriver> actionDriver =  new ThreadLocal<>();
    public final static Logger logger = LoggerManager.getLogger(BaseClass.class);
    protected ThreadLocal<SoftAssert> softAssert = ThreadLocal.withInitial(SoftAssert::new);


    // Getter method for soft assert
    public SoftAssert getSoftAssert() {
        return softAssert.get();
    }
    //remember to put softAssert.assertAll() in test methods where you use soft assert



    //Load the Config Properties File
    @BeforeSuite
    public void loadConfig() throws IOException {
        prop = new Properties();
        //FileInputStream fis = new FileInputStream("src/main/resources/config.properties");
        //using System.getProperty to make it OS independent
        FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "/src/main/resources/config.properties");
        prop.load(fis);
        logger.info("Config.property File Loaded");
    }

    @BeforeMethod
    public synchronized void setUp() throws IOException {
        System.out.println("Setting up WebDriver for :" + this.getClass().getSimpleName());
        launchBrowser();
        configureBrowser();
        staticWait(2);
        logger.info("WebDriver Init and Browser launched");
 /*     logger.trace("This is a trace message");
        logger.error("This is an error message");
        logger.debug("This is a debug message");
        logger.fatal("This is a fatal message");
        logger.warn("This is a warn message");*/

       /* //Init the ActionDriver only once here
        if(actionDriver == null)
        {
            actionDriver = new ActionDriver(driver);
            //System.out.println("ActionDriver instance is created");
            logger.info("ActionDriver instance is created");
        }*/

        //Init actiondriver for the current thread
        actionDriver.set(new ActionDriver(getDriver()));
        logger.info("ActionDriver init for current thread: " + Thread.currentThread().getId());
    }

    //Init the Browser based on Prop File Browser Variable
    private synchronized void launchBrowser() {
        String browser = prop.getProperty("browser");

        if (browser.equalsIgnoreCase("chrome")) {
           // System.setProperty("webdriver.chrome.driver", "C:\\Users\\CK\\Downloads\\Learning\\Sel\\Drivers\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");
            //driver = new ChromeDriver();
            //using WebDriverManager to manage driver binaries
            // WebDriverManager.chromedriver().setup();
            // driver.set(new ChromeDriver());
            //Using ChromeOptions to run in headless mode
            ChromeOptions options = new ChromeOptions();
            //options.addArguments("--headless"); //run in headless mode
            options.addArguments("--headless=new"); //new headless mode in Chrome 109 and above
            options.addArguments("--disable-gpu"); // applicable to windows os only
            options.addArguments("--disable-notifications"); //disable notifications
            options.addArguments("--no-sandbox"); //bypass OS security model
            options.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
            options.addArguments("--disable-infobars");
            options.addArguments("--window-size=1920,1080"); // set window size to avoid issues in headless mode
            driver.set(new ChromeDriver(options));
            ExtentManager.registerDriver(getDriver());
            logger.info("Inside launchBrowser(), ChromeDriver Instance initialized");
        } else if (browser.equalsIgnoreCase("firefox")) {
           // driver = new FirefoxDriver();
            //System.setProperty("webdriver.firefox.driver", "C:\\Users\\CK\\Downloads\\Learning\\Sel\\Drivers\\geckodriver-v0.36.0-win-aarch64\\geckodriver.exe");
            //WebDriverManager.firefoxdriver().setup();
            //Using ChromeOptions to run in headless mode
            FirefoxOptions options = new FirefoxOptions();
            options.addArguments("--headless=new"); //new headless mode in Chrome 109 and above
            options.addArguments("--disable-gpu"); // applicable to windows os only
            options.addArguments("--disable-notifications"); //disable notifications
            options.addArguments("--no-sandbox"); //bypass OS security model
            options.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
            options.addArguments("--disable-infobars");
            options.addArguments("--window-size=1920,1080"); // set window size to avoid issues in headless mode
            driver.set(new FirefoxDriver(options));
            ExtentManager.registerDriver(getDriver());
            logger.info("Inside launchBrowser(), FirefoxDriver Instance initialized");
        } else if (browser.equalsIgnoreCase("edge")) {
            //driver = new EdgeDriver();
            System.setProperty("webdriver.edge.driver", "C:\\Users\\CK\\Downloads\\Learning\\Sel\\Drivers\\edgedriver_win64\\msedgedriver.exe");
            //WebDriverManager.edgedriver().setup();
            //Using ChromeOptions to run in headless mode
            EdgeOptions options = new EdgeOptions();
            options.addArguments("--headless=new"); //new headless mode in Chrome 109 and above
            options.addArguments("--disable-gpu"); // applicable to windows os only
            options.addArguments("--disable-notifications"); //disable notifications
            options.addArguments("--no-sandbox"); //bypass OS security model
            options.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
            options.addArguments("--disable-infobars");
            options.addArguments("--window-size=1920,1080"); // set window size to avoid issues in headless mode
            driver.set(new EdgeDriver(options));
            ExtentManager.registerDriver(getDriver());
            logger.info("Inside launchBrowser(), EdgeDriver Instance initialized");
        } else {
            throw new IllegalArgumentException("Browser not supported " + browser);
        }

    }

    //Configure Browser level settings
    private void configureBrowser() {
        //Implicit Wait
        int implicitWait = Integer.parseInt(prop.getProperty("implicitWait"));
        //driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWait));
        //Now using getDriver from Thread local
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWait));

        //Maximize Browser
        // Commented to avoid issues in headless mode
        //getDriver().manage().window().maximize();


        //navigate to URL
        try {
            getDriver().get(prop.getProperty("url"));
        } catch (Exception e) {
            System.out.println("Failed to Navigate to the URL" + e.getMessage());
        }

    }


    @AfterMethod
    public synchronized void tearDown() {
        if (getDriver() != null) {
            try {
                getDriver().quit();
            } catch (Exception e) {
                System.out.println("Unable to Quit the Browser: " + e.getMessage());
            }
        }
//        driver = null;
//        actionDriver = null;
        //due to Thread local
        driver.remove();
        actionDriver.remove();
        //System.out.println("WebDriver Instance is closed");
        logger.info("At tearDown() - WebDriver Instance is closed");
    }


    //Driver Getter Method  - so that driver can be used outside pkg
//    public WebDriver getDriver()
//    {
//        return driver;
//    }

    //init the Webdriver for getter method
    public static WebDriver getDriver()
    {
        if(driver.get() == null)
        {
            System.out.println("WebDriver is not initialized");
            throw new IllegalStateException("WebDriver is not initialized");
        }
        return driver.get();
    }

    //init the ActionDriver for getter method
    public static ActionDriver getActionDriver()
    {
        if(actionDriver.get() == null)
        {
            System.out.println("ActionDriver is not initialized");
            throw new IllegalStateException("ActionDriver is not initialized");
        }
        return actionDriver.get();
    }


    //Driver Setter Method
    public void setDriver(ThreadLocal<WebDriver> driver)
    {
        this.driver = driver;
    }

   /* //Better way to write Driver Setter Method
    public static void setDriver(ThreadLocal<WebDriver> driver) {
        if (webDriver == null) {
            throw new IllegalArgumentException("webDriver must not be null");
        }
        driver.set(webDriver);
        logger.info("WebDriver set for current thread: " + Thread.currentThread().getId());
    }*/



    //Getter Method for Property File
    public static Properties getProp()
    {
        return prop;
    }


    //Static wait to Pause
    public void staticWait(int seconds) {
        LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(seconds));
        logger.info("At staticWait - Will apply wait of "+ seconds);
    }
}
