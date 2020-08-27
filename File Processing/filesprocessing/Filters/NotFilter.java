package filesprocessing.Filters;

import java.io.File;

/**
 * This class creates a negative filter.
 */

public class NotFilter extends Filter {
	private Filter filter;

	/*
	 * Constructor method
	 *
	 * @param neg_filter - the original filter
	 */
	NotFilter(Filter neg_filter) {
		filter = neg_filter;
		expectedParameters = filter.expectedParameters + 1;
	}

	@Override
	public boolean isFiltered(File file) {
		return !filter.isFiltered(file);
	}
}
