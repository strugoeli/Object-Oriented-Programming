import java.util.Random;
import java.util.Scanner;


/**
 * The Player class represents a player in the Nim game, producing Moves as a response to a Board state. Each player
 * is initialized with a type, either human or one of several computer strategies, which defines the move he
 * produces when given a board in some state. The heuristic strategy of the player is already implemented. You are
 * required to implement the rest of the player types according to the exercise description.
 *
 * @author OOP course staff
 */
public class Player {


	//Constants:

	private static final int DISPLAY = 1;
	private static final int MOVE = 2;
	private static final int RANGE_CORRECTION = 1;
	private static final int INDEX_CORRECTION = 1;
	private static final int DEFAULT_ROW_NUM = 1;
	private static final int DEFAULT_PROBABILITY = 0;
	private static final int MARK_SINGLE_STICK = 0;
	private static final int MARK_TWO_STICKS = 1;

	//Constants that represent the different messages:

	private static final String DISPLAY_OR_PLAY = "Press 1 to display the board. Press 2 to make a move:";
	private static final String ILLEGAL_COMMAND = "Unsupported command";
	private static final String ROW_INPUT = "Enter the row number:";
	private static final String LEFTMOST_INPUT = "Enter the index of the leftmost stick:";
	private static final String RIGHTMOST_INPUT = "Enter the index of the rightmost stick:";


	//Constants that represent the different players.
	/**
	 * The constant integer representing the Random player type.
	 */
	public static final int RANDOM = 1;
	/**
	 * The constant integer representing the Heuristic player type.
	 */
	public static final int HEURISTIC = 2;
	/**
	 * The constant integer representing the Smart player type.
	 */
	public static final int SMART = 3;
	/**
	 * The constant integer representing the Human player type.
	 */
	public static final int HUMAN = 4;

	private static final int BINARY_LENGTH = 4;    //Used by produceHeuristicMove() for binary representation of board rows.

	private final int playerType;
	private final int playerId;
	private Scanner scanner;

	/**
	 * Initializes a new player of the given type and the given id, and an initialized scanner.
	 *
	 * @param type         The type of the player to create.
	 * @param id           The id of the player (either 1 or 2).
	 * @param inputScanner The Scanner object through which to get user input
	 *                     for the Human player type.
	 */
	public Player(int type, int id, Scanner inputScanner) {
		// Check for legal player type (we will see better ways to do this in the future).
		if (type != RANDOM && type != HEURISTIC
				&& type != SMART && type != HUMAN) {
			System.out.println("Received an unknown player type as a parameter"
					+ " in Player constructor. Terminating.");
			System.exit(-1);
		}
		playerType = type;
		playerId = id;
		scanner = inputScanner;
	}

	/**
	 * @return an integer matching the player type.
	 */
	public int getPlayerType() {
		return playerType;
	}

	/**
	 * @return the players id number.
	 */
	public int getPlayerId() {
		return playerId;
	}


	/**
	 * @return a String matching the player type.
	 */
	public String getTypeName() {
		switch (playerType) {

			case RANDOM:
				return "Random";

			case SMART:
				return "Smart";

			case HEURISTIC:
				return "Heuristic";

			case HUMAN:
				return "Human";
		}
		//Because we checked for legal player types in the
		//constructor, this line shouldn't be reachable.
		return "UnknownPlayerType";
	}

	/**
	 * This method encapsulates all the reasoning of the player about the game. The player is given the
	 * board object, and is required to return his next move on the board. The choice of the move depends
	 * on the type of the player: a human player chooses his move manually; the random player should
	 * return some random move; the Smart player can represent any reasonable strategy; the Heuristic
	 * player uses a strong heuristic to choose a move.
	 *
	 * @param board - a Board object representing the current state of the game.
	 * @return a Move object representing the move that the current player will play according to his strategy.
	 */
	public Move produceMove(Board board) {

		switch (playerType) {

			case RANDOM:
				return produceRandomMove(board);

			case SMART:
				return produceSmartMove(board);

			case HEURISTIC:
				return produceHeuristicMove(board);

			case HUMAN:
				return produceHumanMove(board);

			//Because we checked for legal player types in the
			//constructor, this line shouldn't be reachable.
			default:
				return null;
		}
	}

	/*
	 * Produces a random move.
	 */
	private Move produceRandomMove(Board board) {
		Random random = new Random();
		int row, leftMost, rightMost;
		row = getRowWithMaxProbability(board);
		while (true) {
			int leftMostRange = board.getRowLength(row) + RANGE_CORRECTION;
			leftMost = random.nextInt(leftMostRange);
			int rightMostRange = leftMostRange - leftMost;
			rightMost = random.nextInt(rightMostRange) + leftMost;
			if (isStickSequenceValid(board, row, leftMost, rightMost)) {
				break;
			}

		}
		return new Move(row, leftMost, rightMost);

	}

