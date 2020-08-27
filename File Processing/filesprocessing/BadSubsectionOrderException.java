package filesprocessing;

/*
This class represents Bad sub-section of ORDER exception
 */
class BadSubsectionOrderException extends Type2Exceptions {
	private static final String ORDER_TYPE2_EXCEPTION = "Bad Order subsection name";

	BadSubsectionOrderException() {
		super(ORDER_TYPE2_EXCEPTION);
	}
}
