package oop.ex6.exceptions;

/**
 * This type of errors occurred when a method is called from the main scope.
 */
public class MethodCallException extends ScopeException {

    /**
     * Constructor with A permanent message
     */
    public MethodCallException() {
        super("Method has been called from the main scope");
    }
}
