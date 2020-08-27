package oop.ex6.exceptions;

/*This class represent exception that occurred inside Scope, or represent to one of the inner scope */
abstract class ScopeException extends CompilingException {

    /*Constructor */
    ScopeException(String msg) {
        super(msg);
    }
}
