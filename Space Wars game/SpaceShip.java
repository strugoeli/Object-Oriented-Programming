import java.awt.Image;

import oop.ex2.*;


/**
 * The API spaceships need to implement for the SpaceWars game.
 * It is your decision whether SpaceShip.java will be an interface, an abstract class,
 * a base class for the other spaceships or any other option you will choose.
 *
 * @author oop
 */
public abstract class SpaceShip {

	/* Constants */


	/**
	 * The default amount of energy that a spaceship possess
	 */
	public static final int DEFAULT_ENERGY = 190;

	/**
	 * The default max amount of energy that a spaceship possess
	 */
	public static final int DEFAULT_MAX_ENERGY = 210;

	/**
	 * The minimum amount of energy that a spaceship stores
	 */
	public static final int MIN_ENERGY = 0;

	/**
	 * The amount of energy units that the ship gets from bashing another ship while the shield is on
	 */
	public static final int BASHING_ENERGY_BONUS = 18;

	/**
	 * The amount of energy units that the ship losses when it's getting shot
	 */
	public static final int GETTING_HIT_MAX_ENERGY_LOSS = -10;

	/**
	 * The amount of energy units that the action activates the ship's shield requires(absolute)
	 */
	public static final int SHIELD_ENERGY_COST = -3;

	/**
	 * The amount of energy units that the action teleport requires(absolute)
	 */
	public static final int TELEPORTING_ENERGY_COST = -140;

	/**
	 * The amount of energy units that the action shoot requires(absolute)
	 */
	public static final int SHOOTING_ENERGY_COST = -19;


	/**
	 * The maximum amount of health that a spaceship possess
	 */
	public static final int MAX_HEALTH_LEVEL = 22;

	/**
	 * The health level that represents death
	 */
	public static final int DEATH_HEALTH = 0;

	/**
	 * Indicates the space ship is not turning
	 */
	public static final int NO_TURNING = 0;
	/**
	 * Indicates the space ship is turning left
	 */
	public static final int LEFT_TURN = 1;
	/**
	 * Indicates the space ship is turning right
	 */
	public static final int RIGHT_TURN = -1;
	/**
	 * Indicates the space ship is accelerating
	 */
	public static final boolean ACCELERATING = true;
	/**
	 * Indicates the space ship is not accelerating
	 */
	public static final boolean NO_ACCELERATING = false;
	/**
	 * Indicates the space ship has not more rounds to wait till the next shot
	 */
	public static final int READY_TO_SHOOT = 0;

	/**
	 * The number of rounds which determines the pause between one shot to another
	 */
	public static final int ROUNDS_SHOOT_PAUSE = 7;
	/**
	 * The min number of rounds that the ship has to wait till the next shot
	 */
	public static final int MIN_ROUNDS_TO_SHOOT = 0;

	/**
	 * The amount of energy that ship is being charged every round
	 */
	public static final int ENERGY_CHARGE_IN_ROUND = 1;
	/**
	 * The amount of health units that the ship losses when it gets hit
	 */
	public static final int GETTING_HIT_HEALTH_LOSS = -1;
	/**
	 * The amount of rounds that reduces from number of rounds the ship's shooting ability on pause
	 */
	public static final int REDUCE_ROUNDS_OF_WAITING_TO_SHOOT = -1;





	/* Data Members */

	/*
	 * The upper bound of the energy units that the ship can store
	 */
	private int maxEnergy;

	/*
	 * The number of energy units that the ship currently stores
	 */
	private int currentEnergy;
	/*
	 * The number of the health units that ships currently has
	 */
	private int currentHealth;

	/*
	 * The number of rounds that the ship's ability to shoot will be on pause
	 */
	private int roundsToReShoot;

	/*
	 * A flag that represents either the ship's shield is on or not
	 */
	private boolean shieldActivated;

	/*
	 * An object that represents the physical state of a spaceship
	 */
	private SpaceShipPhysics shipPhysics;


	/**
	 * Creates a new ship
	 */
	SpaceShip() {
		reset();
	}

	/**
	 * Does the actions of this ship for this round.
	 * This is called once per round by the SpaceWars game driver.
	 *
	 * @param game the game object to which this ship belongs.
	 */
	public abstract void doAction(SpaceWars game);


