package filesprocessing.Filters;

import java.io.File;

/**
 * This class represent filter which requires that the  name of the given file  contains certain
 * content
 */

public class ContainFilter extends Filter {

	private static final int EXPECTED_PARAMETERS = 2;
	private String content;

	/*
	 * Constructor method
	 */
	ContainFilter(String str) {
		content = str;
		expectedParameters= EXPECTED_PARAMETERS;
	}

	@Override
	public boolean isFiltered(File file) {
		return file.getName().contains(content);

	}
}