	/*
	 * This method checks if the given sequence [leftMost-rightMost], in the given row is legal and returns
	 * boolean type accordingly.
	 */

	private boolean isStickSequenceValid(Board board, int row, int leftMost, int rightMost) {
		if (rightMost < leftMost || leftMost < 1) {
			return false;
		}
		for (int index = leftMost; index <= rightMost; index++) {
			if (!board.isStickUnmarked(row, index)) {
				return false;
			}
		}
		return true;
	}

	/*
	 * This method checks the probability to choose an unmarked stick from each row and returns the row with
	 * the highest probability.
	 */
	private int getRowWithMaxProbability(Board board) {
		double maxProbability = DEFAULT_PROBABILITY;
		int optimalRow = DEFAULT_ROW_NUM;
		int numOfRows = board.getNumberOfRows();
		for (int rowNum = 1; rowNum <= numOfRows; rowNum++) {
			int counterUnmarked = 0;
			for (int index = 1; index <= board.getRowLength(rowNum); index++) {
				if (board.isStickUnmarked(rowNum, index)) {
					counterUnmarked++;
				}
			}
			// The length cannot be zero
			double curProbability = (double) counterUnmarked / board.getRowLength(rowNum);
			if (curProbability > maxProbability) {
				maxProbability = curProbability;
				optimalRow = rowNum;
			}
		}
		return optimalRow;
	}


	/*
	 * Produce some intelligent strategy to produce a move
	 */
	private Move produceSmartMove(Board board) {

		int[] unmarkedSticks = getNumOfUnmarked(board);
		int totalUnmarked = board.getNumberOfUnmarkedSticks();
		int maxRow = getMaxRow(unmarkedSticks) + INDEX_CORRECTION;
		int sequenceLength = MARK_SINGLE_STICK;

		if (totalUnmarked % 2 != 0) {
			sequenceLength = MARK_TWO_STICKS;
		}
		return makeSmartMove(board, maxRow, sequenceLength);
	}

	/*
	 * If it's possible the method creates a move based on intelligent strategy and returns it and returns
	 * a random move otherwise.
	 */
	private Move makeSmartMove(Board board, int row, int sequenceLength) {
		int leftMost = 0;
		int rightMost = 0;
		boolean isValid = false;// a flag which gives an indication either a valid sequence has been found

		for (int i = 1; i < board.getRowLength(row); i++) {
			leftMost = i;
			rightMost = i + sequenceLength;
			if (isStickSequenceValid(board, row, leftMost, rightMost)) {
				isValid = true;
				break;
			}
		}
		if (isValid) {
			return new Move(row, leftMost, rightMost);
		}
		return produceRandomMove(board);

	}

	/*
	 * This method creates an array of integers in the way that each index represents a row on the board and
	 * each cell represents the number of unmarked sticks in the row. note: we start to count the indexes in
	 * the array from zero while we start to count the rows of the board from one.
	 */
	private int[] getNumOfUnmarked(Board board) {
		int numOfRows = board.getNumberOfRows();
		int[] unmarkedSticks = new int[numOfRows];
		for (int row = 1; row <= numOfRows; row++) {
			unmarkedSticks[row - INDEX_CORRECTION] = getNumOfUnmarkedByRow(board, row);
		}
		return unmarkedSticks;
	}

	/*
	 * This method counts the number of unmarked sticks in the given row and returns it.
	 */
	private int getNumOfUnmarkedByRow(Board board, int row) {
		int counter = 0;
		for (int i = 1; i <= board.getRowLength(row); i++) {
			if (board.isStickUnmarked(row, i)) {
				counter++;
			}
		}
		return counter;
	}

	/*
	 * This method checks which row of the board has the highest number of unmarked sticks.
	 */
	private static int getMaxRow(int[] array) {
		int maxValue = array[0];
		int rowNum = 0;
		for (int i = 1; i < array.length; i++) {
			if (array[i] > maxValue) {
				maxValue = array[i];
				rowNum = i;
			}
		}
		return rowNum;
	}


	/*
	 * Interact with the user to produce his move.
	 */
	private Move produceHumanMove(Board board) {
		while (true) {
			System.out.println(DISPLAY_OR_PLAY);
			int userInput = scanner.nextInt();
			if (userInput == DISPLAY) {
				System.out.println(board);
			} else if (userInput == MOVE) {
				return createHumanMove();
			} else {
				System.out.println(ILLEGAL_COMMAND);
			}
		}
	}

