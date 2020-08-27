package filesprocessing.Filters;

import java.io.File;

/**
 * This class represent the  "filter All" - which is the default filter and allows all files to pass
 */

public class AllFilter extends Filter {

	private static final int EXPECTED_PARAMETERS = 1;

	/*
	Constructor
	 */
	AllFilter() {
		expectedParameters = EXPECTED_PARAMETERS;
	}

	@Override
	public boolean isFiltered(File file) {
		return true;
	}
}
