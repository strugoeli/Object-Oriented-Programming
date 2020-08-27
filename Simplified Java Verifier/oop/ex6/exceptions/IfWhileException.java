package oop.ex6.exceptions;

/**
 * this type of errors occurred when the user try to define if or while scope in the main scope.
 */
public class IfWhileException extends ScopeException {
    /**
     * Constructor with A permanent message about declare loops in the main scope error.
     */
    public IfWhileException() {
        super("Create if/while scope only inside other scope");
    }
}
