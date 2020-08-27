package filesprocessing.Filters;

import java.io.File;
/*
This class implements  and requires and represents a filter by the prefix of the file name
 */


class PrefixFilter extends Filter {
	private static final int EXPECTED_PARAMETERS = 2;
	private String prefix;

	/*
	 * Constructor method
	 *
	 * @param prefixInput- The given prefix(string)
	 */
	PrefixFilter(String prefixInput) {
		prefix = prefixInput;
		expectedParameters= EXPECTED_PARAMETERS;
	}

	@Override
	public boolean isFiltered(File file) {
		return file.getName().startsWith(prefix);


	}
}