	/**
	 * This method is called every time a collision with this ship occurs
	 */
	public void collidedWithAnotherShip() {
		if (shieldActivated) {
			updateCurrentMaxEnergy(BASHING_ENERGY_BONUS);
			updateCurrentEnergy(BASHING_ENERGY_BONUS);

		} else {
			gotHit();
		}
	}


	/**
	 * This method is called whenever a ship has died. It resets the ship's
	 * attributes, and starts it at a new random position.
	 */
	public void reset() {
		maxEnergy = DEFAULT_MAX_ENERGY;
		currentHealth = MAX_HEALTH_LEVEL;
		currentEnergy = DEFAULT_ENERGY;
		roundsToReShoot = READY_TO_SHOOT;
		shipPhysics = new SpaceShipPhysics();
		setShieldOff();
	}


	/**
	 * Checks if this ship is dead.
	 *
	 * @return true if the ship is dead. false otherwise.
	 */

	public boolean isDead() {
		return currentHealth <= DEATH_HEALTH;
	}

	/**
	 * Gets the physics object that controls this ship.
	 *
	 * @return the physics object that controls the ship.
	 */
	public SpaceShipPhysics getPhysics() {
		return shipPhysics;
	}

	/**
	 * This method is called by the SpaceWars game object when ever this ship
	 * gets hit by a shot.
	 */
	public void gotHit() {
		if (!shieldActivated) {
			updateCurrentHealth(GETTING_HIT_HEALTH_LOSS);
			updateCurrentMaxEnergy(GETTING_HIT_MAX_ENERGY_LOSS);
			if (maxEnergy < currentEnergy) {
				currentEnergy = maxEnergy;
			}
		}
	}

	/**
	 * Gets the image of this ship. This method should return the image of the
	 * ship with or without the shield. This will be displayed on the GUI at
	 * the end of the round.
	 *
	 * @return the image of this ship.
	 */
	public Image getImage() {
		if (shieldActivated) {
			return GameGUI.ENEMY_SPACESHIP_IMAGE_SHIELD;
		}
		return GameGUI.ENEMY_SPACESHIP_IMAGE;
	}

	/**
	 * Attempts to fire a shot.
	 *
	 * @param game the game object.
	 */
	public void fire(SpaceWars game) {
		if (checkEnergyCosts(SHOOTING_ENERGY_COST) && roundsToReShoot == READY_TO_SHOOT) {
			game.addShot(shipPhysics);
			roundsToReShoot = ROUNDS_SHOOT_PAUSE;
			updateCurrentEnergy(SHOOTING_ENERGY_COST);
		}
	}

	/**
	 * Attempts to turn on the shield.
	 */
	public void shieldOn() {
		if (checkEnergyCosts(SHIELD_ENERGY_COST)) {
			shieldActivated = true;
			updateCurrentEnergy(SHIELD_ENERGY_COST);
		} else {
			setShieldOff();
		}
	}


	/**
	 * Attempts to teleport.
	 */
	public void teleport() {
		if (checkEnergyCosts(TELEPORTING_ENERGY_COST)) {
			shipPhysics = new SpaceShipPhysics();
			updateCurrentEnergy(TELEPORTING_ENERGY_COST);
		}
	}


	/*
	 This method checks if the ship has enough energy to execute the action which it's cost is given.
	 returns true if it's possible and false otherwise.
	 */
	private boolean checkEnergyCosts(int cost) {
		int energyAfterCost = currentEnergy + cost;

		return energyAfterCost >= MIN_ENERGY;
	}

	/*
	This method checks if the given ship object is special ship type and returns boolean type accordingly
	 */
	private boolean isItSpecial(SpaceShip nearestShip) {
		return nearestShip instanceof SpecialShip;

	}

	/**
	 * The method checks if the given ship is SpecialShip object and if it is then the method checks if it
	 * has enough energy to execute it's special ability, if it has enough energy it returns the closest
	 * ship to the given one.
	 *
	 * @param game - the game object to which this ship belongs.
	 * @param ship - The given ship
	 * @return The closest ship to the given ship if the ship is SpecialShip object and has enough energy
	 * to execute it's special ability and returns the given ship otherwise
	 */

