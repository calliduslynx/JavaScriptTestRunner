package de.mama.javascripttestrunner;

import java.lang.annotation.Annotation;

import org.junit.Test;
import org.junit.runners.model.FrameworkMethod;

/**
 * this class represents one single test run of one single test method
 */
public class JavaScriptTestRun {
    private final String name;
    private final JavaScriptException exception;

    /**
     *
     * @param name
     *            the name of the javascript test
     * @param successful
     *            true if the test was successful
     */
    public JavaScriptTestRun(String name, boolean successful) {
        this(name, successful, null);
    }

    public JavaScriptTestRun(String name, boolean successful, String description) {
        this.name = name;
        exception = successful ? null : new JavaScriptException(description);
    }

    @Override
    public String toString() {
        String success = exception == null ? " [PASSED]" : " [FAILED]";
        return name + success;
    }

    /**
     * returns a (partly mocked) instance of {@link FrameworkMethod}. This instance returns correct name and has correct behaviour for #invokeExplosively
     */
    public FrameworkMethod getFrameworkMethod() {
        return new MockedFrameworkMethod(name, exception);
    }

    /**
     * Helper class to mock {@link FrameworkMethod}. This is necessary to override behaviour for starting the test and getting correct test name
     */
    public static class MockedFrameworkMethod extends FrameworkMethod {
        private String name;
        private JavaScriptException exception;

        public MockedFrameworkMethod(String name, JavaScriptException exception) {
            super(null);
            this.name = name;
            this.exception = exception;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public Object invokeExplosively(Object target, Object... params) throws Throwable {
            if (exception != null) {
                throw exception;
            }
            return null;
        }

        @Override
        public int hashCode() {
            return name.hashCode();
        }

        /**
         * expect that all tests are annotated with @Test, nothing else
         */
        @Override
        public <T extends Annotation> T getAnnotation(Class<T> annotationType) {
            if (Test.class.equals(annotationType)) {
                Test test = new Test() {
                    @Override
                    public Class<? extends Annotation> annotationType() {
                        return Test.class;
                    }

                    @Override
                    public Class<? extends Throwable> expected() {
                        return null;
                    }

                    @Override
                    public long timeout() {
                        return 0;
                    }

                };
                return (T) test;
            }
            return null;
        }

        /**
         * expect that all tests are annotated with @Test, nothing else
         */
        @Override
        public Annotation[] getAnnotations() {
            return new Annotation[]{
                    getAnnotation(Test.class)
            };
        }
    }
}
