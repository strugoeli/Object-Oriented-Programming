package filesprocessing.Filters;

import java.io.File;


/*
 * This class represent filter Size filter type which that requires that the given file is between certain
 * range of sizes
 */

class BetweenFilter extends SizeFilters {
	private static final int EXPECTED_PARAMETERS = 3;

	/*
	 * Constructor that uses SizeFilter and finally does a validity check on the given parameters
	 *
	 * @param lowerBound - The lower bound of the filter's size (written in string)
	 * @param upperBound - The lower bound of the filter's size (written in string)
	 * @param numOfPara  -  The number of the expected parameters for this filter
	 * @throws FilterException - if the the range ot the number of parameters are invalid
	 */
	BetweenFilter(String lowerBound, String upperBound) throws FilterException {
		super(lowerBound, upperBound);
		if (!checkRange())
			throw new FilterException();
		expectedParameters = EXPECTED_PARAMETERS;
	}

	/*
	 Checks if the order of the range is valid
	  */
	private boolean checkRange() {
		return size1 <= size2;
	}

	@Override
	public boolean isFiltered(File file) {
		double fileSize = file.length() / K_BYTE;
		return (size1 <= fileSize && fileSize <= size2);
	}
}
