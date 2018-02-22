package org.testcontainers.containers;

import org.junit.runner.Description;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class ContainerListener extends RunListener {

    //    private final GenericContainer watchedContainer;
    private static final Logger LOG = LoggerFactory.getLogger(ContainerListener.class);

    GenericContainer watchedContainer;
    private VncRecordingContainer vnc;
    //    static File recordingDir = new File("build/recording-" + System.currentTimeMillis());
    static File recordingDir = new File("target");


    //    public ContainerListener(BrowserWebDriverContainer navigateur) {
    public ContainerListener(GenericContainer chrome) {
        super();
        watchedContainer = chrome;
    }

/*
    @Override
    public void testRunStarted(Description description) throws Exception {
        super.testRunStarted(description);
    }

    @Override
    public void testRunFinished(Result result) throws Exception {
        super.testRunFinished(result);
    }
*/

    @Override
    public void testStarted(Description description) throws Exception {
        if (depth(description) == 2) {

            vnc = new VncRecordingContainer(watchedContainer) {
                @Override
                protected void succeeded(Description description) {
                    saveRecordingToFile(new File(recordingDir, "PASSED-" + description.getDisplayName() + ".flv"));
                    super.succeeded(description);
                }

                @Override
                protected void failed(Throwable e, Description description) {
                    saveRecordingToFile(new File(recordingDir, "FAILED-" + description.getDisplayName() + ".flv"));
                    super.failed(e, description);
                }
            };
            vnc.start();
            LOG.info("{} started.", description.getDisplayName());
        }
        super.testStarted(description);

    }



    @Override
    public void testFinished(Description description) throws Exception {
//        ((Scenario) description.getMethodName();
        if (depth(description) == 2 && vnc != null) {  // if test has failed, vnc has been stopped already
            vnc.succeeded(description);
            vnc.stop();
            LOG.info("{} finished.", description.getDisplayName());
        }
        super.testFinished(description);
    }

    private int depth(Description description) {
        int depth = 1;
        Description curr = description;

        while (!curr.getChildren().isEmpty()) {
            depth++;
            curr = curr.getChildren().get(0);
        }
        return depth;
    }

    @Override
    public void testFailure(Failure failure) throws Exception {
        Description description = failure.getDescription();
        if (depth(description) == 2) {
            vnc.failed(failure.getException(), description);
            vnc.stop();
            vnc = null;

            LOG.info("{} failed.", description.getDisplayName());
        }
        super.testFailure(failure);
    }


}
