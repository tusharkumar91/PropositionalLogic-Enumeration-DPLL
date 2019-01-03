package com.uofr.course.csc442.hw.hw2.reasoning.model;

/**
 * ENUM for encapsulating all the different kinds
 * of connectives and the symbols one can use 
 * for them.
 * @author tusharkumar
 */
public enum Connective {

	NOT("!"),
	AND("&&"),
	OR("||"),
	IMPLIES("=>"),
	IFF("<=>");
	
	private String connectiveSymbol;
	
	private Connective(String connectiveSymbol) {
		this.connectiveSymbol = connectiveSymbol;
	}
		
	public String printConnective() {
		return connectiveSymbol;
	}	
}
