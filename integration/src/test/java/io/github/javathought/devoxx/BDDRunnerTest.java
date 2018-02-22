package io.github.javathought.devoxx;

import cucumber.api.CucumberOptions;
import io.github.javathought.devoxx.run.ApiServer;
import io.github.javathought.devoxx.tools.CucumberWithWatcher;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.runner.RunWith;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testcontainers.containers.BrowserWebDriverContainer;
import org.testcontainers.containers.Network;

import java.io.IOException;


@RunWith(CucumberWithWatcher.class)
@CucumberOptions(strict = false, plugin = {"pretty", "json:target/cucumber-reports/rules.json" }
//, tags = {"@ignore"}
)
public class BDDRunnerTest {

    public static String jdbcUrl = "jdbc:tc:mysql:5.7://hostname/databasename?" +
            "TC_INITFUNCTION=io.github.javathought.devoxx.run.InitDB::initDB";


    @ClassRule
    public static BrowserWebDriverContainer navigateur = new BrowserWebDriverContainer<>()
            .withDesiredCapabilities(DesiredCapabilities.firefox())
            .withNetwork(Network.SHARED)
            .withNetworkAliases("vnchost")
            .withRecordingMode(BrowserWebDriverContainer.VncRecordingMode.SKIP, null)
//            .withRecordingFileFactory(new CucumberRecordingFileFactory())
            ;


    @BeforeClass
    public static void startHttp() throws IOException {
        ApiServer.start();
    }

}
