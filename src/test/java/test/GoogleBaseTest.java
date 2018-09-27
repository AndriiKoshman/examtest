package test;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import page.GoogleResultPage;
import page.GoogleSearchPage;

public class GoogleBaseTest {

    WebDriver driver;
    GoogleSearchPage googleSearchPage;
    GoogleResultPage googleResultPage;
    ChromeOptions chromeOptions;

    @BeforeMethod
    @Parameters({"browser", "environmentUrl"})
    public void setUp(@Optional("chrome") String browser, @Optional("https://www.google.com/") String environmentUrl) throws Exception {
        switch (browser.toLowerCase()) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                chromeOptions = new ChromeOptions();
                chromeOptions.setExperimentalOption("useAutomationExtension", false);
                driver = new ChromeDriver(chromeOptions);
                break;
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;
            default:
                throw new Exception("Browser " + browser + " is not supported");
        }

        try {
            driver.get(environmentUrl);
        } catch (Exception e){
            e.fillInStackTrace();
        }

        googleSearchPage = new GoogleSearchPage(driver);
    }

    @AfterMethod(alwaysRun = true)
    public void afterMethod() {
        driver.quit();
    }
}

