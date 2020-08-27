/**
 * This class represents ships with tendency to attack others.
 */

public abstract class WarFreakType extends SpaceShip {

	/**
	 * This method leading the ship towards the closest ship to it
	 *
	 * @param currentAngel - the angel to the closest ship
	 */
	protected void getTurnTowardsEnemy(double currentAngel) {
		int turnAngel = NO_TURNING;

		if (currentAngel > turnAngel) {
			turnAngel++;
		} else {
			turnAngel--;
		}
		getPhysics().move(ACCELERATING, turnAngel);
	}
}
