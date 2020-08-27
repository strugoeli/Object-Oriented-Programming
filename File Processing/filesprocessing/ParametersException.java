package filesprocessing;

/*
This class represents Wrong number of parameters exception
 */
class ParametersException extends Type2Exceptions {
	private static final String EXCEPTION_MSG = "Wrong number of parameters ,should be 2 parameters.";

	ParametersException() {
		super(EXCEPTION_MSG);
	}
}
