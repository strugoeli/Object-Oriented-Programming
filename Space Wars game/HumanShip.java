import oop.ex2.*;

import java.awt.*;

/**
 * This class represents a ship which controlled by the user.
 */

public class HumanShip extends SpaceShip {

	/**
	 * Produces an action of the human ship which controlled by the user via GUI.
	 *
	 * @param game the game object to which this ship belongs.
	 */

	public void doAction(SpaceWars game) {
		GameGUI gui = game.getGUI();
		handleTeleport(gui);
		handleMovements(gui);
		handleShield(gui);
		handleShooting(game, gui);
		manageRound();
	}

	/*
	 * This method checks if the user is pressing on the button that indicates teleportion and tires to
	 * perform it
	 */
	private void handleTeleport(GameGUI gui) {
		if (gui.isTeleportPressed()) {
			teleport();
		}
	}

	/*
	 *This method checks if the user is pressing on the button that indicates movement and tires to
	 *perform it
	 */

	private void handleMovements(GameGUI gui) {
		boolean accelerate = gui.isUpPressed();
		int turn = NO_TURNING;
		if (gui.isLeftPressed()) {
			turn++;
		}
		if (gui.isRightPressed()) {
			turn--;
		}
		getPhysics().move(accelerate, turn);

	}

	/*
	 *This method checks if the user is pressing on the button that indicates activating shield and tires to
	 *perform it
	 */

	private void handleShield(GameGUI gui) {
		if (gui.isShieldsPressed()) {
			shieldOn();
		} else {
			setShieldOff();
		}
	}

	/*
	 *This method checks if the user is pressing on the button that indicates shooting and tires to
	 *perform it
	 */
	private void handleShooting(SpaceWars game, GameGUI gui) {
		if (gui.isShotPressed()) {
			fire(game);
		}
	}

	/**
	 * Gets the image of the Human Ship. This method should return the image of the
	 * ship with or without the shield. This will be displayed on the GUI at
	 * the end of the round.
	 *
	 * @return the image of Human Ship.
	 */
	public Image getImage() {
		if (isShieldActivated()) {
			return GameGUI.SPACESHIP_IMAGE_SHIELD;
		}
		return GameGUI.SPACESHIP_IMAGE;
	}
}
