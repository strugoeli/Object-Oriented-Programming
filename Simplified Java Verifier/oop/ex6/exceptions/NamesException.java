package oop.ex6.exceptions;

/**
 * This class represents the error that sent when there is a problem with the name of a variable or method
 */
public class NamesException extends CompilingException {
    /**
     * Constructor with A permanent message about illegal name.
     */
    public NamesException() {
        super("This name is illegal");
    }
}
