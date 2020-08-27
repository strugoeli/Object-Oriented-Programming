package oop.ex6.main;


import oop.ex6.exceptions.*;

import java.util.ArrayList;
import java.util.HashMap;

import static oop.ex6.main.ExpressionHandler.*;

class Scope {
	private static final String FINAL_MODIFIER = "final";
	private static final boolean IS_NOT_FINAL = false;
	private static final boolean IS_FINAL = true;
	private static final String END_SCOPE = "}";
	private static final String START_SCOPE = "{";
	private static final int VAL_PLACE = 1;
	private static final int NAME_PLACE = 0;
	private static final String EQUAL = "=";
	static final String BOOLEAN = "boolean";
	private static final String VOID = "void ";
	private static final String EQUAL_OR_SEMICOLON = "[=;]";
	private static final int BALANCED = 0;
	private static final Scope NO_PARENT = null;
	private static final String PARAM_SEPARATOR = ",";
	static final VarWrapper NOT_DECLARED = null;
	private static final int MOD_LEN = 5;
	private static final int FINAL_LEN = 6;


	/*List of local variables*/
	ArrayList<VarWrapper> localVarNames;
	/*List of global variables*/
	ArrayList<VarWrapper> globalVarNames;
	/*Dictionary of method names and there parameters*/
	private HashMap<String, ArrayList<VarWrapper>> methods;
	/*The command lines*/
	ArrayList<String> lines;
	/*flag that represents either this is the main scope or not*/
	Boolean isMainScope;
	/*The direct outer scope*/
	private Scope parent;
	/*start index of the code lines*/
	int start;
	/*End index of the code lines*/
	int end;

	/*
	Constructor
	 */
	Scope(Scope parent, ArrayList<String> lines, HashMap<String, ArrayList<VarWrapper>> methods, int
			start, int end) {

		localVarNames = new ArrayList<>();
		globalVarNames = getClone(parent.globalVarNames);
		globalVarNames.addAll(getClone(parent.localVarNames));
		this.methods = methods;
		this.parent = parent;
		this.lines = lines;
		this.start = start;
		this.end = end;
		this.isMainScope = false;
	}

	/*
	Default Constructor
	 */
	Scope() {
	}
	

	/*
	Gets the deep copy of the given ArrayList
	 */
	private ArrayList<VarWrapper> getClone(ArrayList<VarWrapper> listToCopy) {
		ArrayList<VarWrapper> copy = new ArrayList<>();
		for (VarWrapper var : listToCopy) {
			copy.add(new VarWrapper(var.type, var.name, var.isFinal, var.isInitialized));
		}
		return copy;
	}

	void checkLines() throws CompilingException {

		while (start < end) {

			String curLine = lines.get(start);

			/*Return command case*/
			if (Patterns.ifMatches(Patterns.getPatterns().returnPattern, curLine)) {
				// Return statement can only used within a method
				if (parent == null || parent.isMainScope) {
					throw new ReturnException();
				}
				start++;
				continue;
			}
			/*Return command case*/
			if (curLine.trim().equals(END_SCOPE)) {
				start++;
				continue;
			}
			/*final declaration command case*/
			if (curLine.startsWith(FINAL_MODIFIER)) {
				declarationOfNewVar(curLine.substring(FINAL_LEN), IS_FINAL);
				start++;
				continue;
			}
			/*Normal declaration command case*/
			if (Patterns.ifMatches(Patterns.getPatterns().types, curLine)) {
				declarationOfNewVar(curLine, IS_NOT_FINAL);
				start++;
				continue;
			}
			/*Method definition command case*/
			if (curLine.startsWith(VOID)) {
				//Method can only be defined in the main scope
				if (parent == null || !parent.isMainScope) {
					throw new MethodException();
				}
				String methodName = curLine.substring(MOD_LEN, curLine.indexOf(OPEN_ROUND_BRACKET));
				createMethodScope(methodName, start);
				continue;
			}
			/*If or while loops command case*/
			if (Patterns.ifMatches(Patterns.getPatterns().ifOrWhile, curLine)) {
				// If/While can only be used within a method
				if (parent.isMainScope) {
					throw new IfWhileException();
				}
				createIfWhileScope(curLine, start);
				continue;
			}
			/*Assignment command case*/
			if (curLine.contains(EQUAL)) {
				String type = getType(curLine);
				ifPossibleToChangeValue(curLine, type);
				start++;
				continue;
			}
			/*Calling method command case*/
			if (checkIfLegalToCallMethod(curLine)) {
				start++;
				continue;
			}
			throw new SyntaxException("Unrecognized statement");

		}
	}

