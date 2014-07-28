package de.mama.javascripttestrunner;

import java.util.ArrayList;
import java.util.List;

import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;

public class JavaScriptTestRunner extends BlockJUnit4ClassRunner {
    private String[] jsTestUrls;
    private JavaScriptTestRunReader javaScriptTestRunReader;

    public JavaScriptTestRunner(Class<?> klass) throws InitializationError {
        super(klass);
        loadUrls(klass);

        javaScriptTestRunReader = new JavaScriptTestRunReader() {
            @Override
            public List<JavaScriptTestRun> readFrom(String jsTestUrl) {
                List<JavaScriptTestRun> runs = new ArrayList<>();
                runs.add(new JavaScriptTestRun("TEST 1 - " + jsTestUrl, true));
                runs.add(new JavaScriptTestRun("TEST 2 - " + jsTestUrl, true));
                runs.add(new JavaScriptTestRun("TEST 3 - " + jsTestUrl, true));
                return runs;
            }
        };
    }

    private void loadUrls(Class<?> klass) {
        JavaScriptTestStarter javaScriptTestStarter = getJavaScriptTestStarterInstance(klass);
        jsTestUrls = javaScriptTestStarter.getJSTestUrls();
    }

    private JavaScriptTestStarter getJavaScriptTestStarterInstance(Class<?> klass) {
        try {
            for (Class<?> interfaze : klass.getInterfaces()) {
                if (interfaze.equals(JavaScriptTestStarter.class)) {
                    return (JavaScriptTestStarter) createTest();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new RuntimeException();
    }

    @Override
    protected List<FrameworkMethod> getChildren() {
        List<FrameworkMethod> children = super.getChildren();
        children.clear();

        runJavascriptTests(children);

        return children;
    }

    private void runJavascriptTests(List<FrameworkMethod> children) {
        for (String jsTestUrl : jsTestUrls) {
            List<JavaScriptTestRun> testRuns = javaScriptTestRunReader.readFrom(jsTestUrl);
            for (JavaScriptTestRun testRun : testRuns) {
                children.add(testRun.getFrameworkMethod());
            }
        }
    }
}