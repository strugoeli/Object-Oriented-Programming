package filesprocessing;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/*
 * This class parses a commands from a file and and by the commands it creates Section objects
 */
class Parser {
	/* Constants */
	private static final String ORDER_ABS = "abs";
	private static final String ORDER_KEY = "ORDER";
	private static final String FILTER_KEY = "FILTER";
	private static final int SUBSECTION_DIFFERENCE = 2;
	private static final int FILTER_SUBSECTION = 2;
	private static final int SECTION_LENGTH = 4;
	private static final Object EMPTY_LINE = null;


	/*
	This is given a path to commands file, reads the lines and by the commands creates list of Section objects
	and returns it
	 */
	static ArrayList<Section> readLines(String commandsPath) throws IOException, Type2Exceptions {

		ArrayList<Section> sections = new ArrayList<>();
		int filterIndex = FILTER_SUBSECTION;

		try (BufferedReader bufferedReader = new BufferedReader(new FileReader(commandsPath))) {
			String line = bufferedReader.readLine();
			String nextLine;
			//As long as there is line to read
			while (line != EMPTY_LINE) {
				int orderIndex = filterIndex + SUBSECTION_DIFFERENCE;

				// Start dealing with filter
				nextLine = bufferedReader.readLine();
				String filterCommands = readFilter(line, nextLine);

				// Start dealing with order
				line = bufferedReader.readLine();
				nextLine = bufferedReader.readLine();
				String orderCommands = readOrder(line, nextLine);

				//Creating sections and preparing the next iteration
				sections.add(new Section(filterCommands, orderCommands, filterIndex, orderIndex));
				filterIndex += SECTION_LENGTH;

				//In case the section length is three
				if (isShotSection(nextLine)) {
					line = nextLine;
					filterIndex--;
				} else
					line = bufferedReader.readLine();
			}
		}
		return sections;
	}

	/*
	Checks if the current section is short- without the orderCmd
	 */
	private static boolean isShotSection(String nextLine) {
		return nextLine == EMPTY_LINE || nextLine.equals(FILTER_KEY);
	}

	/*
	Handles the filter sub-section commands
	 */
	private static String readFilter(String line, String nextLine) throws Type2Exceptions {
		if (line != EMPTY_LINE && line.equals(FILTER_KEY)) {
			return nextLine;
		} else {
			//the FILTER line is invalid
			throw new BadSubsectionFilterException();
		}
	}

	/*
	Handles the order sub-section commands
	 */
	private static String readOrder(String line, String nextLine) throws Type2Exceptions {

		if (line != EMPTY_LINE && line.equals(ORDER_KEY)) {
			if (isShotSection(nextLine)) {
				//means there is no command line related to ORDER so the length of the section is three
				return ORDER_ABS;
			} else {
				//normal section of four
				return nextLine;
			}
		} else {
			//the ORDER line is invalid
			throw new BadSubsectionOrderException();
		}
	}
}

