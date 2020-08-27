package filesprocessing.Filters;

import java.io.File;

/*
 * This class represent Filter that that uses size comparison
 */
abstract class SizeFilters extends Filter {
	static final double K_BYTE = 1024;
	double sizeBound;
	double size1, size2;

	/*
	 * Constructor for two parameters
	 *
	 * @param sizeInput1- a given size that compare with
	 * @param sizeInput2- a given size that compare with
	 * @throws FilterException - in case one of the given size is negative
	 */
	SizeFilters(String sizeInput1, String sizeInput2) throws FilterException {

		size1 = Double.valueOf(sizeInput1);
		size2 = Double.valueOf(sizeInput2);
		if (isNegativeNumber(size1) || isNegativeNumber(size2))
			throw new FilterException();
	}

	/*
	 * Constructor for one parameters
	 *
	 * @param sizeInput - a given size that compare with
	 * @throws FilterException - in case the given size is negative
	 */
	SizeFilters(String sizeInput) throws FilterException {
		sizeBound = Double.valueOf(sizeInput);
		if (isNegativeNumber(sizeBound))
			throw new FilterException();

	}

	// checks if the given number is negative
	private boolean isNegativeNumber(double size) {
		return size < 0;
	}


	@Override
	public abstract boolean isFiltered(File file);


}

