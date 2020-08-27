package oop.ex6.main;

import oop.ex6.exceptions.NamesException;
import oop.ex6.exceptions.ReturnException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;

/* This class represent a method scope */
class MethodScope extends Scope {


	MethodScope(String name, Scope parent, ArrayList<String> lines, HashMap<String, ArrayList<VarWrapper>>
			methods, int start, int end) throws NamesException {
		super(parent, lines, methods, start, end);
		checkMethodName(name);

	}

	/*
	Check if the return statement is valid(in the right place and valid command)
	*/
	void checkReturnStatement(int returnIndex) throws ReturnException {
		if (!Patterns.ifMatches(Patterns.getPatterns().returnPattern, lines.get(returnIndex)))
			throw new ReturnException();
	}

	/*
	Checks if the given method name is valid
	 */
	private void checkMethodName(String methodName) throws NamesException {
		Matcher methodNameFormat = Patterns.getPatterns().methodName.matcher(methodName);
		if (!methodNameFormat.matches()) {
			throw new NamesException();
		}
	}
}
