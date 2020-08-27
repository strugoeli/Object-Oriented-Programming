package filesprocessing.Filters;

import java.io.File;

/*
 * This class represent filter TypeFilter type which requires that the given file hidden or not
 * depends on the given answer
 */
class HiddenFilter extends TypeFilters {

	/*
	 * Constructor method that uses TypeFilter
	 *
	 * @param answer -  is the file hidden/"YES" or not/"NO"
	 * @throws FilterException - in case the given string is invalid
	 */
	HiddenFilter(String answer) throws FilterException {
		super(answer);
	}

	@Override
	public boolean isFiltered(File file) {
		return isPositive() == file.isHidden();

	}
}
