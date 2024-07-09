package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import java.time.Duration;

public abstract class Base {
    public WebDriver webDriver;

    @BeforeSuite(alwaysRun = true)
    public void setupSuite() {
        System.setProperty("webDriver.chrome.driver", "C:/Program Files (x86)/Google/Chrome/Application/chromedriver.exe");
    }

    @BeforeMethod(alwaysRun = true)
    public void initDriver(ITestResult result) {
        String testName = result.getMethod().getMethodName();

        if (!testName.equals("testDecrementCounter") && !testName.equals("addingRelatedGoods") && !testName.equals("checkTotalSumAfterAddingProduct")) {

            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless");
            options.addArguments("--disable-gpu");
            options.addArguments("--window-size=1920,1200");

            webDriver = new ChromeDriver(options);
            System.out.println("Browser opened successfully");
            webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        }
    }

    @AfterMethod(alwaysRun = true)
    public void quitDriver(ITestResult result) {
        String testName = result.getMethod().getMethodName();

        if (!testName.equals("testIncrementCounter") && !testName.equals("redirectionToCheckoutPage") && !testName.equals("addingRelatedGoods")) {
            if (webDriver != null) {
                webDriver.quit();
            }
        }
    }
}

