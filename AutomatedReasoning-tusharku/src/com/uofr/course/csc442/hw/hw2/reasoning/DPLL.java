package com.uofr.course.csc442.hw.hw2.reasoning;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.uofr.course.csc442.hw.hw2.reasoning.model.Connective;
import com.uofr.course.csc442.hw.hw2.reasoning.model.Sentence;
import com.uofr.course.csc442.hw.hw2.reasoning.utils.SentenceUtils;

/**
 * Main class to handle the workflow of DPLL algorithn.
 * It will accept as input a conjuction of clauses 
 * and will try to see if the given query can be 
 * concluded from the knowledge base provided.
 * @author tusharkumar
 *
 */
public class DPLL {
	private static int countOfCalls = 0;

	/**
	 * Inner class to identify the symbol name
	 * and its value(True, False) while 
	 * executing the DPLL algorithm.
	 * @author tusharkumar	 
	 */
	static class SymbolValue {
		
		private String symbol;
		private Boolean value;
		
		public SymbolValue(String symbol, Boolean value) {
			this.symbol = symbol;
			this.value = value;
		}
		
		public String getSymbol() {
			return symbol;
		}
		public void setSymbol(String symbol) {
			this.symbol = symbol;
		}
		public Boolean getValue() {
			return value;
		}
		public void setValue(Boolean value) {
			this.value = value;
		}			
	}
	
	/**
	 * Method to check if the input statement
	 * is a satisfied in a possible world represented
	 * by the given model
	 * @param clauses
	 * @param symbols
	 * @param model
	 * @return
	 */
	private static boolean isSatisfiable(Set<Sentence> clauses, Set<String> symbols, Map<String, Boolean> model) {
		countOfCalls++;
		boolean isEveryClauseTrueInModel = isEveryClauseTrueInModel(clauses, model);
		if(isEveryClauseTrueInModel) {
			return true;
		}
		
		boolean isSomeClauseFalseInModel = isSomeClauseFalseInModel(clauses, model);
		if(isSomeClauseFalseInModel) {
			return false;
		}
		
		SymbolValue symbolValue = getPureSymbol(symbols, clauses, model);
		if(symbolValue != null) {
			Set<String> newSymbolSet = new HashSet<String>(symbols);
			newSymbolSet.remove(symbolValue.getSymbol());
			
			Map<String, Boolean> newModel = new HashMap<String, Boolean>(model);
			newModel.put(symbolValue.getSymbol(), symbolValue.getValue());
			return isSatisfiable(clauses, newSymbolSet, newModel);
		}
		
		symbolValue = getUnitClause(clauses, model);
		
		if(symbolValue != null) {
			Set<String> newSymbolSet = new HashSet<String>(symbols);
			newSymbolSet.remove(symbolValue.getSymbol());
			
			Map<String, Boolean> newModel = new HashMap<String, Boolean>(model);
			newModel.put(symbolValue.getSymbol(), symbolValue.getValue());
			return isSatisfiable(clauses, newSymbolSet, newModel);
			
		}
		
		String symbol = (String) symbols.toArray()[0];
		Set<String> remainingSymbols = new HashSet<String>(symbols);
		remainingSymbols.remove(symbol);
		
		Map<String, Boolean> newModelWithTruthVal = new HashMap<String, Boolean>(model);
		newModelWithTruthVal.put(symbol, true);						
		Map<String, Boolean> newModelWithFalseVal = new HashMap<String, Boolean>(model);
		newModelWithFalseVal.put(symbol, false);
				
		return (isSatisfiable(clauses, remainingSymbols, newModelWithTruthVal)
				|| (isSatisfiable(clauses, remainingSymbols, newModelWithFalseVal)));
	}

