/**
 * This class represents an hash set data structure based on Quadratic Probing method
 */

public class ClosedHashSet extends SimpleHashSet {

	/*Constants*/

	private static final int INDEX_FACTOR = 2;
	private static final int NOT_FOUND = -1;
	private static final String DELETE_FLAG = "";

	/*Data Members*/

	/*
	Array of string that represents an HashSet
	 */
	private String[] hashSet;
	/*
	The key of a specific data
	 */
	private int currentKey;
	/*
	The location of the flag(empty string) in the set
	 */
	private int flagIndex;


	/**
	 * A default constructor.
	 */
	public ClosedHashSet() {
		super();
		hashSet = new String[INITIAL_CAPACITY];
		flagIndex = NOT_FOUND;
	}

	/**
	 * Constructs a new, empty table with the specified load factors, and the default initial capacity (16).
	 *
	 * @param upperLoadFactor - The upper load factor of the hash table.
	 * @param lowerLoadFactor - The lower load factor of the hash table.
	 */
	public ClosedHashSet(float upperLoadFactor, float lowerLoadFactor) {
		super(upperLoadFactor, lowerLoadFactor);
		hashSet = new String[INITIAL_CAPACITY];
		currentCapacity = INITIAL_CAPACITY;
		flagIndex = NOT_FOUND;
	}

	/**
	 * Data constructor - builds the hash set by adding the elements one by one.  Duplicate values should
	 * be ignored. The new table has the default values of initial capacity (16), upper load factor (0.75),
	 * and lower load factor (0.25).
	 *
	 * @param data - Values to add to the set.
	 */
	public ClosedHashSet(java.lang.String[] data) {
		this();
		for (String aData : data) {
			add(aData);
		}
	}

	@Override
	public boolean add(java.lang.String newValue) {

		if (contains(newValue)) {
			return false;
		}
		size++;
		resizeAndReHash(TO_ADD);
		currentKey = getKeyValue(newValue, TO_ADD);
		if (newValue.equals(DELETE_FLAG))
			flagIndex = currentKey;
		hashSet[currentKey] = newValue;

		return true;
	}

	@Override
	public boolean contains(java.lang.String searchVal) {
		if (searchVal.equals(DELETE_FLAG)) {
			return flagIndex != NOT_FOUND;
		}
		currentKey = getKeyValue(searchVal, TO_DELETE);
		return currentKey != NOT_FOUND;
	}

	@Override
	public boolean delete(java.lang.String toDelete) {
		if (!contains(toDelete))
			return false;
		size--;
		resizeAndReHash(TO_DELETE);
		currentKey = getKeyValue(toDelete, TO_DELETE);
		if (toDelete.equals(DELETE_FLAG))
			flagIndex = NOT_FOUND;
		else
			hashSet[currentKey] = DELETE_FLAG;

		return true;
	}

	/*
	This method checks if need to update the capacity of the table and re-hashing with the new capacity if
	need
	 */
	private void resizeAndReHash(boolean typeOfAction) {

		int updatedCapacity;
		if (checkCapacity(typeOfAction)) {
			if (typeOfAction)
				updatedCapacity = capacity() * QUADRATIC_FACTOR;
			else
				updatedCapacity = capacity() / QUADRATIC_FACTOR;

			reHash(updatedCapacity);
		}
	}


	/*
	This method dose a re-hash- creates a new set with updated capacity and adds the data from the old set
	according to the new parameters
	 */
	private void reHash(int updatedCapacity) {
		if (updatedCapacity == EMPTY_SET)
			updatedCapacity = DEFAULT_CAPACITY;
		currentCapacity = updatedCapacity;
		String[] temp = hashSet;
		hashSet = new String[currentCapacity];
		int flagIndexTemp = flagIndex;
		flagIndex = NOT_FOUND;
		for (int i = 0; i < temp.length; i++)
			if (i == flagIndexTemp || temp[i] != null && !temp[i].equals(DELETE_FLAG))
				addToNewSet(temp[i]);
	}

	/*
	This method is given a newValue(string) and returns it's key in the set and -1 if it has not been found
	 */
	private int getKeyValue(java.lang.String newValue, boolean toAdd) {
		int curIndex;
		int key;

		for (int i = 0; i < hashSet.length; i++) {
			curIndex = newValue.hashCode() + (i + i * i) / INDEX_FACTOR;
			key = clamp(curIndex);
			if (toAdd) {
				if (flagIndex != key && (hashSet[key] == null || hashSet[key].equals(DELETE_FLAG)))
					return key;
			} else {
				if (hashSet[key] == null)
					break;
				if (hashSet[key].equals(newValue)) {
					return key;
				}
			}
		}
		return NOT_FOUND;
	}

	/*
	This method is given new value and adds it to the updated set after re-hash
	 */
	private void addToNewSet(String newValue) {

		currentKey = getKeyValue(newValue, TO_ADD);
		if (currentKey != NOT_FOUND) {
			if (newValue.equals(DELETE_FLAG)) {
				flagIndex = currentKey;
			}
			hashSet[currentKey] = newValue;
		}
	}

	/**
	 * @return The current capacity (number of cells) of the table.
	 */
	public int capacity() {
		return currentCapacity;
	}
}


