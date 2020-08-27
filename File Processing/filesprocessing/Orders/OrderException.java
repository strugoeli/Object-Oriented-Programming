package filesprocessing.Orders;

import filesprocessing.Type1Exceptions;

/**
 * This class represents Exception that relates to the Order types
 */

public class OrderException extends Type1Exceptions {

	private static final String ORDER_EXCEPTION = "Bad format of the Commands File";

	/*
	Default Constructor
	 */
	OrderException() {
		super(ORDER_EXCEPTION);
	}

	/**
	 * Constructor which receives Informative message to be printed
	 */
	public OrderException(String message) {
		super(message);
	}
}

