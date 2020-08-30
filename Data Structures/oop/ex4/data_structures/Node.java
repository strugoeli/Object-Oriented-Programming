package oop.ex4.data_structures;

/*
 * A class that represents a node in Binary Search Tree
 */

class Node {

	/*Constants */

	static final int LEAF = 0;
	static final int ONE_CHILD = 1;
	private static final int TWO_CHILD = 2;
	private static final int DEFAULT_HEIGHT = -1;

	/*Data Members */

	/*
	The height of the node in an AVL tree
	 */
	private int height;
	/*
	The value that the node possess
	 */
	int value;
	/*
	The left son of the node
	 */
	Node leftSon;
	/*
	The right son of the node
	 */
	Node rightSon;
	/*
	The node's parent
	 */
	Node parent;

	/*
	A constructor
	 */
	Node(int nodeValue, Node nodeParent) {
		value = nodeValue;
		parent = nodeParent;
	}

	/*
	 * Returns the current height of the node
	 */
	int getHeight() {
		return height;
	}

	/*
	 * The height of this's left son if there is any it returns -1
	 */
	int leftHeight() {
		if (leftSon == null)
			return DEFAULT_HEIGHT;
		return leftSon.height;
	}

	/*
	 * The height of this's right son if there is any it returns -1
	 */
	int rightHeight() {
		if (rightSon == null)
			return DEFAULT_HEIGHT;
		return rightSon.height;
	}

	/*
	 * Sets this's height to be the new given height
	 */

	void setHeight(int height) {
		this.height = height;
	}

	/*
	 * Sets this leftSon to be the new given node
	 */
	void setLeftSon(Node newLeftSon) {
		this.leftSon = newLeftSon;
		if (leftSon != null) {
			this.leftSon.parent = this;
		}
	}

	/*
	 * Sets this rightSon to be the new given node
	 */
	void setRightSon(Node newRightSon) {
		this.rightSon = newRightSon;
		if (rightSon != null) {
			this.rightSon.parent = this;
		}
	}

	/*
	 * returns The number of this's children
	 */
	int getChildrenNum() {
		if (rightSon == null && leftSon == null)
			return LEAF;
		else if (rightSon == null || leftSon == null)
			return ONE_CHILD;
		else
			return TWO_CHILD;
	}
}

