package oop.ex6.exceptions;

/**
 * This class is an abstract class that manages all the errors in the code.
 */
public abstract class CompilingException extends Exception {

    /* Constructor. */
    CompilingException(String msg) { super(msg); }

}
