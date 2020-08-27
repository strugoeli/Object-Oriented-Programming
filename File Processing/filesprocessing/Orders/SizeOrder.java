package filesprocessing.Orders;

import java.io.File;

/*
 * This class determines the order of the files by there size
 */
class SizeOrder extends Order {
	private static final int EQUAL = 0;
	private static final int EXPECTED_PARAMETERS = 1;

	/*
	Constructor
	 */
	SizeOrder() {
		expectedParameters = EXPECTED_PARAMETERS;
	}

	@Override
	public int compare(File file1, File file2) {
		int compareResult = Long.compare(file1.length(), file2.length());
		if (compareResult == EQUAL)
			return compareAbs(file1, file2);
		return compareResult;
	}
}


