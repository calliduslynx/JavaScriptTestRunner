package de.mama.javascripttestrunner;

import java.util.ArrayList;
import java.util.List;

public class TestRunReaderMock implements JavaScriptTestRunReader {
    @Override
    public List<JavaScriptTestRun> readFrom(String jsTestUrl) {
        List<JavaScriptTestRun> runs = new ArrayList<>();
        runs.add(new JavaScriptTestRun("a.1", true));
        runs.add(new JavaScriptTestRun("a.2", true));
        runs.add(new JavaScriptTestRun("a.3", true));
        runs.add(new JavaScriptTestRun("a.1", true));
        return runs;
    }

    @Override
    public void startUp() {
    }

    @Override
    public void cleanUp() {
    }
}
