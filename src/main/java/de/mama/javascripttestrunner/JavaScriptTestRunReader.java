package de.mama.javascripttestrunner;

import java.util.List;

public interface JavaScriptTestRunReader {
    List<JavaScriptTestRun> readFrom(String jsTestUrl);

    void startUp();

    void cleanUp();
}