	/**
	 * Method to check for presence of a unit clause. 
	 * In case one is present it returns the literal 
	 * string for the clause a possible boolean value
	 * to be assigned to the clause.
	 * @param clauses
	 * @param model
	 * @return
	 */
	private static SymbolValue getUnitClause(Set<Sentence> clauses, Map<String, Boolean> model) {
		for(Sentence clause : clauses) {			
			if(clause.isSymbol()) {
				if(!model.containsKey(clause.getSymbol())){
					return new SymbolValue(clause.getSymbol(), Boolean.TRUE);
				}
			}
			if(Connective.NOT.equals(clause.getConnective())) {
				Sentence connectedChild = clause.getConnectedChildred()[0];
				String childSymbol =  connectedChild.getSymbol();
				if(!model.containsKey(childSymbol)){
					return new SymbolValue(childSymbol, Boolean.FALSE);
				}
			}
		}
		return null;
	}

	/**
	 * Method to check presence of pure symbols
	 * @param symbols
	 * @param clauses
	 * @param model
	 * @return
	 */
	private static SymbolValue getPureSymbol(Set<String> symbols, Set<Sentence> clauses, Map<String, Boolean> model) {
		Set<Sentence> clausesWithNonTrueValue = getClausesWithNonTrueValue(clauses, model);
		List<Set<String>> pureSymbols = SentenceUtils.getPureSymbols(symbols, clausesWithNonTrueValue);
		
		Set<String> truthSymbols = pureSymbols.get(1);
		Set<String> falseSymbols = pureSymbols.get(0);
		if(truthSymbols.size() > 0) {
			String firstTruthSymbol = (String) truthSymbols.toArray()[0];
			return new SymbolValue(firstTruthSymbol, Boolean.TRUE);
		}
		if(falseSymbols.size() > 0) {
			String firstFalseSymbol = (String) falseSymbols.toArray()[0];
			return new SymbolValue(firstFalseSymbol, Boolean.FALSE);
		}
		return null;
	}

	private static Set<Sentence> getClausesWithNonTrueValue(Set<Sentence> clauses, Map<String, Boolean> model) {
		Set<Sentence> clausesWithNonTrueValue = new HashSet<Sentence>();
		for(Sentence clause : clauses) {
			Boolean ret = SentenceEvaluator.evaluateSentenceOnModel(clause, model);
			if(ret == null || ret != true) {
				clausesWithNonTrueValue.add(clause);
			}
		}
		return clausesWithNonTrueValue;
	}

	private static boolean isSomeClauseFalseInModel(Set<Sentence> clauses, Map<String, Boolean> model) {
		for(Sentence clause : clauses) {
			Boolean ret = SentenceEvaluator.evaluateSentenceOnModel(clause, model);
			if(ret !=null && ret == false) {
				return true;
			}
		}
		return false;
	}

	private static boolean isEveryClauseTrueInModel(Set<Sentence> clauses, Map<String, Boolean> model) {		
		for(Sentence clause : clauses) {
			Boolean ret = SentenceEvaluator.evaluateSentenceOnModel(clause, model);
			if( ret == null || ret != true) {
				return false;
			}
		}
		return true;
	}

	private static Set<String> extractSymbolsFromSentence(Sentence inputSentence) {
		return inputSentence.getSymbols();
	}

	private static Set<Sentence> extractClauseFromSentence(Sentence inputSentence) {
		Set<Sentence> clauses = new HashSet<Sentence>();
		if(inputSentence.getConnective().equals(Connective.AND)) {
			for(Sentence clause : inputSentence.getConnectedChildred()) {
				clauses.add(clause);
			}
		}
		return clauses;
	}
	
	/**
	 * Method to check if the input statement
	 * is satisfied in any possible world.
	 * @param inputSentence
	 * @return
	 */
	public static boolean isSatisfiable(Sentence inputSentence) {
		Set<Sentence> clauses = extractClauseFromSentence(inputSentence);
		Set<String> symbols = extractSymbolsFromSentence(inputSentence);
		Map<String, Boolean> model = new HashMap<String, Boolean>();
		return isSatisfiable(clauses, symbols, model);
	}

	public static int getCountOfCalls() {
		return countOfCalls;
	}
	
	public static void setCountOfCalls(int newCountOfCalls) {
		countOfCalls = newCountOfCalls; 
	}
}
