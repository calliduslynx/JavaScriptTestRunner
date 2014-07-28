package de.mama.javascripttestrunner;

/**
 * class representing an exception occured during js tests
 */
public class JavaScriptException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public JavaScriptException() {
        super("This JavaScript Test failed");
    }
}