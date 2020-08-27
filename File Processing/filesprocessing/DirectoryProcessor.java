package filesprocessing;

import java.io.File;
import java.io.*;
import java.util.*;

/**
 * This class is the main class that manages the program which gets path to a folder and path to commands
 * that contain the order and the filter that determines the order of the files to be printed and in what
 * order
 */
public class DirectoryProcessor {

	/*Constants */

	private static final int PARAMETERS_NUMBER = 2;

	/*Data Members */

	private File[] files;

	/*
	 * This method is given directory path to folder that contains files that need to be filtered and
	 * sorted by the commands text which it's path is given and finally generates the output - prints the
	 * files name that passed the filter and in the given order
	 */
	private DirectoryProcessor(String directoryPath, String commandsPath) throws IOException, Type2Exceptions {
		files = new File(directoryPath).listFiles();
		ArrayList<Section> sections = Parser.readLines(commandsPath);
		executing(sections);
	}

	/*
	This method executing the output
	 */
	private void executing(ArrayList<Section> sections) {
		File[] outPut;
		for (Section section : sections) {
			outPut = section.generateOutput(files);
			printOutput(outPut);
		}
	}

	/*
	This method is given a list of files and print the files name
	 */
	private void printOutput(File[] files) {
		for (File file : files)
			System.out.println(file.getName());
	}

	public static void main(String[] args) {

		try {
			if (args.length != PARAMETERS_NUMBER) {
				throw new ParametersException();
			}
			new DirectoryProcessor(args[0], args[1]);
		} catch (Exception exception) {
			System.err.println(exception.getMessage());
		}
	}
}








