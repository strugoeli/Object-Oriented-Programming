package oop.ex6.exceptions;

/**
 * This class represent error occurred when the user try to define new scope in bad format.
 */
public class FormatScopeException extends ScopeException {
    /**
     * Constructor with A permanent message about wrong format scope.
     */
    public FormatScopeException() {
        super("Wrong scope pattern (if/while/method) name");
    }
}
