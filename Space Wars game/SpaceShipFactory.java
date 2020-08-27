/**
 * This class is responsible for the creation of the SpaceShip objects based on the user's inputs
 */

public class SpaceShipFactory {

	/*
	 *The following constants represent ship types:
	 */
	private static final String AGGRESSIVE_SHIP = "a";
	private static final String BASHER_SHIP = "b";
	private static final String DRUNK_SHIP = "d";
	private static final String HUMAN_SHIP = "h";
	private static final String SPECIAL_SHIP = "s";
	private static final String RUNNER_SHIP = "r";

	/**
	 * This method is given an array of strings which contains the user wanted ships and creates an array of
	 * SpaceShip objects according to the user's input
	 *
	 * @param args - Array of strings, each cell in the array represents a ship that has to be created
	 * @return An array of SpaceShip objects
	 */
	public static SpaceShip[] createSpaceShips(String[] args) {
		SpaceShip[] ships = new SpaceShip[args.length];
		for (int i = 0; i < ships.length; i++) {
			createSingleShip(ships, i, args[i]);
		}
		return ships;
	}

	/*
	 * This method creates a new SpaceShip object based on the given type and locates it in array based on
	 * the given index
	 */

	private static void createSingleShip(SpaceShip[] ships, int index, String typeOfShip) {

		switch (typeOfShip) {

			case AGGRESSIVE_SHIP:
				ships[index] = new AggressiveShip();
				break;
			case BASHER_SHIP:
				ships[index] = new BasherShip();
				break;
			case DRUNK_SHIP:
				ships[index] = new DrunkShip();
				break;
			case HUMAN_SHIP:
				ships[index] = new HumanShip();
				break;
			case SPECIAL_SHIP:
				ships[index] = new SpecialShip();
				break;
			case RUNNER_SHIP:
				ships[index] = new RunnerShip();

		}
	}
}
