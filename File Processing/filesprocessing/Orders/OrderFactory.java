package filesprocessing.Orders;

/**
 * this class represents a Order Factory which creates Order objects
 */
public class OrderFactory {
	private static final String SEPARATOR = "#";
	private static final String REVERSE_KEY = "REVERSE";
	private static final int NAME_LOCATION = 0;
	private static final String ABS = "abs";
	private static final String TYPE = "type";
	private static final String SIZE = "size";


	/**
	 * This method is given a string that contains the commands of the file's order and creates a new Order
	 * according to the given commands
	 *
	 * @param orderCmd- string that contains commands that determines the order of the files
	 * @return - the appropriate Order object that fits to the order name in the given commands list
	 * @throws OrderException - if the order pattern is invalid
	 */
	public static Order selectOrder(String orderCmd) throws OrderException {
		String[] orderParameters = orderCmd.split(SEPARATOR);
		String orderName = orderParameters[NAME_LOCATION];
		int numberOfParameters = orderParameters.length;
		Order order;

		switch (orderName) {
			case ABS:
				order = new AbsOrder();
				break;
			case TYPE:
				order = new TypeOrder();
				break;
			case SIZE:
				order = new SizeOrder();
				break;
			default:
				throw new OrderException();
		}
		if (thereIsREVERSE(orderParameters))
			order = new ReverseOrder(order);
		if (order.expectedParameters != numberOfParameters)
			throw new OrderException();
		return order;
	}

	/**
	 * creates an AbsOrder object
	 *
	 * @return an AbsOrder object
	 */
	public static Order selectDefault() {
		return new AbsOrder();
	}

	/*
	This method checks if the command that represents the reverse order exists in the given parameters and
	returns boolean type accordingly
	*/
	private static boolean thereIsREVERSE(String[] orderParameters) {
		String lastCmd = orderParameters[orderParameters.length - 1];
		return lastCmd.equals(REVERSE_KEY);
	}
}

