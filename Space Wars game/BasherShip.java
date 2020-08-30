
/**
 * This class represents a ship which driven by a pilot that tends to collide other ships.
 */


public class BasherShip extends WarFreakType {

	/*
	 * The upper bound of the distance range within the ship would try to collide the closest ship to it
	 */
	private static final double BASHING_DISTANCE = 0.19;

	/**
	 * Produces an action of the basher ship which characterized by it's tendency to collide into others and by
	 * trying to turn it's shield on when the other ship is close enough.
	 *
	 * @param game the game object to which this ship belongs.
	 */

	public void doAction(SpaceWars game) {

		SpaceShip nearestShip = game.getClosestShipTo(this);
		reactToTheSpecialShip(game, nearestShip);
		double currentAngel = getPhysics().angleTo(nearestShip.getPhysics());
		getTurnTowardsEnemy(currentAngel);
		TryToActivateShield(nearestShip);
		manageRound();
	}

	/*
	 *This method checks if the given ship it's within the right distance to activate the shield and if it is
	 *the method tires to activate it
	 */
	private void TryToActivateShield(SpaceShip nearestShip) {
		double distance = getPhysics().distanceFrom(nearestShip.getPhysics());
		if (distance <= Math.abs(BASHING_DISTANCE)) {
			shieldOn();
		} else {
			setShieldOff();
		}
	}
}