	/*
	This method is given method name and the index of the line where it appears in the code
	and creates a new method scope(Scope object)
	 */
	private void createMethodScope(String methodName, int lineIndex) throws CompilingException {
		int start = lineIndex + 1;
		int end = getEndIndex(lineIndex);
		MethodScope newScope = new MethodScope(methodName, this, lines, methods, start, end);
		newScope.localVarNames.addAll(methods.get(methodName.trim()));
		newScope.checkReturnStatement(newScope.end - 2);
		newScope.checkLines();
	}


	/*
	This method is given a command that contains if or while loop and the index of the command where it
	appears in the code and creates a new scope
	 */
	private void createIfWhileScope(String command, int lineIndex) throws CompilingException {
		int start = lineIndex + 1;
		int end = getEndIndex(lineIndex);
		IfOrWhileScope newScope = new IfOrWhileScope(command, this, lines, methods, start, end);
		newScope.checkLines();
	}


	/*
	This method is responsible of the deceleration of a new variable
	 */
	private void declarationOfNewVar(String line, boolean isFinal) throws CompilingException {

		String type = getContentBefore(line, SPACE);
		line = removeFirstWord(line);
		initialTest(line);
		String[] expressions = line.split("[,;]");
		String name, val;

		for (String expression : expressions) {
			String[] nameAndVal = expression.split(EQUAL);
			name = nameAndVal[NAME_PLACE].trim();
			val = getContentAfter(expression, EQUAL).trim();
			checkParamName(name);
			boolean isInitialized = declareAndInitialize(isFinal, val, type, expression, nameAndVal);
			VarWrapper variable = new VarWrapper(type, name, isFinal, isInitialized);
			localVarNames.add(variable);
		}
	}

	/*this method get a declaration expression and declare about new variable and if it possible initial
	the variable*/
	private boolean declareAndInitialize(boolean isFinal, String val, String type, String expression, String[]
			nameAndVal) throws SyntaxException, VariableException {

		boolean isInitialized;
		if (expression.contains(EQUAL)) { //case the user declare and assign the variable
			if (nameAndVal.length != 2) {
				throw new SyntaxException("Bad format line");
			}
			if (!ifPossibleToChangeValue(expression, type)) {
				throw new VariableTypeException();
			}
			if (ifTypeAdjustValue("double", type))
				checkDouble(val);

			isInitialized = true;
		} else { //if the user only declare the variable
			if (isFinal) {
				throw new VariableFinalException();
			}
			isInitialized = false;
		}
		return isInitialized;
	}


	/*helper method that get a line represent initialization of param and make a basic validation test*/
	private void initialTest(String line) throws SyntaxException {
		if (Patterns.ifMatches(Patterns.getPatterns().badDeclareVarFormat, line)) {
			throw new SyntaxException("Cant declare the type twice in one line");
		}
		if (Patterns.ifMatches(Patterns.getPatterns().commaWithoutParam, line)) {
			throw new SyntaxException("Empty comma at the end");
		}
	}

	/*method that get a param name and check if the local or global ArrayList of variable contain var
	with the same name. this method first search at the local variable and then search in the global.
	if the variable was found, it returned . else returns null. */
	VarWrapper alreadyDeclare(String param) {
		VarWrapper var = existsInArrayList(param, localVarNames);
		if (var != null) return var;
		var = existsInArrayList(param, globalVarNames);
		return var;
	}

	/*Method that get a param, and arrayList of VarWrapper and search for exist variable with name that
	equal to the given param */
	private VarWrapper existsInArrayList(String param, ArrayList<VarWrapper> listToSearch) {
		for (VarWrapper var : listToSearch) {
			if (var.name.equals(param)) {
				return var;
			}
		}
		return null;
	}

