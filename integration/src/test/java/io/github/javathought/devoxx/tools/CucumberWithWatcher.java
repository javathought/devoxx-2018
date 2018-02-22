package io.github.javathought.devoxx.tools;

import cucumber.api.junit.Cucumber;
import io.github.javathought.devoxx.BDDRunnerTest;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.InitializationError;
import org.testcontainers.containers.ContainerListener;

import java.io.IOException;

public class CucumberWithWatcher extends Cucumber {

    public CucumberWithWatcher(Class clazz) throws InitializationError, IOException {
        super(clazz);
    }

    @Override
    public void run(RunNotifier notifier) {
        notifier.addListener(new ContainerListener(BDDRunnerTest.navigateur));
        super.run(notifier);
    }
}
