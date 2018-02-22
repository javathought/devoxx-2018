package io.github.javathought.devoxx;

import cucumber.api.java8.En;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.Assert.assertEquals;

public class TodosSteps implements En {

    private AppSteps withAppSteps;
    private WebDriver driver;
    private WebDriverWait wait;

    public TodosSteps(AppSteps appSteps) {

        withAppSteps = appSteps;
        driver = withAppSteps.getDriver();
        wait = new WebDriverWait(driver, 3);
        
        When("^je vais à la page \"([^\"]*)\"$", (String page) -> {
           driver.get(withAppSteps.getBaseUrl().concat(page));
           wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.id("create_task"))));
           assertEquals("Liste des tâches", driver.getTitle());
        });
        When("^je clique sur le menu \"([^\"]*)\"$", (String menu) -> {
            // Write code here that turns the phrase above into concrete actions
            driver.findElement(By.id(menu)).click();
            wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.id("create_task"))));
            wait.until(ExpectedConditions.titleIs("Liste des tâches"));
            assertEquals("Liste des tâches", driver.getTitle());
        });


    }
}
