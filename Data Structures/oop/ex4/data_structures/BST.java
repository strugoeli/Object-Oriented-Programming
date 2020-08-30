package oop.ex4.data_structures;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * Application of binary search tree with all the required attributes for binary trees.
 */

public class BST implements Iterable<Integer> {

	/*Constants*/

	private static final int NOT_FOUND = -1;
	private static final int ROOT_DEPTH = 0;
	private static final Node NO_SON = null;

	/*
	The tree node
	*/
	Node root;

	/*
	The node which is being manipulated
	*/
	Node currentNode;

	/*
	The size of the tree (number of nodes)
	 */

	private int size;


	public BST() {

	}

	/**
	 * A constructor that builds a new BST tree containing all unique values in the input array.
	 *
	 * @param data - data the values to add to tree.
	 */
	public BST(int[] data) {
		if (data != null) {
			for (Integer aData : data) {
				add(aData);
			}
		}
	}


	/**
	 * Add a new node with key newValue into the tree.
	 *
	 * @param newValue new value to add to the tree.
	 * @return false if newValue already exist in the tree
	 */
	public boolean add(int newValue) {
		if (contains(newValue) != NOT_FOUND)
			return false;

		Node toAdd = new Node(newValue, currentNode);
		if (currentNode == null) {
			root = toAdd;
		} else if (currentNode.value > newValue)
			currentNode.setLeftSon(toAdd);
		else if (currentNode.value < newValue)
			currentNode.setRightSon(toAdd);
		else
			return false;
		updateHeightAdd();
		size++;
		return true;
	}

	/*
	This method updates the height of the parents of the node that has been added to the tree after
    adding it
	 */
	private void updateHeightAdd() {
		if (currentNode == null)
			return;
		Node traveler = currentNode;
		while (traveler != null) {
			traveler.setHeight(Math.max(traveler.leftHeight(), traveler.rightHeight()) + 1);

			traveler = traveler.parent;
		}
	}

	/**
	 * A recursive method that checks the height of the right and left son
	 * and thus updates the height of the given node.
	 *
	 * @param root Current node
	 * @return The height of the current node.
	 */
	int updateHeightSubTree(Node root) {
		if (root == null)
			return -1;
		root.setHeight(Math.max(updateHeightSubTree(root.rightSon), updateHeightSubTree(root.leftSon)) + 1);
		return root.getHeight();
	}


	/**
	 * Check whether the tree contains the given input value.
	 *
	 * @param searchVal - the value to search.
	 * @return the depth of the node(0 for the root) with the given value if it was found in the tree
	 * ,-1 otherwise
	 */
	public int contains(int searchVal) {

		int depth = containsR(root, searchVal);
		if (currentNode != null && currentNode.value == searchVal)
			return depth;
		else
			return NOT_FOUND;
	}

	/*
	This method finds recursively either the given node is found in the tree or not, and defines
	currentNode to be the node with the given value or the node where the value should be added to keep
	 the order of the BST
	 */

	private int containsR(Node node, int searchVal) {
		if (node == null)
			return NOT_FOUND;
		else
			currentNode = node;
		if (node.value == searchVal) {
			return ROOT_DEPTH;
		}
		if (searchVal > node.value)
			return containsR(node.rightSon, searchVal) + 1;
		else
			return containsR(node.leftSon, searchVal) + 1;
	}

	/**
	 * Removes the node with the given value from the tree, if it exists.
	 *
	 * @param toDelete the value to remove from the tree.
	 * @return true if the given value was found and deleted, false otherwise.
	 */
	public boolean delete(int toDelete) {
		if (contains(toDelete) == NOT_FOUND) {
			return false;
		}
		int numOfChildren = currentNode.getChildrenNum();
		if (numOfChildren == Node.LEAF) {
			removeLeaf(currentNode);
		} else if (numOfChildren == Node.ONE_CHILD)

			removeCaseOneChild(currentNode, currentNode.parent);
		else {
			replaceWithSuccessor(currentNode, currentNode.parent);
		}
		updateHeightSubTree(root);
		size--;
		return true;
	}