	protected SpaceShip reactToTheSpecialShip(SpaceWars game, SpaceShip ship) {
		int minEnergyAbility = SpecialShip.MIN_ENERGY_FOR_SPECIAL_ABILITY;
		int shipCurEnergy = ship.getCurrentEnergy();
		if (isItSpecial(ship) && shipCurEnergy >= minEnergyAbility) {
			ship = game.getClosestShipTo(ship);
		}
		return ship;
	}


	/**
	 * This method updates the current energy by adding to it the given number
	 *
	 * @param updateValue - a number of energy units that needed to add to the current energy
	 */

	protected void updateCurrentEnergy(int updateValue) {
		currentEnergy += updateValue;
		if (currentEnergy < MIN_ENERGY) {
			currentEnergy = MIN_ENERGY;
		}

	}

	/**
	 * This method updates the current max energy by adding to it the given number
	 *
	 * @param updateValue - a number of energy units that needed to add to the current max energy
	 */

	protected void updateCurrentMaxEnergy(int updateValue) {
		maxEnergy += updateValue;
		if (maxEnergy < MIN_ENERGY) {
			maxEnergy = MIN_ENERGY;
		}

	}

	/**
	 * This method updates the current health by adding to it the given number
	 *
	 * @param updateValue - a number of energy units that needed to add to the current health
	 */

	protected void updateCurrentHealth(int updateValue) {
		currentHealth += updateValue;
		if (currentHealth < DEATH_HEALTH) {
			currentHealth = DEATH_HEALTH;
		}
	}

	/**
	 * This method updates the current round to shoot by adding to it the given number
	 *
	 * @param updateValue - a number of energy units that needed to add to the round to shoot
	 */

	protected void updateCurrentRoundToShoot(int updateValue) {
		roundsToReShoot += updateValue;
		if (roundsToReShoot < MIN_ROUNDS_TO_SHOOT)
			roundsToReShoot = MIN_ROUNDS_TO_SHOOT;
	}


	/**
	 * This method is called after every each round of the game and updates the max energy and the number
	 * of rounds that ship's shooting ability is on pause.
	 */
	protected void manageRound() {
		updateCurrentEnergy(ENERGY_CHARGE_IN_ROUND);
		updateCurrentRoundToShoot(REDUCE_ROUNDS_OF_WAITING_TO_SHOOT);
	}

	/* Getters and Setters*/

	/**
	 * @return The number of the current energy units that the ship's storing
	 */

	public int getCurrentEnergy() {
		return currentEnergy;
	}

	/**
	 * Sets the number of current energy units to the given number
	 *
	 * @param currentEnergy - The number of energy units that the ship currently stores
	 */
	public void setCurrentEnergy(int currentEnergy) {
		this.currentEnergy = currentEnergy;
	}

	/**
	 * @return The number of the current health units the ship's having
	 */
	public int getCurrentHealth() {
		return currentHealth;
	}

	/**
	 * Sets the number of current health units to the given number
	 *
	 * @param currentHealth -The number of the health units that ships currently has
	 */
	public void setCurrentHealth(int currentHealth) {
		this.currentHealth = currentHealth;
	}

	/**
	 * @return The current max energy
	 */

	public int getMaxEnergy() {
		return maxEnergy;
	}

	/**
	 * Sets the the max energy to the given number
	 *
	 * @param maxEnergy -The current upper bound of the energy units that the ship can store
	 */

	public void setMaxEnergy(int maxEnergy) {
		this.maxEnergy = maxEnergy;
	}

	/**
	 * @return The current number of rounds that the ship's ability to shoot will be on pause
	 */

	public int getRoundsToReShoot() {
		return roundsToReShoot;
	}

	/**
	 * Sets the current number round that the ship needs to wait till the next shot to the given number
	 *
	 * @param roundsToReShoot -The number of rounds that the ship's ability to shoot will be on pause
	 */

	public void setRoundsToReShoot(int roundsToReShoot) {
		this.roundsToReShoot = roundsToReShoot;
	}

	/**
	 * Sets the shield of the ship off.
	 */
	public void setShieldOff() {
		shieldActivated = false;
	}

	/**
	 * @return true if the shield is activated and false otherwise
	 */
	public boolean isShieldActivated() {
		return shieldActivated;
	}
}
