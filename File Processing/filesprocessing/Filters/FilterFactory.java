package filesprocessing.Filters;

/**
 * This class represents a Filter Factory that creates Filter Objects
 */
public class FilterFactory {

	/*Constant*/
	private static final String GREATER_THAN = "greater_than";
	private static final String BETWEEN = "between";
	private static final String NEGATIVE_KEY = "NOT";
	private static final String SMALLER_THAN = "smaller_than";
	private static final String FILE = "file";
	private static final String CONTAINS = "contains";
	private static final String PREFIX = "prefix";
	private static final String SUFFIX = "suffix";
	private static final String WRITABLE = "writable";
	private static final String EXECUTABLE = "executable";
	private static final String HIDDEN = "hidden";
	private static final String ALL = "all";
	private static final String SEPARATOR = "#";


	/**
	 * This method is given a string that contains the commands of files filter and creates a new
	 * Filter object according to the given commands
	 *
	 * @param filterCmd - string that contains the commands that determines the filter condition
	 * @return - the appropriate Filter object that fits to the filter name in the given commands list
	 * @throws FilterException - if the filter pattern is invalid
	 */
	public static Filter selectFilter(String filterCmd) throws FilterException {

		String[] filterParameters = filterCmd.split(SEPARATOR);
		String filterName = filterParameters[0];
		int numOfParameters = filterParameters.length;
		Filter filter;

		try {
			switch (filterName) {

				case GREATER_THAN:
					filter = new GreaterThanFilter(filterParameters[1]);
					break;
				case BETWEEN:
					filter = new BetweenFilter(filterParameters[1], filterParameters[2]);
					break;
				case SMALLER_THAN:
					filter = new SmallerThanFilter(filterParameters[1]);
					break;
				case FILE:
					filter = new FileNameFilter(filterParameters[1]);
					break;
				case CONTAINS:
					filter = new ContainFilter(filterParameters[1]);
					break;
				case PREFIX:
					filter = new PrefixFilter(filterParameters[1]);
					break;
				case SUFFIX:
					filter = new SuffixFilter(filterParameters[1]);
					break;
				case WRITABLE:
					filter = new WritableFilter(filterParameters[1]);
					break;
				case EXECUTABLE:
					filter = new ExecutableFilter(filterParameters[1]);
					break;
				case HIDDEN:
					filter = new HiddenFilter(filterParameters[1]);
					break;
				case ALL:
					filter = new AllFilter();
					break;

				default:
					throw new FilterException();
			}
		} catch (IndexOutOfBoundsException e) {
			throw new FilterException();
		}
		if (thereIsNegationKey(filterParameters))
			filter = new NotFilter(filter);
		if (filter.expectedParameters != numOfParameters)
			throw new FilterException();
		return filter;
	}


	/**
	 * creates a default Filter object
	 *
	 * @return a new default Filter object
	 */
	public static Filter selectDefault() {
		return new AllFilter();
	}

	/*
	This method checks if the command that represents the negate filter exists in the given parameters and
	returns boolean type accordingly
	*/
	private static boolean thereIsNegationKey(String[] filterParameters) {
		String lastCmd = filterParameters[filterParameters.length - 1];
		return lastCmd.equals(NEGATIVE_KEY);
	}
}

