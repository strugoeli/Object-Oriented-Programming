import java.util.Random;

/**
 * This class represents a ship which driven by dunk pilot.
 * the ship is characterized by few drunk pattern that being chosen randomly.
 */

public class DrunkShip extends SpaceShip {

	/* Constants */

	/*
	 * Drunk pattern number 0: the ship always accelerates and shoots
	 */
	private static final int ACCELERATING_FORWARD_SHOOTING = 0;

	/*
	 * Drunk pattern number 1: the ship does not accelerate and always spinning
	 */
	private static final int PASSING_OUT = 1;

	/*
	 * Drunk pattern number 2: the ship always accelerates and does nothing else
	 */
	private static final int ACCELERATING_PASSING_OUT = 2;

	/*
	 * Drunk pattern number 3: the ship always shoots while spinning
	 */
	private static final int SPIN_SHOOTING = 3;

	/*
	 * Drunk pattern number 4: the ship always tries to teleport
	 */
	private static final int JUST_TRYING_TO_TELEPORT = 4;

	/*
	 * Indicates the space ship on the verge of death
	 */
	private static final int ON_THE_VERGE_OF_DEATH = 1;

	/*
	 * The upper bound of the random range of the numbers the Indicate which patten the ship's using
	 */
	private static final int PATTERN_RANGE = 5;

	/* Data Members */

	/*
	 * The current drunk pattern
	 */
	private int drunkPattern;

	/*
	 *Random object
	 */
	private Random rand;


	/**
	 * Constructor
	 */
	DrunkShip() {
		rand = new Random();
		drunkPattern = rand.nextInt(PATTERN_RANGE);
	}

	/**
	 * Produces an action of the drunk ship which based of five different patterns that
	 * are being chosen randomly at the begging and at every time the ship is on the verge of death.
	 *
	 * @param game the game object to which this ship belongs.
	 */
	public void doAction(SpaceWars game) {

		handleDrunkPatterns(game);
		manageRound();

	}

	/*
	 * This method picks randomly one of the drunk's pattern and produces a move based on the chosen pattern
	 */
	private void handleDrunkPatterns(SpaceWars game) {

		boolean acc = ACCELERATING;
		int turn = NO_TURNING;
		if (getCurrentHealth() == ON_THE_VERGE_OF_DEATH) {
			drunkPattern = rand.nextInt(PATTERN_RANGE);
		}
		switch (drunkPattern) {

			case ACCELERATING_FORWARD_SHOOTING:
				turn = NO_TURNING;
				acc = ACCELERATING;
				fire(game);
				break;
			case PASSING_OUT:
				turn = RIGHT_TURN;
				acc = NO_ACCELERATING;
				break;
			case ACCELERATING_PASSING_OUT:
				turn = LEFT_TURN;
				acc = ACCELERATING;
				break;

			case SPIN_SHOOTING:
				turn = LEFT_TURN;
				acc = ACCELERATING;
				fire(game);
				break;
			case JUST_TRYING_TO_TELEPORT:
				teleport();

		}
		getPhysics().move(acc, turn);
	}
}

