package de.mama.javascripttestrunner;

import java.util.List;

import org.junit.runner.manipulation.Filter;
import org.junit.runner.manipulation.NoTestsRemainException;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;

/**
 * With this test runner it is possible to run JavaScript unit tests like unit tests in java. So it is possible to integrate them easily without installing
 * node.js for example
 *
 * To provide the test url to the runner it is required that the test class implements {@link JavaScriptTestStarter}
 */
public class JavaScriptTestRunner extends BlockJUnit4ClassRunner {
    private String[] jsTestUrls;
    private JavaScriptTestRunReader javaScriptTestRunReader;

    public JavaScriptTestRunner(Class<?> klass) throws InitializationError {
        super(klass);
        jsTestUrls = getJSTestUrls(klass);

        javaScriptTestRunReader = new TestRunReaderMock();

    }

    /**
     * returns urls out of the test class
     */
    private String[] getJSTestUrls(Class<?> klass) {
        return getJavaScriptTestStarterInstance(klass).getJSTestUrls();
    }

    private JavaScriptTestStarter getJavaScriptTestStarterInstance(Class<?> klass) {
        try {
            for (Class<?> interfaze : klass.getInterfaces()) {
                if (interfaze.equals(JavaScriptTestStarter.class)) {
                    return (JavaScriptTestStarter) createTest();
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        throw new RuntimeException("the test class " + klass + " doesn't implement the interface " + JavaScriptTestStarter.class);
    }

    /**
     * remove all java tests, fill up with javascript tests -> for this all js tests will be started at this point of time
     */
    @Override
    protected List<FrameworkMethod> getChildren() {
        List<FrameworkMethod> children = super.getChildren();
        children.clear();

        runJavascriptTests(children);

        return children;
    }

    private void runJavascriptTests(List<FrameworkMethod> children) {
        javaScriptTestRunReader.startUp();
        for (String jsTestUrl : jsTestUrls) {
            List<JavaScriptTestRun> testRuns = javaScriptTestRunReader.readFrom(jsTestUrl);
            for (JavaScriptTestRun testRun : testRuns) {
                children.add(testRun.getFrameworkMethod());
            }
        }
        javaScriptTestRunReader.cleanUp();
    }

    @Override
    public void filter(Filter filter) throws NoTestsRemainException {
    }
}