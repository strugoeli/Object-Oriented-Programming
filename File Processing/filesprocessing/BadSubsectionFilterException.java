package filesprocessing;

/*
This class represents Bad sub-section of FILTER exception
 */
class BadSubsectionFilterException extends Type2Exceptions {
	private static final String FILTER_TYPE2_EXCEPTION = "Bad Filter subsection name";


	BadSubsectionFilterException() {
		super(FILTER_TYPE2_EXCEPTION);
	}
}
