package oop.ex6.exceptions;

/**
 * This class represent a syntax errors in the code. when we use this kind of exception we need to send a
 * specific message about the error.
 */
public class SyntaxException extends CompilingException {

    /* Constructor */
    public SyntaxException(String msg){
        super(msg);
    }
}
