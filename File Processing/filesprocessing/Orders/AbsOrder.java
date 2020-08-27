package filesprocessing.Orders;

import java.io.File;

/*
 * This class determines the order of two files by absolute name
 */
class AbsOrder extends Order {

	private static final int EXPECTED_PARAMETERS = 1;

	/*
	Constructor
	 */
	AbsOrder() {
		expectedParameters = EXPECTED_PARAMETERS;
	}


	@Override
	public int compare(File file1, File file2) {
		return compareAbs(file1, file2);
	}
}
