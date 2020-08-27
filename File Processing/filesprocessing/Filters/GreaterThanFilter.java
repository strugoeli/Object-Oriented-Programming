package filesprocessing.Filters;

import java.io.File;


/*
 * This class represent filter Size filter type which that requires that the given file's size is
 * strictly greater than the given number of k-bytes
 */

class GreaterThanFilter extends SizeFilters {
	private static final int EXPECTED_PARAMETERS = 2;


	/*
	 * Constructor method that uses SizeFilter and finally does a validity check on the given parameters
	 *
	 * @param lowerBound -The lower bound of the filter's size (written in string)
	 * @param numOfPara  -The number of the given parameters for this filter
	 * @throws FilterException in case the number of parameters is invalid
	 */

	GreaterThanFilter(String lowerBound) throws FilterException {
		super(lowerBound);
		expectedParameters=EXPECTED_PARAMETERS;

	}

	@Override
	public boolean isFiltered(File file) {
		double fileSize = file.length() / K_BYTE;
		return fileSize > sizeBound;

	}
}
