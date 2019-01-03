package com.uofr.course.csc442.hw.hw2.reasoning.model;

import java.util.HashSet;
import java.util.Set;

/**
 * Class to represent a Sentence or expression.
 * Its constructed as a tree with one single connective 
 * and a bunch of connecting children.
 * Example : (A && B && C && (!D) will be expressed in 
 * program as the following:
 * Root Node && with three children
 * 		1: Node A
 * 		2: Node B
 * 		3: Node C
 *		4: Node ! with one child
 *				4a: D
 * @author tusharkumar
 *
 */
public class Sentence {
	private boolean isSymbol;
	private Connective connective;
	private String symbol;
	private Sentence[] connectedChildren;
		
	public void setConnective(Connective connective) {
		this.connective = connective;
	}
	
	public void setConnectedChildren(Sentence[] connectedChildren) {
		this.connectedChildren = connectedChildren;
	}
	
	public void setIsSymbol(boolean isSymbol) {
		this.isSymbol = isSymbol;
	}
	
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	
	public Sentence(String symbol) {
		isSymbol = true;
		this.symbol= symbol;		
	}
	
	public Sentence(Connective connective, Sentence[] connectedChildren) {
		this.connective = connective;
		this.connectedChildren = connectedChildren;
	}
	
	public boolean isSymbol() {
		return this.isSymbol;
	}
	
	public Connective getConnective() {
		return this.connective;
	}
	
	public Sentence[] getConnectedChildred() {
		return connectedChildren;
	}
	
	public String getSymbol(){
		if(this.isSymbol) {
			return symbol;
		}
		return null;
	}
	
	public Set<String> getSymbols(){
		Set<String> symbols = new HashSet<String>();
		if(this.isSymbol) {
			symbols.add(symbol);
		}
		else {
			for(Sentence connectedChild : connectedChildren) {
				symbols.addAll(connectedChild.getSymbols());
			}
		}
		return symbols;
	}
	
	public String getSentenceText() {
		return getSentenceText(true);
	}
	
	/**
	 * Helper method to get the textual form 
	 * of this expression tree
	 * Example : (A && B && C && (!D))
	 * @param indentTopLevel
	 * @return
	 */
	public String getSentenceText(boolean indentTopLevel) {
		StringBuilder strBuilder = new StringBuilder();
		if(isSymbol) {
			strBuilder.append(symbol);
		}
		else {
			if(Connective.NOT.equals(connective)){
				strBuilder.append('(');
				strBuilder.append(connective.printConnective());
				for(Sentence connectedChild : connectedChildren) {
					strBuilder.append(connectedChild.getSentenceText(false));
				}
				strBuilder.append(')');
			}
			else {
				strBuilder.append("(");
				for(int i=0; i<connectedChildren.length-1; i++) {
					strBuilder.append(connectedChildren[i].getSentenceText(false));
					
					if(indentTopLevel) {
						strBuilder.append(" ");	
						//strBuilder.append("\n");
					}
					strBuilder.append(connective.printConnective());		
					if(indentTopLevel) {
						strBuilder.append(" ");	
						strBuilder.append("");
					}
				}
				strBuilder.append("");
				strBuilder.append(connectedChildren[connectedChildren.length-1].getSentenceText(false));
				strBuilder.append(")");
			}
		}
		return strBuilder.toString();
	}
	
	@Override
	public String toString() {
		return getSentenceText();
	}
}
