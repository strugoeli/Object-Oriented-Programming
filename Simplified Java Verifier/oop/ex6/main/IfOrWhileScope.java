package oop.ex6.main;

import oop.ex6.exceptions.ConditionException;

import java.util.ArrayList;
import java.util.HashMap;

import static oop.ex6.main.ExpressionHandler.getBracketsContent;
import static oop.ex6.main.ExpressionHandler.ifTypeAdjustValue;
import static oop.ex6.main.ExpressionHandler.ifTypeAdjustValueType;
/* This class represent a scope of if or while loops. */
class IfOrWhileScope extends Scope {

	private static final String OR_AND_REGEX = "\\|\\||&&";

	/*
	Constructor
	 */

	IfOrWhileScope(String conditions, Scope parent, ArrayList<String> lines, HashMap<String,
			ArrayList<VarWrapper>> methods, int start, int end) throws ConditionException {
		super(parent, lines, methods, start, end);
		checkConditions(conditions);

	}

	/*
	Checks either the given condition expressions are valid
	 */
	private void checkConditions(String command) throws ConditionException {
		String expression = getBracketsContent(command);
		String[] dividedByConditions = expression.split(OR_AND_REGEX);

		for (String condition : dividedByConditions) {
			String trimCondition = condition.trim();
			VarWrapper conditionType = alreadyDeclare(trimCondition);

			if (conditionType != NOT_DECLARED) {
				if (!conditionType.isInitialized || !ifTypeAdjustValueType(BOOLEAN, conditionType.type))
					throw new ConditionException();

			} else if (!ifTypeAdjustValue(BOOLEAN, trimCondition) || trimCondition.equals("")) {
				throw new ConditionException();
			}
		}
	}
}
