package io.github.javathought.devoxx.bdd;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(strict = false, plugin = {"pretty", "json:target/cucumber-reports/rules.json" })
public class BDDRunnerTest {

}
