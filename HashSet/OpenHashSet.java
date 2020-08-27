/**
 * This class represents an HashSet data structure which based on chaining method
 */

public class OpenHashSet extends SimpleHashSet {
	/*
	 * The hash table
	 */
	private TreeSetWrapper[] hashSet;
	/*
	The key for the set of specific data
	 */
	private int currentKey;


	/**
	 * A default constructor. Constructs a new, empty table with default initial capacity (16), upper load
	 * factor (0.75) and lower load factor (0.25).
	 */
	public OpenHashSet() {
		super();
		hashSet = new TreeSetWrapper[INITIAL_CAPACITY];
		initializeTable(hashSet);
	}

	/**
	 * Constructs a new, empty table with the specified load factors, and the default initial capacity (16)
	 *
	 * @param upperLoadFactor -The upper load factor of the hash table.
	 * @param lowerLoadFactor -  The lower load factor of the hash table
	 */
	public OpenHashSet(float upperLoadFactor, float lowerLoadFactor) {

		super(upperLoadFactor, lowerLoadFactor);
		currentCapacity = INITIAL_CAPACITY;
		hashSet = new TreeSetWrapper[INITIAL_CAPACITY];
		initializeTable(hashSet);
	}

	/**
	 * Data constructor - builds the hash set by adding the elements one by one. Duplicate values should
	 * be ignored. The new table has the default values of initial capacity (16), upper load factor (0.75),
	 * and lower load factor (0.25).
	 *
	 * @param data - Values to add to the set.
	 */
	public OpenHashSet(java.lang.String[] data) {
		this();
		for (String aData : data) {
			add(aData);
		}
	}

	@Override
	public boolean add(java.lang.String newValue) {
		if (contains(newValue))
			return false;
		int updatedCapacity;
		hashSet[currentKey].add(newValue);
		size++;
		if (checkCapacity(TO_ADD)) {
			updatedCapacity = capacity() * QUADRATIC_FACTOR;
			reHash(updatedCapacity);
		}
		return true;
	}


	@Override
	public boolean contains(java.lang.String searchVal) {
		currentKey = getKeyValue(searchVal);
		return hashSet[currentKey].contains(searchVal);
	}

	@Override
	public boolean delete(java.lang.String toDelete) {
		if (!contains(toDelete)) {
			return false;
		}
		int updatedCapacity;
		hashSet[currentKey].delete(toDelete);
		size--;
		if (checkCapacity(TO_DELETE)) {

			updatedCapacity = capacity() / QUADRATIC_FACTOR;

			reHash(updatedCapacity);
		}
		return true;
	}

	/*
	This method is given a capacity that fits to the new size of the Hash table
	and creates new hash set with that capacity and re-adding the data according to the new parameters.
	 */
	private void reHash(int updatedCapacity) {
		if (updatedCapacity == EMPTY_SET)
			updatedCapacity = DEFAULT_CAPACITY;
		TreeSetWrapper[] updatedHashSet = new TreeSetWrapper[updatedCapacity];
		TreeSetWrapper[] oldHashTable = hashSet;
		hashSet = updatedHashSet;
		currentCapacity = hashSet.length;
		initializeTable(updatedHashSet);
		addItemsToNewTable(oldHashTable);
	}

	/*
	initializes a hash set with a TreeSetWrapper objects in every cell
	 */
	private void initializeTable(TreeSetWrapper[] updatedHashSet) {
		for (int i = 0; i < currentCapacity; i++) {
			updatedHashSet[i] = new TreeSetWrapper();
		}
	}

	/*
	This method adds data to the new HashSet after Re-Hashing
	 */
	private void addItemsToNewTable(TreeSetWrapper[] oldHashTable) {
		for (TreeSetWrapper treeSet : oldHashTable) {
			for (String str : treeSet.getTreeSet()) {
				if (!treeSet.isEmpty()) {
					hashSet[getKeyValue(str)].add(str);
				}
			}
		}
	}

	/*
	This method is given a String and returns it's key in the HashSet
	 */
	private int getKeyValue(java.lang.String str) {
		return clamp(str.hashCode());
	}

	/**
	 * @return The current capacity (number of cells) of the table.
	 */
	public int capacity() {
		return currentCapacity;
	}


}
