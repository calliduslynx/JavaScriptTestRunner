package de.mama.javascripttestrunner;

import java.lang.annotation.Annotation;

import org.junit.Test;
import org.junit.runners.model.FrameworkMethod;

public class JavaScriptTestRun {
    public final String name;
    public final boolean successful;

    public JavaScriptTestRun(String name, boolean successful) {
        this.name = name;
        this.successful = successful;
    }

    public FrameworkMethod getFrameworkMethod() {
        return new MockedFrameworkMethod(name, successful);
    }

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

        @Override
        public Annotation[] getAnnotations() {
            return new Annotation[]{
                    getAnnotation(Test.class)
            };
        }
    }
}
