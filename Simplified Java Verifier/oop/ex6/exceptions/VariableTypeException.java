package oop.ex6.exceptions;

/**
 * this class extend the VariableError, this type of error is sent when there is an incorrect assignment
 * in the variable
 */
public class VariableTypeException extends VariableException {
    /**
     * Constructor with A permanent message about type variable error.
     */
    public VariableTypeException() {
        super("the value or parameter does not match to the variable");
    }

}
