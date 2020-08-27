package oop.ex6.main;

import oop.ex6.exceptions.SyntaxException;

/*This class contains methods that handle expressions of code lines according to the s-Java format*/
class ExpressionHandler {


    static final String OPEN_ROUND_BRACKET = "(";
    static final String CLOSE_ROUND_BRACKET = ")";
    private static final int FIRST_WORD_PLACE = 1;
    private static final int SINGLETON = 1;
    private static final String EMPTY_STR = "";
    private static final int BEGIN_INDEX = 0;
    private static final int NOT_FOUND = -1;
    static final String SPACE = " ";


    /*Method that get a type, and value type. this method check if the valueType adjust to the given type*/
    static Boolean ifTypeAdjustValueType(String type, String valType) {
        switch (type) {
            case ("boolean"):
                return valType.equals("boolean") || valType.equals("double") || valType.equals("int");
            case ("double"):
                return valType.equals("double") || valType.equals("int");
            case ("int"):
                return valType.equals("int");
            case ("String"):
                return valType.equals("String");
            case ("char"):
                return valType.equals("char");
        }
        return false;
    }

    /* Method that get a type and wanted value-some expression. this method check if the given value is
    kind of the given type and return the answer*/
    static boolean ifTypeAdjustValue(String type, String value) {
        switch (type) {
            case "int":
                return Patterns.ifMatches(Patterns.getPatterns().typeInt, value);
            case "double":
                return Patterns.ifMatches(Patterns.getPatterns().typeDouble, value);
            case "boolean":
                return Patterns.ifMatches(Patterns.getPatterns().typeBoolean, value);
            case "String":
                return Patterns.ifMatches(Patterns.getPatterns().typeString, value);
            case "char":
                return Patterns.ifMatches(Patterns.getPatterns().typeChar, value);
        }
        return false;
    }

    /*
    Returns true if the given list is a singleton of empty string and false otherwise
     */
    static boolean thereNoParameters(String[] params) {
        return params.length == SINGLETON && params[0].trim().equals(EMPTY_STR);
    }

    /*
    Gets the content after the last appearance
     */
    static String getContentAfter(String line, String str) {
        int start = line.lastIndexOf(str);

        return start == NOT_FOUND ? "-" : line.substring(start + 1);
    }

    /*
    Gets the content of the brackets in the given command line.
     */
    static String getBracketsContent(String line) {
        int start = line.indexOf(OPEN_ROUND_BRACKET) + 1;
        int end = line.lastIndexOf(CLOSE_ROUND_BRACKET);
        return line.substring(start, end);
    }

    static String getContentBefore(String line, String str) {
        int end = line.indexOf(str);
        return line.substring(BEGIN_INDEX, end).trim();
    }

    /*
    This method is given a command line and returns the line without the first word
     */
    static String removeFirstWord(String line) {
        String[] arr = line.split(SPACE, 2);
        line = arr[FIRST_WORD_PLACE];
        return line;
    }

    static void checkDouble(String value) throws SyntaxException {
        try {
            Double.parseDouble(value);
        } catch (NumberFormatException e) {
            throw new SyntaxException("Unsupported double format");
        }
    }


}
