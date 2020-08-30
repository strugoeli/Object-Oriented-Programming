package oop.ex4.data_structures;

/**
 * This class is the complete and tested implementation of an AVL-tree.
 */

public class AvlTree extends BST implements Iterable<Integer> {
	/* Constants */

	private static final int INITIAL_CURRENT = 0;
	private static final int DEFAULT_HEIGHT = 0;
	private static final int MINIMUM_HEIGHT = 1;
	private static final int INITIAL_SUM = 1;
	private static final int AVL_BOUND = 1;
	private static final int MAX_NODE_BASE = 2;


	/**
	 * A default constructor.
	 */
	public AvlTree() {

	}

	/**
	 * A constructor that builds a new AVL tree containing all unique values in the input array.
	 *
	 * @param data - data the values to add to tree.
	 */
	public AvlTree(int[] data) {
		if (data != null) {
			for (Integer aData : data) {
				add(aData);
			}
		}
	}

	/**
	 * A copy constructor that creates a deep copy of the given AvlTree. the new tree contains all the
	 * values of the given tree, but not necessarily in the same structure.
	 *
	 * @param avlTree avlTree and AVL tree.
	 */
	public AvlTree(AvlTree avlTree) {
		if (avlTree != null) {
			for (Integer avlNode : avlTree)
				add(avlNode);
		}
	}

	/**
	 * Add a new node with key newValue into the tree.
	 *
	 * @param newValue new value to add to the tree.
	 * @return false if newValue already exist in the tree
	 */
	public boolean add(int newValue) {
		boolean isAdded = super.add(newValue);
		checkValidate();
		return isAdded;
	}

	/**
	 * Removes the node with the given value from the tree, if it exists.
	 *
	 * @param toDelete the value to remove from the tree.
	 * @return true if the given value was found and deleted, false otherwise.
	 */
	public boolean delete(int toDelete) {
		boolean isDeleted = super.delete(toDelete);
		checkValidate();
		return isDeleted;

	}

	/**
	 * Check whether the tree contains the given input value.
	 *
	 * @param searchVal - the value to search.
	 * @return the depth of the node(0 for the root) with the given value if it was found in the tree
	 * ,-1 otherwise
	 */
	public int contains(int searchVal) {
		return super.contains(searchVal);
	}

	/**
	 * @return the number of nodes in the tree.
	 */
	public int size() {
		return super.size();
	}

	/*
	A method that checks whether there is a violation and, if necessary, sends an AVL repair
	*/
	private void checkValidate() {
		int balanceFactor;
		Node traveler = currentNode;

		while (traveler != null) {
			balanceFactor = Math.abs(traveler.leftHeight() - traveler.rightHeight());
			if (balanceFactor > AVL_BOUND) {
				fixBalance(traveler);
				break;
			}
			traveler = traveler.parent;
		}
	}

	/*
	This method finds the type of the violation (RR,LL,RL,LR) and executes the right rotation accordingly
	 */
	private void fixBalance(Node node) {
		int balance = getBalance(node);
		if (balance > AVL_BOUND) {
			if (getBalance(node.leftSon) < DEFAULT_HEIGHT) {
				rotateLeft(node.leftSon);
			}
			rotateRight(node);
		} else if (balance < -AVL_BOUND) {
			if (getBalance(node.rightSon) > DEFAULT_HEIGHT) {
				rotateRight(node.rightSon);
			}
			rotateLeft(node);
		}
		updateHeightSubTree(root);
	}

	/*
	Receives a node and restores its balance.
	 */
	private int getBalance(Node node) {
		if (node == null)
			return DEFAULT_HEIGHT;
		return node.leftHeight() - node.rightHeight();
	}


	/*
	A method that receives a node and rotates left accordingly.
	 */
	private void rotateLeft(Node node) {
		if (node == root) {
			node.rightSon.parent = null;
			root = node.rightSon;
		} else {
			if (node == node.parent.rightSon)
				node.parent.setRightSon(node.rightSon);
			else if (node == node.parent.leftSon)
				node.parent.setLeftSon(node.rightSon);
		}
		Node right = node.rightSon;
		node.setRightSon(right.leftSon);
		right.setLeftSon(node);

	}

	/*
	A method that receives a node and rotates right accordingly.
	 */
	private void rotateRight(Node node) {
		if (node == root) {
			node.leftSon.parent = null;
			root = node.leftSon;
		} else {
			if (node == node.parent.rightSon)
				node.parent.setRightSon(node.leftSon);
			else
				node.parent.setLeftSon(node.leftSon);
		}
		Node left = node.leftSon;
		node.setLeftSon(left.rightSon);
		left.setRightSon(node);
	}

	/**
	 * Calculates the minimum number of nodes in an AVL tree of height h.
	 *
	 * @param h The height of the tree (a non-negative number) in question.
	 * @return minimal number of nodes in an AVL tree of the given height.
	 */
	public static int findMinNodes(int h) {
		if (h == DEFAULT_HEIGHT) {
			return MINIMUM_HEIGHT;
		}
		int current = INITIAL_CURRENT, next = INITIAL_SUM, sum;
		for (int i = 0; i < h; i++) {
			sum = current + next + 1;
			current = next;
			next = sum;
		}
		return next;
	}

	/**
	 * Calculates the maximum number of nodes in an AVL tree of height h.
	 *
	 * @param h The height of the tree (a non-negative number) in question.
	 * @return maximal number of nodes in an AVL tree of the given height.
	 */
	public static int findMaxNodes(int h) {
		return (int) (Math.pow(MAX_NODE_BASE, h + 1)) - 1;
	}
}
