package filesprocessing.Filters;

import java.io.File;

/*
 * This class represents filter TypeFilter type which requires that the given file writable or not
 * depends on the given answer
 */

class WritableFilter extends TypeFilters {
	/*
	 * Constructor method that uses TypeFilter
	 *
	 * @param answer - is the writable/"YES" or not/"NO"
	 * @throws FilterException - in case the given string is invalid
	 */
	WritableFilter(String answer) throws FilterException {
		super(answer);
	}

	@Override
	public boolean isFiltered(File file) {
		return isPositive() == file.canWrite();
	}
}
