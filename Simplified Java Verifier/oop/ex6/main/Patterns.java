package oop.ex6.main;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*This is a singleTone class represent all the pattern we create to check the code */
class Patterns {

    /* private object of this class */
    private static Patterns patterns = new Patterns();
    /*pattern of line start with one of the type */
    Pattern types;
    /* pattern of line end with ',;' */
    Pattern commaWithoutParam;
    /* pattern of legal param name */
    Pattern LegalParamName;
    /* pattern of type int */
    Pattern typeInt;
    /* pattern of type String */
    Pattern typeString;
    /* pattern of type char */
    Pattern typeChar;
    /* pattern of type boolean */
    Pattern typeBoolean;
    /* pattern of type double */
    Pattern typeDouble;
    /*pattern of line that start with letter */
    Pattern beginWithLetters;
    /* return statement pattern */
    Pattern returnPattern;
    /* pattern of a new scope */
    Pattern scopePattern;
    /* pattern represent illegal comment line */
    Pattern iLegalComment;
    /* pattern of line start with if or while*/
    Pattern ifOrWhile;
    /* pattern of line of declare a new variable */
    Pattern typesParam;
    /*pattern of bad line format  */
    Pattern badLineFormat;
    /*pattern of bad declaration format */
    Pattern badDeclareVarFormat;
    /*pattern of method name */
    Pattern methodName;
    /*pattern of legal method call */
    Pattern methodCall;


    /*Private constructor, initialize all the pattern */
    private Patterns() {
        types = Pattern.compile("(int|double|boolean|char|String) .*");
        iLegalComment = Pattern.compile("[^//].*");
        commaWithoutParam = Pattern.compile(".*,;");
        LegalParamName = Pattern.compile("(\\s*(((_)[\\w])|[a-zA-Z])[\\w_]*\\s*)");
        typeInt = Pattern.compile("-?[0-9][0-9]*");
        typeDouble = Pattern.compile("-?[0-9]*(\\.[0-9]*)?");
        typeBoolean = Pattern.compile("(true|false)\\s*|\\s*-?[0-9]*(\\.[0-9]*)?|-?[0-9][0-9]*");
        typeString = Pattern.compile("\"[^\"]*\"");
        typeChar = Pattern.compile("\'(.)\'");
        beginWithLetters = Pattern.compile("([a-z]|[A-Z]).*");
        returnPattern = Pattern.compile("\\s*return\\s*;\\s*");
        scopePattern = Pattern.compile("\\s*(void|while|if).*[(].*[)]\\s*[{]\\s*");
        ifOrWhile = Pattern.compile("\\s*(while|if).*[(].*[)]\\s*[{]\\s*");
        badLineFormat = Pattern.compile(".*([;]|[}]|[{]).*([;]|[}]|[{])");
        badDeclareVarFormat = Pattern.compile(".*\\s+(int |double |Sting |boolean |char ).*");
        typesParam = Pattern.compile("(int|double|boolean|char|String)\\s*([a-z]|[A-Z]\\s*)|(\\s*-?[0-9]*" +
                "(\\.[0-9]*)?)|-?[0-9][0-9]*\"");
        methodName = Pattern.compile("([a-z]|[A-Z])+([0-9]|(_))*\\s*");
        methodCall = Pattern.compile("([a-z]|[A-Z])+([0-9]\\s*[()].*|(_))*\\s*[()].*");


    }

    /* This method return the singleTone object of patterns */
    static Patterns getPatterns() {
        return patterns;
    }

    /*This method get a Pattern, and value and check if the value adjust to the pattern */
    static boolean ifMatches(Pattern pattern, String value) {
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }
}