	/*method that get expression of initialization, and wanted type. if the given wanted value (parameter
	 or value match to the given type, this method initial the variable and return true, else return
	 false */
	private boolean ifPossibleToChangeValue(String expression, String type) throws VariableException {
		String[] nameAndVal = expression.split(EQUAL_OR_SEMICOLON);
		String valNameOrValue = nameAndVal[VAL_PLACE].trim();
		String varName = nameAndVal[NAME_PLACE].trim();
		VarWrapper var = alreadyDeclare(varName);
		VarWrapper val = alreadyDeclare(valNameOrValue);
		if (var != null && var.isFinal) { //make sure we do not change a final variable
			throw new VariableFinalException();
		}
		if (val == null) {//initial value (not parameter)
			return initialParamWithValue(type, valNameOrValue, var);
		}
		if (!val.isInitialized) { //the value is parameter, check if we can use it
			throw new VariableInitializationException();
		}
		return ifTypeAdjustValueType(type, val.type);
	}

	/* Method that get variable, type and value, and initialize the variable if the value adjust the type.
	 if not, the method return false */
	private boolean initialParamWithValue(String type, String value, VarWrapper var) throws VariableException {
		if (ifTypeAdjustValue(type, value)) {//check if the given value adjust the type
			if (var != null) {
				var.isInitialized = true;
			}
			return true;
		}
		throw new VariableTypeException();
	}


	/*
	Gets the variable's type
	 */
	private String getType(String curLine) throws SyntaxException {
		VarWrapper var = alreadyDeclare(curLine.split(EQUAL)[NAME_PLACE].trim());
		if (var == null)
			throw new SyntaxException("Type is missing");
		return var.type;
	}


	/*
	Gets the index of the scope's end
	 */
	private int getEndIndex(int lineIndex) throws SyntaxException {
		int bracketCounter = BALANCED;
		for (int i = lineIndex; i < lines.size(); i++) {
			String curLine = lines.get(i);
			if (curLine.trim().endsWith(START_SCOPE))
				bracketCounter++;
			if (curLine.trim().endsWith(END_SCOPE))
				bracketCounter--;
			if (bracketCounter == BALANCED) {
				start = i;
				return i + 1;
			}
		}
		throw new SyntaxException("Missing end of line");
	}


	/*
	Check the validity of calling a method command line
	 */
	private boolean checkIfLegalToCallMethod(String line) throws VariableException, SyntaxException, MethodCallException {
		if (!Patterns.ifMatches(Patterns.getPatterns().methodCall, line))
			throw new SyntaxException("Bad format of call method");
		if (parent == NO_PARENT)
			throw new MethodCallException();

		String methodName = getContentBefore(line, OPEN_ROUND_BRACKET);
		String[] parameters = getBracketsContent(line).split(PARAM_SEPARATOR);
		if (methods.containsKey(methodName)) {
			return checkParameters(parameters, methods.get(methodName));
		}
		return false;
	}

	/*This method get a wanted param name, and check if the name is legal, and not exist */
	private void checkParamName(String paramName) throws CompilingException {

		if (!Patterns.ifMatches(Patterns.getPatterns().LegalParamName, paramName)) {
			throw new NamesException();
		}
		if (existsInArrayList(paramName, localVarNames) != null) {
			throw new DeclareVariableException();
		}
	}


	/*
	Checks the validity of the parameters that are given to a method when it called
	 */
	private boolean checkParameters(String[] params, ArrayList<VarWrapper> varTypes) throws VariableException,
			SyntaxException {

		if (thereNoParameters(params))
			return varTypes.size() == 0;

		if (params.length != varTypes.size()) {
			throw new SyntaxException("The number of the parameters do not match");
		}
		for (int i = 0; i < params.length; i++) {
			String param = params[i].trim();
			String expectedType = varTypes.get(i).type;
			if (ifTypeAdjustValue(expectedType, param))
				continue;
			VarWrapper var = alreadyDeclare(param);
			if (var == NOT_DECLARED || !var.type.equals(expectedType))
				throw new VariableTypeException();
			if (!var.isInitialized)
				throw new VariableInitializationException();
		}
		return true;
	}


}

