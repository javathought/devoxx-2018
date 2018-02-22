package io.github.javathought.devoxx;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.Ignore;
import org.junit.runner.RunWith;


@RunWith(Cucumber.class)
@CucumberOptions(strict = false, plugin = {"pretty", "json:target/cucumber-reports/rules.json" }
//, tags = {"@Todos"}
)
@Ignore
public class BDDLiveRunnerTest {

/*
    @ClassRule
    public static RunningMode setRunningMode() {
        return new RunningMode("live");
    }
*/


}
