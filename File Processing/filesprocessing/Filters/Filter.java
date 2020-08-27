package filesprocessing.Filters;

import java.io.File;

/**
 * This abstract class represents filter of files by some parameters
 */

public abstract class Filter {
	protected int expectedParameters;

	/**
	 * This method checks either the given file fits to the filter's conditions or not
	 *
	 * @param file - file to be checked
	 * @return true if the given file passes the filter and false otherwise
	 */
	public abstract boolean isFiltered(File file);
}
