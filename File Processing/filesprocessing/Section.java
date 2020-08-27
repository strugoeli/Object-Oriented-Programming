
package filesprocessing;

import filesprocessing.Filters.*;
import filesprocessing.Orders.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

/*
 * This class represents a section that contains Filter and Order objects
 */

class Section {

	/*Data Members */

	private Filter filter;
	private Order order;
	private String filterCmd;
	private String orderCmd;
	private int filterIndex;
	private int orderIndex;

	/*
	Constructor
	 */
	Section(String filterCommands, String orderCommands, int filterLine, int orderLine) {
		filterCmd = filterCommands;
		orderCmd = orderCommands;
		filterIndex = filterLine;
		orderIndex = orderLine;
	}

	/*
	Generates the filter object by using FilterFactory and prints Waring in line"X" case of FilterException
	 */
	private void generateFilter() {
		try {
			filter = FilterFactory.selectFilter(filterCmd);
		} catch (FilterException e) {
			e.printWarning(filterIndex);
			filter = FilterFactory.selectDefault();
		}
	}

	/*
	Generates the order object by using FilterFactory and prints Waring in line"X" case of FilterException
	 */
	private void generateOrder() {
		try {
			order = OrderFactory.selectOrder(orderCmd);
		} catch (OrderException e) {
			e.printWarning(orderIndex);
			order = OrderFactory.selectDefault();
		}
	}

	/*
	Creates lists of files that have passed the filter
	 */
	private File[] filterFileList(File[] files) {
		ArrayList<File> filteredFiles = new ArrayList<>();
		for (File file : files) {
			if (!file.isDirectory() && this.filter.isFiltered(file)) {
				filteredFiles.add(file);
			}
		}
		return filteredFiles.toArray(new File[0]);
	}

	/*
	This method generating the this filter and this order and returns filtered and sorted list of files
	 */
	File[] generateOutput(File[] files) {
		generateFilter();
		generateOrder();
		File[] filtered = filterFileList(files);
		return sortFileList(filtered);
	}

	/*
	Sorts the given list of files and returns it sorted
	 */
	private File[] sortFileList(File[] files) {
		Arrays.sort(files, order);
		return files;
	}
}

