package de.mama.javascripttestrunner;

import java.io.File;

import org.junit.runner.RunWith;

@RunWith(JavaScriptTestRunner.class)
public class JavaScriptTestSuite implements JavaScriptTestStarter {
    @Override
    public String[] getJSTestUrls() {
        if (new File("/Users/").exists()) {
            return new String[]{
                    "file:///Users/calliduslynx/Documents/workspace/JavaScriptTestRunner/src/test/resources/test.html",
                    "file:///Users/calliduslynx/Documents/workspace/JavaScriptTestRunner/src/test/resources/test2.html"
            };
        } else {
            return new String[]{
                    "file:///home/dawn-ubuntu/workspace/JavaScriptTestRunner/src/test/resources/test.html",
                    "file:///home/dawn-ubuntu/workspace/JavaScriptTestRunner/src/test/resources/test2.html"
            };
        }
    }
}