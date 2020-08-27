package filesprocessing;

/**
 * This class represents Type1 Exceptions
 */
public class Type1Exceptions extends Exception {

	private static final String LINE_MSG = "Warning in line ";

	/**
	 * Constructor
	 *
	 * @param exception - Informative message about the exception
	 */
	public Type1Exceptions(String exception) {
		super(exception);
	}

	/**
	 * This method is given a certain line number and print some message relating to the line number
	 *
	 * @param line - line number in some file that contains commands
	 */
	public void printWarning(int line) {
		System.err.println(LINE_MSG + line);
	}
}
