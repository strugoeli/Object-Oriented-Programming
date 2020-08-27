package filesprocessing.Orders;

import java.io.File;

/*
 * This class determines the order of the files by there type
 */
class TypeOrder extends Order {
	private static final String TYPE_DOES_NOT_APPEAR = "", PERIOD_MARK = ".";
	private static final int NOT_FOUND = -1;
	private static final int EXPECTED_PARAMETERS = 1;

	/*
	Constructor
	*/
	TypeOrder() {
		expectedParameters = EXPECTED_PARAMETERS;
	}

	@Override
	public int compare(File file1, File file2) {

		int comparisonResult = getFileType(file1).compareTo(getFileType(file2));
		if (comparisonResult == 0)
			return compareAbs(file1, file2);
		return comparisonResult;
	}

	/*
	This method is given a file and returns it's filename extension if it's valid and empty string otherwise
	 */
	private String getFileType(File file) {
		String fileName = file.getName();
		int periodIndex = fileName.lastIndexOf(PERIOD_MARK);
		if (periodIndex == NOT_FOUND || fileName.endsWith(PERIOD_MARK))
			return TYPE_DOES_NOT_APPEAR;
		return fileName.substring(periodIndex + 1);
	}


}



