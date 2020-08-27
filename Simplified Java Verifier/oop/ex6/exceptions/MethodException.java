package oop.ex6.exceptions;

/**
 * This type of errors occurred when the user try to define method inside method.
 */
public class MethodException extends ScopeException {
	/**
	 * Constructor with A permanent message about method inside method error.
	 */
	public MethodException() {
		super("Wrong method definition");
	}
}
