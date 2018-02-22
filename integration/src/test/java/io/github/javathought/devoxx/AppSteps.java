package io.github.javathought.devoxx;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.concurrent.TimeUnit;

//import static io.github.javathought.devoxx.BDDRunnerTest.navigateur;

public class AppSteps {

    private static boolean initialized = false;
    private WebDriver driver;
    private String baseUrl;
    private String runningMode;



    public AppSteps() {

        runningMode = "live";
        runningMode = "container";

        if ("container".equalsIgnoreCase(runningMode)) {

            driver = BDDRunnerTest.navigateur.getWebDriver();

            baseUrl = "https://" + BDDRunnerTest.navigateur.getTestHostIpAddress() + ":9090/";
//        baseUrl = "https://" + "192.168.99.1" + ":9090/";
//        baseUrl = "https://docker.for.mac.localhost:9090";
        } else {
//            ChromeDriverManager.getInstance().setup();
            System.setProperty("webdriver.navigateur.driver", "/Applications/chromedriver");
            System.setProperty("webdriver.gecko.driver", "/Applications/geckodriver");
//            driver = new ChromeDriver();
//            System.setProperty("webdriver.FIREFOX.driver", "/Applications/chromedriver");
            driver = new FirefoxDriver();
            baseUrl = "https://localhost:9090";

        }

        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

        initialized = true;


    }

    public WebDriver getDriver() {
        return driver;
    }

    public void setBaseUrl(String baseUrl) {
//        this.baseUrl = baseUrl;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

}
