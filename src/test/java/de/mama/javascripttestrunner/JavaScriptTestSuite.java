package de.mama.javascripttestrunner;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JavaScriptTestRunner.class)
public class JavaScriptTestSuite implements JavaScriptTestStarter {

    @Test
    public void runJsTests() {
    }

    @Override
    public String[] getJSTestUrls() {
        // return new String[]{
        // "file:///Users/calliduslynx/Documents/workspace/JavaScriptTestRunner/src/test/resources/test.html",
        // "file:///Users/calliduslynx/Documents/workspace/JavaScriptTestRunner/src/test/resources/test2.html"
        // };
        return new String[]{
                "file:///home/dawn-ubuntu/workspace/JavaScriptTestRunner/src/test/resources/test.html",
                "file:///home/dawn-ubuntu/workspace/JavaScriptTestRunner/src/test/resources/test2.html"
        };
    }
}
