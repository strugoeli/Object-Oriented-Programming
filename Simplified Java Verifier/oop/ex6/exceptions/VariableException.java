package oop.ex6.exceptions;

/**
 * This class is abstract class that manges all the errors that related to variable. also, this class
 * extend the CompilingException .
 */
public abstract class VariableException extends CompilingException {
    /* Constructor */
    VariableException(String msg){
        super(msg);
    }
}
