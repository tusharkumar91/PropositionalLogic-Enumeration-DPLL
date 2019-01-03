package com.uofr.course.csc442.hw.hw2.reasoning.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.uofr.course.csc442.hw.hw2.reasoning.model.Connective;
import com.uofr.course.csc442.hw.hw2.reasoning.model.Sentence;

/**
 * Class to wrap around all utility methods which 
 * are required on class {@link Sentence}
 * @author tusharkumar
 *
 */
public final class SentenceUtils {
	private SentenceUtils() {
		//No constructor needed for this
	}
	
	/**
	 * Used by DPLL to find out pure symbols in the list 
	 * of clauses. Returns the list of literals which
	 * were always used in their positive form(A) and also
	 * list of literals always used in negated form(!B) by 
	 * scanning the entire list of clauses.
	 * @param symbols
	 * @param clauses
	 * @return
	 */
	public final static List<Set<String>> getPureSymbols(Set<String> symbols, Set<Sentence> clauses) {
		List<Set<String>> symbolList = new ArrayList<Set<String>>();
		symbolList.add(new HashSet<String>());
		symbolList.add(new HashSet<String>());
		
		for(Sentence clause : clauses) {
			updateTrueFalseSymbols(clause, symbols, symbolList);
		}
		Set<String> onlyTruthSymbols = new HashSet<String>(symbolList.get(1));
		onlyTruthSymbols.removeAll(symbolList.get(0));
		
		Set<String> onlyFalseSymbols = new HashSet<String>(symbolList.get(0));
		onlyFalseSymbols.removeAll(symbolList.get(1));
		
		symbolList.set(0, onlyFalseSymbols);
		symbolList.set(1, onlyTruthSymbols);
		return symbolList;
	}

	private final static void updateTrueFalseSymbols(Sentence clause, Set<String> symbols, List<Set<String>> symbolList) {
		if(clause.isSymbol()) {
			String symbol = clause.getSymbol();
			if(symbols.contains(symbol)) {
				symbolList.get(1).add(clause.getSymbol());
			}
		}
		else {
			if(clause.getConnective().equals(Connective.NOT)) {
				if(symbols.contains(clause.getConnectedChildred()[0].getSymbol())) {
					symbolList.get(0).add(clause.getConnectedChildred()[0].getSymbol());
				}
			}
			else {
				for(Sentence sentence : clause.getConnectedChildred()) {
					updateTrueFalseSymbols(sentence, symbols, symbolList);
				}
			}
		}
	}
}
