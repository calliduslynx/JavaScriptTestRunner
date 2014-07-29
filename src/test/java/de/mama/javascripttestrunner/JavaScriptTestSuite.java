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
        return new String[]{
                "file:///home/dawn-ubuntu/workspace/JavaScriptTestRunner/src/test/resources/test.html"
                // "file:///Users/calliduslynx/Documents/workspace/planme/src/main/webapp/javascript-testing/test2.html"
        };
    }
}
