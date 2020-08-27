/**
 * An abstract class that implements the class SimpleSet
 */

public abstract class SimpleHashSet implements SimpleSet {

	/* Constants*/

	protected static final float DEFAULT_HIGHER_CAPACITY = 0.75f;
	protected static final float DEFAULT_LOWER_CAPACITY = 0.25f;
	protected static final int QUADRATIC_FACTOR = 2;
	protected static final int EMPTY_SET = 0;
	protected static final int DEFAULT_CAPACITY = 1;


	protected static final int INITIAL_CAPACITY = 16;
	protected static final boolean TO_ADD = true;
	protected static final boolean TO_DELETE = false;

	/* Data Members*/

	private float lowerLoadFactor;
	private float upperLoadFactor;
	protected int currentCapacity;
	protected int size;
	protected int capacityMinusOne;


	/**
	 * Constructs a new hash set with the default capacities given in DEFAULT_LOWER_CAPACITY and
	 * DEFAULT_HIGHER_CAPACITY
	 */
	protected SimpleHashSet() {
		currentCapacity = INITIAL_CAPACITY;
		this.lowerLoadFactor = DEFAULT_LOWER_CAPACITY;
		this.upperLoadFactor = DEFAULT_HIGHER_CAPACITY;
	}

	/**
	 * Constructs a new hash set with capacity INITIAL_CAPACITY
	 *
	 * @param upperLoadFactor -the upper load factor before rehashing
	 * @param lowerLoadFactor - the lower load factor before rehashing
	 */
	protected SimpleHashSet(float upperLoadFactor, float lowerLoadFactor) {
		this.upperLoadFactor = upperLoadFactor;
		this.lowerLoadFactor = lowerLoadFactor;
	}

	/**
	 * @return The size of the HashSet( means the number of strings it includes)
	 */
	public int size() {
		return size;
	}

	/**
	 * @return The current capacity (number of cells) of the table.
	 */

	public abstract int capacity();


	/**
	 * Clamps hashing indices to fit within the current table capacity
	 *
	 * @param index -  the index before clamping
	 * @return an index properly clamped
	 */
	protected int clamp(int index) {
		return index & (capacity() - 1);
	}


	/**
	 * @return The lower load factor of the table.
	 */
	protected float getLowerLoadFactor() {
		return lowerLoadFactor;
	}

	/**
	 * @return The higher load factor of the table.
	 */
	protected float getUpperLoadFactor() {
		return upperLoadFactor;

	}

	/**
	 * This method checks if the current load factor is out of it's boundaries the check is done according
	 * to the action that is currently occurring
	 *
	 * @param addFlag - a flag that indicates if the current action is either add or delete
	 * @return true if the current loadFactor is out of it's boundaries.
	 */
	protected boolean checkCapacity(boolean addFlag) {

		float loadFactor = ((float) (size) / (float) (capacity()));
		if (addFlag) {
			return (loadFactor > getUpperLoadFactor());
		}
		return loadFactor < getLowerLoadFactor();

	}
}
