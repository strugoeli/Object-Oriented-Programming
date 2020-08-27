import java.util.TreeSet;

public class CollectionFacadeSet implements SimpleSet {

	private java.util.Collection<java.lang.String> collection;


	/**
	 * Creates a new facade wrapping the specified collection.
	 *
	 * @param collection - The Collection to wrap.
	 */

	public CollectionFacadeSet(java.util.Collection<java.lang.String> collection) {
		this.collection = collection;
		handleDuplicates();
	}

	/*
	 * This method handles with duplications of the collection by using TreeSet Java's data structure
	 */
	private void handleDuplicates() {
		TreeSet<String> treeSet = new TreeSet<>(collection);
		collection.clear();
		collection.addAll(treeSet);
	}

	/**
	 * This method is given a value and tries to add it from the collection
	 *
	 * @param newValue New value to add to the set
	 * @return true if the Value has been added and false otherwise
	 */
	public boolean add(java.lang.String newValue) {
		return !contains(newValue) && this.collection.add(newValue);
	}

	/**
	 * This method is given a value and searches for it in the collection
	 *
	 * @param searchVal Value to search for
	 * @return true if the value has been found and false otherwise
	 */
	public boolean contains(java.lang.String searchVal) {
		return this.collection.contains(searchVal);
	}

	/**
	 * This method is given a value and tries to delete it from the collection
	 *
	 * @param toDelete Value to delete
	 * @return true if the value has been deleted and false otherwise
	 */
	public boolean delete(java.lang.String toDelete) {
		return contains(toDelete) && this.collection.remove(toDelete);

	}

	/**
	 * @return the size of the collection(the numbers of values)
	 */
	public int size() {
		return this.collection.size();
	}


}
