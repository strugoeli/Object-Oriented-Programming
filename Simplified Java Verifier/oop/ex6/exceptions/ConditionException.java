package oop.ex6.exceptions;

/**
 * This class represent an errors that occurred when the condition inside of if or while is illegal.
 */
public class ConditionException extends CompilingException {
    /**
     * Constructor with A permanent message about illegal condition error.
     */
    public ConditionException(){
        super("Wrong condition expression");
    }

}
