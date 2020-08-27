package filesprocessing.Filters;

import java.io.File;

/**
 * This abstract class represents Filters that use file's type comparison
 */

public abstract class TypeFilters extends Filter {
	private static final String POSITIVE_INPUT = "YES";
	private static final String NEGATIVE_INPUT = "NO";
	private static final int EXPECTED_PARAMETERS = 2;
	private String answer;

	/*
	 * Constructor method
	 *
	 * @param answerInput - is the file is a certain type/"YES" or not/"NO"
	 * @throws FilterException- in case the the answer text is invalid
	 */
	TypeFilters(String answerInput) throws FilterException {
		if (!checkAnswerInput(answerInput))
			throw new FilterException();
		answer = answerInput;
		expectedParameters= EXPECTED_PARAMETERS;
	}

	// checks if the answer is valid - "NO" or "YES" are correct
	private boolean checkAnswerInput(String answer) {
		return answer.equals(POSITIVE_INPUT) || answer.equals(NEGATIVE_INPUT);
	}

	// checks if the given string represents positive or not
	boolean isPositive() {
		return answer.equals(POSITIVE_INPUT);
	}

	@Override
	public abstract boolean isFiltered(File file);
}

