
/**
 * The Move class represents a move in the Nim game by a player. A move consists of the row on which  it is
 * applied, the left bound (inclusive) of the sequence of sticks to mark, and the right bound (inclusive)
 * of the same sequence.
 *
 * @author strugo
 */

public class Move {

	private int row, left, right;

	/**
	 * Constructs a Move object with the given parameters
	 *
	 * @param inRow   - The row on which the move is performed
	 * @param inLeft  -  The left bound of the sequence to mark.
	 * @param inRight - The right bound of the sequence to mark.
	 */
	Move(int inRow, int inLeft, int inRight) {
		this.row = inRow;
		this.left = inLeft;
		this.right = inRight;
	}

	/**
	 * @return a string representation of the move.
	 */
	public String toString() {
		return this.row + ":" + this.left + "-" + this.right;
	}

	/**
	 * @return The row on which the move is performed.
	 */
	public int getRow() {
		return this.row;

	}

	/**
	 * @return The right bound of the stick sequence to mark.
	 */
	public int getRightBound() {
		return this.right;
	}

	/**
	 * @return The left bound of the stick sequence to mark.
	 */
	public int getLeftBound() {
		return this.left;
	}
}
