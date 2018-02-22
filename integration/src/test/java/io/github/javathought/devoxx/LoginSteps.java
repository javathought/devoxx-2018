package io.github.javathought.devoxx;

import cucumber.api.java8.En;
import cucumber.api.java8.Fr;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class LoginSteps implements Fr, En {

    public static final String USERNAME_FIELD_ID = "username";
    public static final String PASSWORD_FIELD_ID = "password";
    public static final String SUBMIT_BUTTON_ID = "submit";
    private WebDriver driver;
    private String baseUrl;
    private String username;
    private WebDriverWait wait1;
    private AppSteps withAppSteps;


    public LoginSteps(AppSteps appSteps) {
        
        withAppSteps = appSteps;
        driver = withAppSteps.getDriver();
        wait1 = new WebDriverWait(driver, 5);
       
        
/*
        After(() -> {
            if (driver != null) {
                driver.quit();
            }
        });
*/
        
        Given("^l'url de l'application \"([^\"]*)\"$", (String url) -> {
            withAppSteps.setBaseUrl(url);
            driver.manage().deleteAllCookies();
            driver.get(withAppSteps.getBaseUrl());
        });

        When("^je me connecte avec le compte \"([^\"]*)\" et le mot de passe \"([^\"]*)\"$", (String user, String pwd) -> { username = user;
            wait1.until(ExpectedConditions.visibilityOf(driver.findElement(By.id(USERNAME_FIELD_ID))));
            wait1.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.id(USERNAME_FIELD_ID))));
//            driver.findElement(By.id(USERNAME_FIELD_ID)).clear();
            driver.findElement(By.id(USERNAME_FIELD_ID)).sendKeys(user);
//            driver.findElement(By.id(PASSWORD_FIELD_ID)).clear();
            driver.findElement(By.id(PASSWORD_FIELD_ID)).sendKeys(pwd);
            driver.findElement(By.id(SUBMIT_BUTTON_ID)).click();        
        });

        Then("^la connexion est acceptée$", () -> {
            wait1.until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver d) {
                    return ! d.findElement(By.id("connected_user")).getText().isEmpty();
                }
            });
            assertEquals(username, driver.findElement(By.id("connected_user")).getText());        });

        Then("^la connexion est refusée$", () -> {
            // Write code here that turns the phrase above into concrete actions
            wait1.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("login-error"))));
            assertThat(driver.findElement(By.id("login-error")).getText(), containsString("Username or password is incorrect"));
            ;
        });
        And("^je ferme le navigateur$", () -> {
            if (driver != null) {
                driver.quit();
            }
        });
    }



//    @After
//    public void tearDownScenario(Scenario sc) {
//        if (sc.isFailed()) {
//            BDDRunnerTest.watcher.
//        }
//
//    }

}
