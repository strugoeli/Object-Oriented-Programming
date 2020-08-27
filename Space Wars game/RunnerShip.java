
/**
 * This class represents a ship which driven by an runner pilot.
 */

public class RunnerShip extends PacifistType {
	/*
	 * The upper bound of the angel range within the ship could feel threatened
	 */
	private static final double TELEPORT_ANGEL = 0.23;
	/*
	 * * The upper bound of the distance range within the ship could feel threatened
	 */
	private static final double TELEPORT_DISTANCE = 0.25;


	/**
	 * Produces an action of the runner ship which characterized by it's tendency to run away from the fight
	 * and by trying to teleport when it feels threatened.
	 *
	 * @param game the game object to which this ship belongs.
	 */
	public void doAction(SpaceWars game) {

		SpaceShip nearestShip = game.getClosestShipTo(this);
		double currentAngel = getPhysics().angleTo(nearestShip.getPhysics());
		getAwayFromEnemy(currentAngel);
		TryToTeleport(nearestShip, currentAngel);
		manageRound();
	}

	/*
	 *This method checks if the given ship it's within the right distance and within the right angel range
	 * to be threatened by other ship if  it is the method tires to teleport the ship
	 */
	private void TryToTeleport(SpaceShip nearestShip, double currentAngel) {
		double distance = getPhysics().distanceFrom(nearestShip.getPhysics());

		if (distance < Math.abs(TELEPORT_DISTANCE) && currentAngel < Math.abs(TELEPORT_ANGEL)) {
			teleport();
		}
	}

}









