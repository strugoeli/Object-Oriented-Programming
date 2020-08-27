package oop.ex6.main;

import java.util.ArrayList;

 class MainScope extends Scope {
	private static final int BEGIN_INDEX = 0;

	/*
	Main scope Constructor
	 */
	MainScope(ArrayList<String> lines) {
		this.isMainScope = true;
		this.lines = lines;
		this.start = BEGIN_INDEX;
		this.end = lines.size();
		localVarNames = new ArrayList<>();
		globalVarNames = new ArrayList<>();
	}
}

