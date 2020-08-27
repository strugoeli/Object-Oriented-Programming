/**
 * This class represents a ship with special ability.
 */

public class SpecialShip extends PacifistType {

	/* Constants */

	/**
	 * The min  amount of energy units the special ship needs to have in order to use electronic warfare
	 */
	public static final int MIN_ENERGY_FOR_SPECIAL_ABILITY = 80;
	/*
	 *The lower bound of the energy range within the special ability has an effect on the ship's energy
	 */
	private static final int MIN_ENERGY_FOR_ABILITY_COST = 55;
	/*
	 *Indicates number of rounds the ship needs to wait till the next use of the electronic warfare
	 */

	private static final int PAUSE_FOR_SPECIAL_ABILITY = 100;
	/*
	 *The amount of energy units that the electronic warfare requires(absolute) each round
	 */

	private static final int SPECIAL_ABILITY_COST = -2;
	/*
	 *Indicates there is no more rounds to wait till the next use of the electronic warfare
	 */

	private static final int READY_TO_ACTIVATE = 0;
	/*
	 *Indicates the electronic warfare is activated
	 */

	private static final boolean ACTIVATED = true;

	/* Data Members */

	/*
	 *A flag which Indicates either the electronic warfare is activated or not
	 */
	private boolean electronicWarfare;
	/*
	 *The current amount of round till the next use of the ability
	 */
	private int electronicWarfareLoading;

	/**
	 * Constructor
	 */
	SpecialShip() {
		electronicWarfare = ACTIVATED;
		electronicWarfareLoading = PAUSE_FOR_SPECIAL_ABILITY;
	}

	/**
	 * Produces an action of the special ship which characterized by it's tendency to run away from the fight
	 * and actives electronic warfare whenever it's possible.
	 *the electronic warfare makes it invisible to the aggressive and the basher ships and makes them to
	 * attack the closest ship to it instead of attacking it.
	 *
	 * @param game the game object to which this ship belongs.
	 */

	public void doAction(SpaceWars game) {

		SpaceShip nearestShip = game.getClosestShipTo(this);
		double currentAngel = getPhysics().angleTo(nearestShip.getPhysics());
		getAwayFromEnemy(currentAngel);

		if (getCurrentEnergy() > MIN_ENERGY_FOR_ABILITY_COST && electronicWarfare) {
			updateCurrentEnergy(SPECIAL_ABILITY_COST);
		} else {
			electronicWarfare = false;
			electronicWarfareLoading--;
		}
		if (electronicWarfareLoading == READY_TO_ACTIVATE) {
			electronicWarfare = true;
			electronicWarfareLoading = PAUSE_FOR_SPECIAL_ABILITY;
		}
		manageRound();
	}
}


