package oop.ex6.exceptions;

/**
 * Class that manage all the error related to final variable.
 * Means, if the user will try to change a final variable.
 */
public class VariableFinalException extends VariableException {
    /**
     * Constructor with A permanent message about final variable error.
     */
    public VariableFinalException() {
        super("You cant change a final variable");
    }
}
