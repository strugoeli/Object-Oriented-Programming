package filesprocessing.Orders;

import java.io.File;
import java.util.Comparator;

/**
 * This abstract class implements the interface Comparator and represents a certain order of the files
 */
public abstract class Order implements Comparator<File> {
	protected int expectedParameters;



	@Override
	public abstract int compare(File file1, File file2);

	/*
	 * This methods compares two files  by there absolute name (using File.getAbsolutePath() ).
	 *
	 * @return  signum function - which is defined to return one of -1, 0, or 1
	 * -1 means less than, 0 means equals and 1 mean greater than.
	 */

	int compareAbs(File file1, File file2) {
		return file1.getAbsolutePath().compareTo(file2.getAbsolutePath());
	}

}







