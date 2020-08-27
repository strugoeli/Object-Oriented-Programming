package filesprocessing.Filters;

import java.io.File;

/*
This class implements  and requires and represents a filter by suffix of the file name

 */
class SuffixFilter extends Filter {
	private static final int EXPECTED_PARAMETERS = 2;
	private String suffix;

	/*
	 * Constructor method
	 *
	 * @param suffixInput - The given suffix(string)
	 */
	SuffixFilter(String suffixInput) {
		suffix = suffixInput;
		expectedParameters= EXPECTED_PARAMETERS;
	}

	public boolean isFiltered(File file) {
		return file.getName().endsWith(suffix);
	}
}
