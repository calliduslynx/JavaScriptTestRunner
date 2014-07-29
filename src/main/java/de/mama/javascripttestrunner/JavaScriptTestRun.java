package de.mama.javascripttestrunner;

import java.lang.annotation.Annotation;

import org.junit.Test;
import org.junit.runners.model.FrameworkMethod;

/**
 * this class represents one single test run of one single test method
 */
public class JavaScriptTestRun {
    private final String name;
    private final boolean successful;

    /**
     *
     * @param name
     *            the name of the javascript test
     * @param successful
     *            true if the test was successful
     */
    public JavaScriptTestRun(String name, boolean successful) {
        this.name = name;
        this.successful = successful;
    }

    @Override
    public String toString() {
        String success = successful ? " [PASSED]" : " [FAILED]";
        return name + success;
    }

    /**
     * returns a (partly mocked) instance of {@link FrameworkMethod}. This instance returns correct name and has correct behaviour for #invokeExplosively
     */
    public FrameworkMethod getFrameworkMethod() {
        return new MockedFrameworkMethod(name, successful);
    }

    /**
     * Helper class to mock {@link FrameworkMethod}. This is necessary to override behaviour for starting the test and getting correct test name
     */
    public static class MockedFrameworkMethod extends FrameworkMethod {
        private String name;
        private boolean successful;

        public MockedFrameworkMethod(String name, boolean successful) {
            super(null);
            this.name = name;
            this.successful = successful;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public Object invokeExplosively(Object target, Object... params) throws Throwable {
            if (!successful) {
                throw new JavaScriptException();
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
