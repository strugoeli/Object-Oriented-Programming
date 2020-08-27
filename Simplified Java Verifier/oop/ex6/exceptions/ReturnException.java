package oop.ex6.exceptions;

/**
 * This class represents one of the Scope errors. This exception is sent when the end of a method scoop is
 * missing a return statement
 */
public class ReturnException extends ScopeException {
    /**
     * Constructor with A permanent message about missing return statement.
     */
    public ReturnException() {
        super("Missing return statement ");
    }
}
