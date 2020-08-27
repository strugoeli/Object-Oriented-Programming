package oop.ex6.main;

import oop.ex6.exceptions.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.regex.Matcher;

import static oop.ex6.main.ExpressionHandler.*;

/*The class compiler get a file and do the initial checks on the file make sure each line ends well ,
`the brackets are balanced . initial globals variables and method, and create all the scope in the
 code.*/
class Compiler {

    /*	Constants */
    private static final String EMPTY = "";
    private static final String COMMENT = "//";
    private static final String NEW_SCOPE = "{";
    private static final String END_SCOPE = "}";
    private static final String END_COMMAND = ";";
    private static final String SINGLE_SPACE = " ";
    private static final int BALANCED_BRACKETS = 0;
    private static final String COMMA = ",";
    private static final String VOID_TYPE = "void ";
    private static final int TYPE_LENGTH = 5;
    private static final String SINGLE_SPACE_OR_MORE = "\\s+";
    private static final int FIRST_INDEX = 0;
    private static final String FINAL_KEY = "final";
    private static final int FINAL_KEY_LENGTH = 6;
    private static final boolean IS_INITIALIZED = true;
    private static final String NO_PARAM = "";
    private static final int NAME_SEQUENCE = 2;
    private static final int TYPE_SEQUENCE = 1;


    /* Data Members */

    //mainLine Represents the code line of the main scope innerLines the inner scopes - within methods
    private ArrayList<String> mainLines, innerLines;

    //Represents the file's which has to be parsed path
    private String filePath;

    //Represents a dictionary of methods where the key is the method's name and the value is it's parameters
    private HashMap<String, ArrayList<VarWrapper>> methodsDic;


    /* Constructor */
    Compiler(String theFilePath) {
        filePath = theFilePath;
        innerLines = new ArrayList<>();
        mainLines = new ArrayList<>();
        methodsDic = new HashMap<>();
    }


    /*
    The method is responsible for locating the file by the given path, reading the file's content and finally
    creating two objects, one of the main scope and the secondary scope.
    */
    void readLines() throws IOException, CompilingException {
        String CurrentLine;
        int bracketsChecker = BALANCED_BRACKETS;
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));

        while ((CurrentLine = bufferedReader.readLine()) != null) {
            checkCommentLine(CurrentLine);
            String updatedLine = CurrentLine.trim().replaceAll(SINGLE_SPACE_OR_MORE, SINGLE_SPACE);
            bracketsChecker = readActualCodeLines(bracketsChecker, updatedLine);
        }
        checkBracketsBalance(bracketsChecker);
        MainScope mainScope = new MainScope(mainLines);
        mainScope.checkLines();
        Scope scope = new Scope(mainScope, innerLines, methodsDic, FIRST_INDEX, innerLines.size());
        scope.checkLines();
    }

    /*
    This method is responsible for reading the active code lines - without comments and empty lines
    and returns the current balance of the curly brackets.
     */
    private int readActualCodeLines(int bracketsChecker, String updatedLine) throws CompilingException {
        if (!updatedLine.equals(EMPTY) && !updatedLine.startsWith(COMMENT)) {
            checkEndOfLine(updatedLine);
            if (bracketsChecker == BALANCED_BRACKETS && !updatedLine.endsWith(NEW_SCOPE))
                mainLines.add(updatedLine);
            else
                innerLines.add(updatedLine);
            bracketsChecker = getBracketsChecker(updatedLine, bracketsChecker);
        }
        return bracketsChecker;
    }

    /*
    Checks if the given line is a comment and if it is one so it checks if it fits to the code format.
    if it does not so an Exception is thrown.
     */
    private void checkCommentLine(String line) throws SyntaxException {
        if (line.contains(COMMENT)) {
            if (Patterns.ifMatches(Patterns.getPatterns().iLegalComment, line))
                throw new SyntaxException("Space before comment");
        }
    }

    /*
    Checks if the the curly brackets are balanced and throw and Exception otherwise
     */
    private void checkBracketsBalance(int bracketsChecker) throws UnbalancedBracketsException {
        if (bracketsChecker != BALANCED_BRACKETS)
            throw new UnbalancedBracketsException();
    }

    private int getBracketsChecker(String line, int bracketsChecker) throws CompilingException {
        if (line.endsWith(NEW_SCOPE)) {
            if (!Patterns.ifMatches(Patterns.getPatterns().scopePattern, line))
                throw new FormatScopeException();
            if (line.startsWith(VOID_TYPE)) {
                defineMethod(line.substring(TYPE_LENGTH));
            }
            bracketsChecker++;
        }
        if (line.endsWith(END_SCOPE))
            bracketsChecker--;
        return bracketsChecker;
    }

    /*
    Checks if the given line fit to the code format and throws an appropriate Exception otherwise
     */
    private void checkEndOfLine(String line) throws SyntaxException {
        if (!line.endsWith(NEW_SCOPE) && !line.endsWith(END_SCOPE) && !line.endsWith(END_COMMAND))
            throw new SyntaxException("Wrong end of line");
        if (Patterns.ifMatches(Patterns.getPatterns().badLineFormat, line)) {
            throw new SyntaxException("bad format line");
        }
    }

    /*
    This method is given command line that represents definition of a new method and defines the method
    by stores it's name and parameters by some data structure
     */
    private void defineMethod(String command) throws CompilingException {
        String methodName = getContentBefore(command, OPEN_ROUND_BRACKET);
        String[] parameters = getBracketsContent(command).split(COMMA);
        ArrayList<VarWrapper> paramTypes = getParametersTypes(parameters);
        methodsDic.put(methodName, paramTypes);
    }


    /*
    This method is given a list of parameters and returns an Array list od VarWrapper objects that defined
    by the given parameters.
     */
    private ArrayList<VarWrapper> getParametersTypes(String[] parameters) throws SyntaxException, VariableException {

        HashSet<String> names = new HashSet<>();
        ArrayList<VarWrapper> methodVars = new ArrayList<>();

        for (String parameter : parameters) {
            boolean isFinal = parameter.startsWith(FINAL_KEY);
            parameter = isFinal ? parameter.substring(FINAL_KEY_LENGTH) : parameter;
            Matcher types = checkParameters(parameters, parameter);
            String paramType = types.group(TYPE_SEQUENCE);
            String paraName = types.group(NAME_SEQUENCE);
            checkNameDuplications(names, paraName);
            if (types.find()) {
                VarWrapper variable = new VarWrapper(paramType, paraName, isFinal, IS_INITIALIZED);
                methodVars.add(variable);
            }
        }
        return methodVars;
    }

    /*
    This method is given a HashSet and adds to it the given parameter's name.
    if the parameter's is not successfully added it means that there is duplication and the method throws
    an appropriate Exception.
     */
    private void checkNameDuplications(HashSet<String> names, String paraName) throws DeclareVariableException {
        if (paraName == null)
            return;
        boolean isAdded = names.add(paraName);
        if (!isAdded)
            throw new DeclareVariableException();

    }

    /*
    Checks either the given parameter is a valid one and throws an appropriate Exception otherwise.
     */
    private Matcher checkParameters(String[] parameters, String parameter) throws SyntaxException {
        if ((parameters.length > 1 && parameter.trim().equals(NO_PARAM))) {
            throw new SyntaxException("Operators are not allow in the Method's Parameters field");
        }
        Matcher types = Patterns.getPatterns().typesParam.matcher(parameter.trim());
        if (!types.matches()) {
            throw new SyntaxException("Unsupported command in the Method's parameters field");
        }
        return types;
    }
}
