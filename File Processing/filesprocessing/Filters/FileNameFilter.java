package filesprocessing.Filters;

import java.io.File;

/*
 * This class represent filter which requires that the name of the given file equals to certain content
 */

class FileNameFilter extends Filter {

	private static final int EXPECTED_PARAMETERS = 2;
	private String fileName;

	/*
	 * Constructor method
	 *
	 * @param fileNameInput - given file name
	 */
	FileNameFilter(String fileNameInput) {
		fileName = fileNameInput;
		expectedParameters= EXPECTED_PARAMETERS;
	}

	@Override
	public boolean isFiltered(File file) {
		return file.getName().equals(fileName);

	}
}
