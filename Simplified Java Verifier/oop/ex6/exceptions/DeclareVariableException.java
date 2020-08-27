package oop.ex6.exceptions;

/**
 * An abstract class that represents an error when the user declares a variable twice
 */
public class DeclareVariableException extends VariableException {
    /**
     * Constructor with A permanent message about already declare variable error.
     */
    public DeclareVariableException() {
        super("This variable already declared");
    }
}
