
/**
 * This class represents a ship which driven by an aggressive pilot.
 */

public class AggressiveShip extends WarFreakType {
	/*
	 *The upper bound of the angel range within the ship would try to shoot the closest ship to it
	 */
	private static final double MAX_SHOOTING_ANGEL = 0.21;


	/**
	 * Produces an action of the aggressive ship which characterized by it's tendency to shoot others
	 * close to it
	 *
	 * @param game the game object to which this ship belongs.
	 */

	public void doAction(SpaceWars game) {

		SpaceShip nearestShip = game.getClosestShipTo(this);
		nearestShip = reactToTheSpecialShip(game, nearestShip);
		double currentAngel = getPhysics().angleTo(nearestShip.getPhysics());
		getTurnTowardsEnemy(currentAngel);
		handleShooting(game, currentAngel);
		manageRound();
	}

	/*
	 *This method checks if the given ship it's within the right angel range to shoot it and if
	 * it is the method tires to preform shooting
	 */
	private void handleShooting(SpaceWars game, double currentAngel) {
		if (currentAngel < Math.abs(MAX_SHOOTING_ANGEL)) {
			fire(game);
		}
	}
}
