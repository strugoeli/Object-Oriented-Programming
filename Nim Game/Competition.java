import java.util.Scanner;

/**
 * The Competition class represents a Nim competition between two players, consisting of a given number of rounds.
 * It also keeps track of the number of victories of each player.
 *
 * @author strugo
 */

public class Competition {
	/**
	 * The objects representing the first and the second player.
	 */
	private Player player1, player2;

	/**
	 * This object represents the player whose turn it is.(the first or the second)
	 */
	private Player currentPlayer;
	/**
	 * a flag indicating whether game play messages should be printed to the console.
	 */
	private boolean displayMessage;
	/**
	 * an array which contains the scores of each player relatively(the score of player(n) in score[n-1])
	 */

	private int[] scores;

	//Constants:
	private static final int PLAYER1 = 1;
	private static final int PLAYER2 = 2;
	private static final int NUM_OF_PLAYERS = 2;
	private static final int OVERLAP = -2;
	private static final int FULLY_MARKED = 0;
	private static final int OUT_OF_BOUNDARIES = -1;
	private static final int INDEX_CORRECTION = 1;

	//Constants that represent the different messages:
	private static final String WELCOME_MESSAGE = "Welcome to the sticks game!";
	private static final String TURN_MESSAGE = "Player %d, it is now your turn!";
	private static final String WINNER_MESSAGE = "Player %d won!";
	private static final String INVALID_MOVE = "Invalid move. Enter another:";
	private static final String RESULTS = "The results are %d:%d";
	private static final String LEGAL_MOVE = "Player %d made the move: %s";
	private static final String START_COMPETITION = "Starting a Nim competition of %d rounds between a %s "
			+ "player and a %s player.";

	/**
	 * Constructs a Competition object with the given parameters:
	 *
	 * @param player1        - The Player objects representing the first player.
	 * @param player2        - The Player objects representing the second player.
	 * @param displayMessage - a flag indicating whether game play messages should be printed to the console.
	 */

	public Competition(Player player1, Player player2, boolean displayMessage) {
		this.player1 = player1;
		this.player2 = player2;
		this.currentPlayer = player1;
		this.displayMessage = displayMessage;
		this.scores = new int[NUM_OF_PLAYERS];
	}

	/**
	 * @param playerPosition- The player's id number.
	 * @return the score of the player based on he's id number.
	 */
	public int getPlayerScore(int playerPosition) {
		if (playerPosition == PLAYER1 || playerPosition == PLAYER2) {
			return scores[playerPosition - INDEX_CORRECTION];
		}
		return -1;
	}

	/**
	 * Runs the game for the given number of rounds.
	 *
	 * @param numberOfRounds -number of rounds to play.
	 */
	public void playMultipleRounds(int numberOfRounds) {
		printStartMessage(numberOfRounds);
		while (getNumOfTurns() < numberOfRounds) {
			Board board = new Board();
			playSingleRound(board);
		}
		printResults();
	}

	/*
	 * Runs the game for single round.
	 */
	private void playSingleRound(Board board) {
		handleHumanMessages(WELCOME_MESSAGE);
		while (!isTurnEnded(board)) {
			handleHumanMessages(TURN_MESSAGE);
			playSingleTurn(board);
		}
		handleHumanMessages(WINNER_MESSAGE);
		addToScore(this.currentPlayer);
		this.currentPlayer = this.player1;
	}

	/*
	 * Runs the game for single turn.
	 */
	private void playSingleTurn(Board board) {
		Move currentMove;
		while (true) {
			currentMove = this.currentPlayer.produceMove(board);
			int moveLegality = board.markStickSequence(currentMove);
			if ((moveLegality != OUT_OF_BOUNDARIES && moveLegality != OVERLAP)) {
				break;
			}
			handleHumanMessages(INVALID_MOVE);
		}
		showLegalMove(currentMove);
		switchPlayers();
	}

	/*
	 * if the current player is human the method prints the move that the current player has made and does
	 * nothing otherwise.
	 */
	private void showLegalMove(Move currentMove) {
		if (isPlayerHuman()) {
			int playerId = this.currentPlayer.getPlayerId();
			System.out.println(String.format(LEGAL_MOVE, playerId, currentMove.toString()));
		}
	}

