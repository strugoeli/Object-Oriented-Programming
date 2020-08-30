/**
 * This class represents a ship tendency to run away from the fight.
 */
public abstract class PacifistType extends SpaceShip {

	/**
	 * This method leading the ship away from the closest ship to it
	 *
	 * @param currentAngel - the angel to the closest ship
	 */
	protected void getAwayFromEnemy(double currentAngel) {
		int turnAngel = NO_TURNING;

		if (currentAngel > turnAngel) {
			turnAngel--;
		} else {
			turnAngel++;
		}
		getPhysics().move(ACCELERATING, turnAngel);
	}
}