	/*
	 replaces the given node with it's successor- for deletion purposes
	 */
	private void replaceWithSuccessor(Node toRemove, Node parent) {

		Node successor = getSuccessor(toRemove);
		if (successor != null) {
			currentNode = successor;
			successor.setLeftSon(toRemove.leftSon);
			if (toRemove != successor.parent) {
				handleSuccessorNotSon(toRemove, successor);
			}
			if (toRemove == root) {
				root = successor;
				successor.parent = null;
			} else if (toRemove == parent.rightSon)
				parent.setRightSon(successor);
			else
				parent.setLeftSon(successor);
		}
	}

	/*
	This method replace the given node to remove with it's successor in the case that successor is not a
	direct son of the given node
	 */
	private void handleSuccessorNotSon(Node toRemove, Node successor) {
		currentNode = successor.parent;
		if (successor.rightSon != null)
			successor.parent.setLeftSon(successor.rightSon);
		if (successor.rightSon == null)
			successor.parent.setLeftSon(NO_SON);
		successor.setRightSon(toRemove.rightSon);
	}

	/*
	This method removes the given node from the tree in case the given node has only one child
	 */
	private void removeCaseOneChild(Node toRemove, Node parent) {
		if (parent == null) {
			if (root.rightSon != null)
				root = root.rightSon;
			else
				root = root.leftSon;
			root.parent = null;

		} else if (toRemove.rightSon != null) {
			if (toRemove == parent.rightSon) {
				parent.setRightSon(toRemove.rightSon);
			} else {
				parent.setLeftSon(toRemove.rightSon);
			}
		} else {
			if (parent.rightSon == toRemove) {
				parent.setRightSon(toRemove.leftSon);
			} else {
				parent.setLeftSon(toRemove.leftSon);
			}
		}
	}

	/*
	This method removes the given node from the tree in case the given node is a leaf
	 */
	private void removeLeaf(Node toRemove) {
		Node parent = toRemove.parent;
		if (parent == null) {
			root = null;
			return;
		}
		if (toRemove == parent.rightSon)
			parent.setRightSon(NO_SON);
		else
			parent.setLeftSon(NO_SON);

	}

	/*
	This method gets the given node's Successor
	 */
	private Node getSuccessor(Node node) {
		if (node.rightSon != null)
			return getMinValue(node.rightSon);

		while (node.parent != null && node == node.parent.rightSon)
			node = node.parent;
		if (node.parent == null)
			return null;
		else
			return node.parent;
	}

	/*
	This method is given a root a tree and finds it's minimum value
	 */
	private Node getMinValue(Node root) {

		Node traveler = root;
		while (traveler.leftSon != null) {
			traveler = traveler.leftSon;
		}
		return traveler;


	}

	/**
	 * @return the number of nodes in the tree.
	 */
	public int size() {
		return size;
	}


	@Override
	public Iterator<Integer> iterator() {
		return new TreeIterator();
	}

	/*
	The Iterator class is responsible for running on the entire tree
	 */
	private class TreeIterator implements Iterator<Integer> {

		private LinkedList<Node> iterationList;

		/*
		A constructor
		 */
		private TreeIterator() {
			iterationList = new LinkedList<>();
			if (BST.this.root != null)
				addNext(root);
		}

		@Override
		public boolean hasNext() {
			return (!iterationList.isEmpty());
		}


		@Override
		public Integer next() {
			if (!hasNext())
				throw new NoSuchElementException();
			return iterationList.removeFirst().value;
		}

		/*
		Receives a node and adds the next one
		 */
		private void addNext(Node curNode) {
			if (curNode.leftSon != null) {
				addNext(curNode.leftSon);
			}
			iterationList.add(curNode);
			if (curNode.rightSon != null) {
				addNext(curNode.rightSon);
			}
		}

	}

}