	/*
	 * This method gets a row number, left and right bounds of the sequence (Including edges), from the user
	 * and returns a Move object that constructed by those parameters.
	 */
	private Move createHumanMove() {
		System.out.println(ROW_INPUT);
		int row = scanner.nextInt();
		System.out.println(LEFTMOST_INPUT);
		int leftMost = scanner.nextInt();
		System.out.println(RIGHTMOST_INPUT);
		int rightMost = scanner.nextInt();
		return new Move(row, leftMost, rightMost);
	}

	/*
	 * Uses a winning heuristic for the Nim game to produce a move.
	 */
	private Move produceHeuristicMove(Board board) {

		int numRows = board.getNumberOfRows();
		int[][] bins = new int[numRows][BINARY_LENGTH];
		int[] binarySum = new int[BINARY_LENGTH];
		int bitIndex, higherThenOne = 0, totalOnes = 0, lastRow = 0, lastLeft = 0, lastSize = 0, lastOneRow = 0, lastOneLeft = 0;

		for (bitIndex = 0; bitIndex < BINARY_LENGTH; bitIndex++) {
			binarySum[bitIndex] = 0;
		}

		for (int k = 0; k < numRows; k++) {

			int curRowLength = board.getRowLength(k + 1);
			int i = 0;
			int numOnes = 0;

			for (bitIndex = 0; bitIndex < BINARY_LENGTH; bitIndex++) {
				bins[k][bitIndex] = 0;
			}

			do {
				if (i < curRowLength && board.isStickUnmarked(k + 1, i + 1)) {
					numOnes++;
				} else {

					if (numOnes > 0) {

						String curNum = Integer.toBinaryString(numOnes);
						while (curNum.length() < BINARY_LENGTH) {
							curNum = "0" + curNum;
						}
						for (bitIndex = 0; bitIndex < BINARY_LENGTH; bitIndex++) {
							bins[k][bitIndex] += curNum.charAt(bitIndex) - '0'; //Convert from char to int
						}

						if (numOnes > 1) {
							higherThenOne++;
							lastRow = k + 1;
							lastLeft = i - numOnes + 1;
							lastSize = numOnes;
						} else {
							totalOnes++;
						}
						lastOneRow = k + 1;
						lastOneLeft = i;

						numOnes = 0;
					}
				}
				i++;
			} while (i <= curRowLength);

			for (bitIndex = 0; bitIndex < BINARY_LENGTH; bitIndex++) {
				binarySum[bitIndex] = (binarySum[bitIndex] + bins[k][bitIndex]) % 2;
			}
		}


		//We only have single sticks
		if (higherThenOne == 0) {
			return new Move(lastOneRow, lastOneLeft, lastOneLeft);
		}

		//We are at a finishing state
		if (higherThenOne <= 1) {

			if (totalOnes == 0) {
				return new Move(lastRow, lastLeft, lastLeft + (lastSize - 1) - 1);
			} else {
				return new Move(lastRow, lastLeft, lastLeft + (lastSize - 1) - (1 - totalOnes % 2));
			}

		}

		for (bitIndex = 0; bitIndex < BINARY_LENGTH - 1; bitIndex++) {

			if (binarySum[bitIndex] > 0) {

				int finalSum = 0, eraseRow = 0, eraseSize = 0, numRemove = 0;
				for (int k = 0; k < numRows; k++) {

					if (bins[k][bitIndex] > 0) {
						eraseRow = k + 1;
						eraseSize = (int) Math.pow(2, BINARY_LENGTH - bitIndex - 1);

						for (int b2 = bitIndex + 1; b2 < BINARY_LENGTH; b2++) {

							if (binarySum[b2] > 0) {

								if (bins[k][b2] == 0) {
									finalSum = finalSum + (int) Math.pow(2, BINARY_LENGTH - b2 - 1);
								} else {
									finalSum = finalSum - (int) Math.pow(2, BINARY_LENGTH - b2 - 1);
								}

							}

						}
						break;
					}
				}

				numRemove = eraseSize - finalSum;

				//Now we find that part and remove from it the required piece
				int numOnes = 0, i = 0;
				while (numOnes < eraseSize) {

					if (board.isStickUnmarked(eraseRow, i + 1)) {
						numOnes++;
					} else {
						numOnes = 0;
					}
					i++;

				}
				return new Move(eraseRow, i - numOnes + 1, i - numOnes + numRemove);
			}
		}

		//If we reached here, and the board is not symmetric, then we only need to erase a single stick
		if (binarySum[BINARY_LENGTH - 1] > 0) {
			return new Move(lastOneRow, lastOneLeft, lastOneLeft);
		}

		//If we reached here, it means that the board is already symmetric, and then we simply mark one stick from the last sequence we saw:
		return new Move(lastRow, lastLeft, lastLeft);
	}


}