	/*
	 * prints the winning results of the match
	 */
	private void printResults() {
		System.out.println(String.format(RESULTS, getPlayerScore(PLAYER1), getPlayerScore(PLAYER2)));
	}

	/*
	 * This method is given a message that should be printed to the console and prints it if the player is
	 * human and does nothing otherwise.
	 */
	private void handleHumanMessages(String message) {
		if (isPlayerHuman()) {
			int playerId = this.currentPlayer.getPlayerId();
			switch (message) {

				case WELCOME_MESSAGE:
					System.out.println(WELCOME_MESSAGE);
					return;

				case TURN_MESSAGE:
					System.out.println(String.format(TURN_MESSAGE, playerId));
					return;

				case INVALID_MOVE:
					System.out.println(INVALID_MOVE);
					return;
				case WINNER_MESSAGE:
					System.out.println(String.format(WINNER_MESSAGE, playerId));
			}
		}
	}

	/*
	 * The method prints the general messages to the console at the very beginning of the game.
	 */
	private void printStartMessage(int numberOfRounds) {
		String playerOneType = this.player1.getTypeName();
		String playerTwoType = this.player2.getTypeName();
		System.out.println(String.format(START_COMPETITION, numberOfRounds, playerOneType, playerTwoType));

	}

	/*
	 * returns the numbers of the turn that have been played based on the scores of the players.
	 */
	private int getNumOfTurns() {
		return getPlayerScore(PLAYER1) + getPlayerScore(PLAYER2);
	}

	/*
	 * returns true if the sticks are marked and the turn is ended.
	 */
	private boolean isTurnEnded(Board board) {
		return board.getNumberOfUnmarkedSticks() == FULLY_MARKED;
	}

	/*
	 * Adds one to the score counter of the given player
	 */
	private void addToScore(Player player) {
		if (player.getPlayerId() == PLAYER1) {
			this.scores[PLAYER1 - INDEX_CORRECTION]++;
		} else {
			this.scores[PLAYER2 - INDEX_CORRECTION]++;
		}
	}

	/*
	 *Returns true if the game play messages should be printed to the console and false otherwise.
	 */
	private boolean isPlayerHuman() {
		return this.displayMessage;
	}

	/*
	 * Sets the current to be the other player.
	 */
	private void switchPlayers() {
		if (this.currentPlayer == this.player1) {
			this.currentPlayer = this.player2;
		} else {
			this.currentPlayer = this.player1;
		}
	}

	/*
	 * Returns the integer representing the type of player 1; returns -1 on bad
	 * input.
	 */
	private static int parsePlayer1Type(String[] args) {
		try {
			return Integer.parseInt(args[0]);
		} catch (Exception E) {
			return -1;
		}
	}

	/*
	 * Returns the integer representing the type of player 2; returns -1 on bad
	 * input.
	 */
	private static int parsePlayer2Type(String[] args) {
		try {
			return Integer.parseInt(args[1]);
		} catch (Exception E) {
			return -1;
		}
	}

	/*
	 * Returns the integer representing the type of player 2; returns -1 on bad
	 * input.
	 */
	private static int parseNumberOfGames(String[] args) {
		try {
			return Integer.parseInt(args[2]);
		} catch (Exception E) {
			return -1;
		}
	}

	/**
	 * The method runs a Nim competition between two players according to the three user-specified arguments.
	 * (1) The type of the first player, which is a positive integer between 1 and 4: 1 for a Random computer
	 * player, 2 for a Heuristic computer player, 3 for a Smart computer player and 4 for a human player.
	 * (2) The type of the second player, which is a positive integer between 1 and 4.
	 * (3) The number of rounds to be played in the competition.
	 *
	 * @param args an array of string representations of the three input arguments, as detailed above.
	 */
	public static void main(String[] args) {

		int p1Type = parsePlayer1Type(args);
		int p2Type = parsePlayer2Type(args);
		int numGames = parseNumberOfGames(args);
		boolean displayMessage = false;
		if (p1Type == Player.HUMAN || p2Type == Player.HUMAN) {
			displayMessage = true;
		}
		Scanner scanner = new Scanner(System.in);
		Player player1 = new Player(p1Type, PLAYER1, scanner);
		Player player2 = new Player(p2Type, PLAYER2, scanner);
		Competition competition = new Competition(player1, player2, displayMessage);
		competition.playMultipleRounds(numGames);
		scanner.close();
	}
}
