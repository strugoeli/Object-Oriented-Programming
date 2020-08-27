import java.util.TreeSet;

/**
 * This class represents a wrapper for linked list of strings
 */


public class TreeSetWrapper {
	private static final int EMPTY = 0;

	private TreeSet<String> treeSet;

	/**
	 * default constructor
	 */

	TreeSetWrapper() {
		treeSet = new TreeSet<>();

	}

	/**
	 * @return tree set instance for delegation needs
	 */
	public TreeSet<String> getTreeSet() {
		return treeSet;
	}

	/**
	 * This method is given a value and searches for it in the collection
	 *
	 * @param searchVal Value to search for
	 * @return true if the value has been found and false otherwise
	 */
	public boolean contains(String searchVal) {
		return treeSet.contains(searchVal);
	}

	/**
	 * This method is given a value and tries to add it from the collection
	 *
	 * @param newVal - New value to add to the set
	 * @return true if the Value has been added and false otherwise
	 */
	public boolean add(String newVal) {
		return (!treeSet.contains(newVal)) && treeSet.add(newVal);
	}

	/**
	 * This method is given a value and searches for it in the collection
	 *
	 * @param toDelete Value to delete
	 * @return true if the value has been deleted and false otherwise
	 */
	public boolean delete(String toDelete) {
		return treeSet.remove(toDelete);
	}

	/**
	 * @return the size of the collection(the numbers of values)
	 */
	public int getSize() {
		return treeSet.size();
	}

	/**
	 * @return true if the set is empty and false otherwise
	 */
	public boolean isEmpty() {
		return (treeSet.size() == EMPTY);

	}
}
