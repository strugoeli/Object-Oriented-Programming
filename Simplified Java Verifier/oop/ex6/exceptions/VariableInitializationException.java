package oop.ex6.exceptions;

/**
 * This class represents the error that sent when the user attempts to use a non-initialized variable.
 */
public class VariableInitializationException extends VariableException {
    /**
     * Constructor with A permanent message about initial variable error.
     */
    public VariableInitializationException() {
        super("The variable is not initialize");
    }
}
