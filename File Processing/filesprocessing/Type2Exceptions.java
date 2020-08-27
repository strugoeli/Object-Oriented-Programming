package filesprocessing;

/*
This class represents Type2 Exceptions
 */

class Type2Exceptions extends Exception {

	Type2Exceptions(String msg) {
		super("ERROR: " + msg + " \n");
	}
}
