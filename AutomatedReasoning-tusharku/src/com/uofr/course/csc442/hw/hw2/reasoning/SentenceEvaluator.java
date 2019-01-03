package com.uofr.course.csc442.hw.hw2.reasoning;

import java.util.Map;

import com.uofr.course.csc442.hw.hw2.reasoning.model.Sentence;

/**
 * Utility class to evaluate a sentence given a model.
 * Returns a Boolean object.
 * Note : This will return a value ONLY if its possible to compute.
 * If model does not know the value of a certain symbol
 * which is present in expression then it will return null.
 * Like in case A && B && C with no value of C in model.
 * However in the same model if we knew A is true then 
 * we would be able to compute A || B || C and return true.
 * @author tusharkumar
 *
 */
public final class SentenceEvaluator {
	
	private SentenceEvaluator(){
		//NO reason to have a constructor of utility class
	}
	
	/**
	 * Main method to evaluate the truth and falsehood
	 * of a given sentence with respect to a specific 
	 * assignment of literals it contains to true and
	 * false values provided in the model
	 * @param sentence
	 * @param model
	 * @return
	 */
	public final static Boolean evaluateSentenceOnModel(Sentence sentence, Map<String, Boolean> model) {
		if(sentence.isSymbol()) {
			if(model.get(sentence.getSymbol()) != null) {
				return model.get(sentence.getSymbol());
			}
			else{
				return null;
			}
		}
		else {
			switch(sentence.getConnective()) {
			case AND:
				return validateAnd(sentence, model);
			case OR:
				return validateOr(sentence, model);
			case NOT:
				return validateNot(sentence, model);
			case IFF:
				return validateIff(sentence, model);
			case IMPLIES:
				return validateImplies(sentence, model);
			default:
				return false;
			}
		}
	}
	
	private final static Boolean validateAnd(Sentence sentence, Map<String, Boolean> model) {
		for(Sentence connectedChild : sentence.getConnectedChildred()) {
			Boolean res = evaluateSentenceOnModel(connectedChild, model);
			if(res == null || res != true) {
				return res;
			}
		}
		return true;
	}
	
	private final static Boolean validateOr(Sentence sentence, Map<String, Boolean> model) {
		boolean isAnyNullValuePresent = false;
		for(Sentence connectedChild : sentence.getConnectedChildred()) {
			Boolean res = evaluateSentenceOnModel(connectedChild, model);
			if(res == null) {
				isAnyNullValuePresent = true;
			}
			if(res !=null && res == true) {
				return true;
			}
		}
		if(isAnyNullValuePresent) {
			return null;
		}
		return false;
	}
	
	private final static Boolean validateNot(Sentence sentence, Map<String, Boolean> model) {
		Boolean res = evaluateSentenceOnModel(sentence.getConnectedChildred()[0], model);
		if(res == null) {
			return null;
		}
		else {
			return !res;
		}
	}
	
	private final static Boolean validateIff(Sentence sentence, Map<String, Boolean> model) {
		Boolean resLeft = evaluateSentenceOnModel(sentence.getConnectedChildred()[0], model);
		if(resLeft == null) {
			return null;
		}
		Boolean resRight = evaluateSentenceOnModel(sentence.getConnectedChildred()[1], model);
		if(resRight == null) {
			return null;
		}
		return (resLeft == resRight);
	}
	
	private final static Boolean validateImplies(Sentence sentence, Map<String, Boolean> model) {
		Boolean resLeft = evaluateSentenceOnModel(sentence.getConnectedChildred()[0], model);		
		if(resLeft == null) {
			Boolean resRight = evaluateSentenceOnModel(sentence.getConnectedChildred()[1], model);	
			if(resRight != null && resRight == true) {
				return true;
			}
			else {
				return null;
			}
		}
		else {
			if(resLeft == false) {
				return true;
			}
			else {
				Boolean resRight = evaluateSentenceOnModel(sentence.getConnectedChildred()[1], model);	
				return resRight;
			}
			
		}
	}
}
