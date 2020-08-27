package oop.ex6.main;

import java.io.File;
import java.io.IOException;

/**
 * The main class that response to run all the code and print 0- if the code pass all the check , 1- if
 * the code have a problem, and 2 if problem was found when we tried to read the given file path
 */
public class Sjavac {

    /* ********************************** constants *********************************************** */

    /*the value that our code print when the code is legal */
    private static final String LEGAL_CODE = "0";
    /* The value that our code print when there is a problem with the given path */
    private static final String ILLEGAL_PATH = "2";
    /* The value that our code print when there is a problem in the code */
    private static final String ILLEGAL_CODE = "1";
    /*The number of args we need to get from the user, one- file path */
    private static final int ONE_PATH = 1;

    /* the given file we want to check*/
    private static File file;



    public static void main(String[] args) {
        try {
            isArgsValid(args);
            Compiler compiler = new Compiler(file.getPath());
            compiler.readLines();
            System.out.println(LEGAL_CODE);
        } catch (IOException e) {
            System.out.println(ILLEGAL_PATH);
            System.err.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(ILLEGAL_CODE);
            System.err.println(e.getMessage());
        }

    }

    /*This method check if the path we get from the user is legal.  */
    private static void isArgsValid(String[] args) throws IOException {
        if (args.length != ONE_PATH)
            throw new IOException("Only one file allowed");
        file = new File(args[0]);
        if (!file.isFile())
            throw new IOException("This is not a file");
    }

}





