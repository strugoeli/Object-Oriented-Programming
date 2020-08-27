package filesprocessing.Orders;

import java.io.File;

/*
 * This class creates instance of an Order object and which acts the vice-versa of an given order
 */

class ReverseOrder extends Order {

	private Order reverseOrder;
	private static final int NEGATIVE_RESULT = -1;

	//Constructor
	ReverseOrder(Order order) {
		reverseOrder = order;
		expectedParameters = order.expectedParameters + 1;
	}

	@Override
	public int compare(File file1, File file2) {
		return NEGATIVE_RESULT * reverseOrder.compare(file1, file2);
	}
}
