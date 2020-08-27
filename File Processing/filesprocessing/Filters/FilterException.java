package filesprocessing.Filters;

import filesprocessing.Type1Exceptions;

/**
 * This class represents Exception that relates to the Filter types
 */

public class FilterException extends Type1Exceptions {
	private static final String FILTER_EXCEPTION = "Bad format of the Commands File";


	/**
	 * Default Constructor
	 */
	public FilterException() {
		super(FILTER_EXCEPTION);
	}

	/**
	 * Constructor which receives Informative message to be printed
	 */
	public FilterException(String message) {
		super(message);
	}

}
