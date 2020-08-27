package oop.ex6.exceptions;

/**
 * This class represents the error that sent when the brackets in the code are not balanced. means, the
 * number of opening brackets, is not equal to the number of closing brackets in a specific scope
 * or an entire file.
 */
public class UnbalancedBracketsException extends ScopeException {

    /**
     * Constructor with A permanent message about unbalanced brackets exception.
     */
    public UnbalancedBracketsException() {
        super("Unbalanced brackets");
    }
}
