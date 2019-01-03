package com.uofr.course.csc442.hw.hw2.reasoning;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.uofr.course.csc442.hw.hw2.reasoning.model.Sentence;


/**
 * Class to check if a given proposition
 * can be derived from the input knowledge base or not
 * using the truth table enumeration technique
 * @author tusharkumar
 *
 */
public class PropositionValidator {	
	private static int countOfCalls = 0;
	
	/**
	 * Method to check for entailment of a
	 * given proposition in an input knowledge base
	 * @param knowledgeBase
	 * @param proposition
	 * @return
	 */
	public static boolean checkEntailment(Sentence knowledgeBase, Sentence proposition) {
		Set<String> symbols = knowledgeBase.getSymbols();
		symbols.addAll(proposition.getSymbols());
		Map<String, Boolean> model = new HashMap<String, Boolean>(); 
		return checkEntailRecursive(knowledgeBase, proposition, model, symbols);
	}
	
	private static boolean checkEntailRecursive(Sentence knowledgeBase, 
			Sentence proposition, 
			Map<String, Boolean> model,
			Set<String> symbols) {
		countOfCalls++;
		if(symbols.isEmpty()) {
			if(PLTrue(knowledgeBase, model)) {
				if(!PLTrue(proposition, model)) {
					System.out.println(model);
					return false;
				}
				else {
					return true;
				}
			}
			else {
				return true;
			}
		}
		else {
			String symbol = (String) symbols.toArray()[0];
			Set<String> remainingSymbols = new HashSet<String>(symbols);
			remainingSymbols.remove(symbol);
			
			Map<String, Boolean> newModelWithTruthVal = new HashMap<String, Boolean>(model);
			newModelWithTruthVal.put(symbol, true);						
			Map<String, Boolean> newModelWithFalseVal = new HashMap<String, Boolean>(model);
			newModelWithFalseVal.put(symbol, false);
			
			return (checkEntailRecursive(knowledgeBase, proposition, newModelWithTruthVal, remainingSymbols))
				&& (checkEntailRecursive(knowledgeBase, proposition, newModelWithFalseVal, remainingSymbols));						
		}
	}
	
	/**
	 * Method to find the truth or falsehood of a given 
	 * sentence with respect to the give possible 
	 * world created from the set of assignments mentioned
	 * in the input model
	 * @param sentence
	 * @param model
	 * @return
	 */
	private static boolean PLTrue(Sentence sentence, Map<String, Boolean> model) {
		return SentenceEvaluator.evaluateSentenceOnModel(sentence, model);
	}

	public static int getCountOfCalls() {
		return countOfCalls;
	}
	
	public static void setCountOfCalls(int newCountOfCalls) {
		countOfCalls = newCountOfCalls; 
	}
}
