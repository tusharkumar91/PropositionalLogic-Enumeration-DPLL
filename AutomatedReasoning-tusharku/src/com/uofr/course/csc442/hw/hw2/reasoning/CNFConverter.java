package com.uofr.course.csc442.hw.hw2.reasoning;

import java.util.ArrayList;
import java.util.List;

import com.uofr.course.csc442.hw.hw2.reasoning.model.Connective;
import com.uofr.course.csc442.hw.hw2.reasoning.model.Sentence;

/**
 * Utility class for converting any input of type sentence to 
 * Conjunctive normal form.
 * Follows the below mentioned steps:
 * a.) Convert BiImplication
 * b.) Convert Implication
 * c.) Push Negation Inwards
 * d.) Distribute Or over And
 * @author tusharkumar
 *
 */
public final class CNFConverter {
	
	private CNFConverter() {
		//Private constructor to prevent needless instantiation
	}

	/**
	 * Method for converting an implication to its CNF form
	 * @param input
	 */
	private static void convertImplication(Sentence input) {
		if(input.isSymbol()) {
			return;
		}
		for(Sentence sentence : input.getConnectedChildred()) {
			convertImplication(sentence);
		}
		
		if(Connective.IMPLIES == input.getConnective()) {
			Sentence notLeft = new Sentence(Connective.NOT, new Sentence[]{input.getConnectedChildred()[0]});
			input.setConnective(Connective.OR);
			input.setConnectedChildren(new Sentence[]{notLeft, input.getConnectedChildred()[1]});			
		}
	}
	
	/**
	 * Method for converting an biImplication to its CNF form
	 * @param input
	 */
	private static void convertBiConditional(Sentence input) {
		if(input.isSymbol()) {
			return;
		}
		for(Sentence sentence : input.getConnectedChildred()) {
			convertBiConditional(sentence);
		}
		
		if(Connective.IFF == input.getConnective()) {
			Sentence left = input.getConnectedChildred()[0];			
			Sentence right = input.getConnectedChildred()[1];
						
			Sentence leftImpRight = new Sentence(Connective.IMPLIES, new Sentence[]{left, right});
			Sentence RightImpLeft = new Sentence(Connective.IMPLIES, new Sentence[]{right, left});		
			
			input.setConnective(Connective.AND);
			input.setConnectedChildren(new Sentence[]{leftImpRight, RightImpLeft});
		}
	}	
	
	/**
	 * Method pushing the negation inwards 
	 * @param input
	 */
	private static Sentence pushNegation(Sentence input) {
		if(input.isSymbol()) {
			return input;
		}

		if(Connective.NOT == input.getConnective()) {
			if(input.getConnectedChildred()[0].isSymbol()) {
				return input;
			}
			else {
				Connective connective = input.getConnectedChildred()[0].getConnective();
				switch (connective) {
				case NOT:
					Sentence innerNot = input.getConnectedChildred()[0].getConnectedChildred()[0];
					return pushNegation(innerNot);
				case AND:
					List<Sentence> tempAndOutput = new ArrayList<Sentence>();
					for(Sentence part: input.getConnectedChildred()[0].getConnectedChildred()) {
						Sentence tempSentence = pushNegation(new Sentence(Connective.NOT, new Sentence[]{part}));
						tempAndOutput.add(tempSentence);
					}
					return new Sentence(Connective.OR, tempAndOutput.toArray(new Sentence[tempAndOutput.size()]));
				case OR:
					List<Sentence> tempOROutput = new ArrayList<Sentence>();
					for(Sentence part: input.getConnectedChildred()[0].getConnectedChildred()) {
						Sentence tempSentence = pushNegation(new Sentence(Connective.NOT, new Sentence[]{part}));
						tempOROutput.add(tempSentence);
					}
					return new Sentence(Connective.AND, (Sentence[]) tempOROutput.toArray(new Sentence[tempOROutput.size()]));
				default:
					break;
				}					
			}
		}
		return input;
	}
	
	/**
	 * Method for handling the scenario when OR is the
	 * connective between two sentence and the entire 
	 * group of sentences needs to be converted 
	 * to CNF 
	 * @param input
	 * @return
	 */
	private static Sentence convertCNFForOrCase(Sentence input) {
		if(input.isSymbol()) {
			return input;
		}
		if(input.getConnectedChildred().length == 1) {
			return input;
		}
		
		Sentence leftInput = convertToCNF(input.getConnectedChildred()[0]);
		int index = 1;
		while(index < input.getConnectedChildred().length) {
			List<Sentence> tempOutputList = new ArrayList<Sentence>();
			Sentence rightInput = convertToCNF(input.getConnectedChildred()[index]);
			Connective leftConnective = leftInput.getConnective();
			Connective rightConnective = rightInput.getConnective();
			
			if(leftConnective == Connective.OR && rightConnective == Connective.OR) {
				leftInput = (new Sentence(Connective.OR, new Sentence[]{leftInput, rightInput}));				
			}
			else if(leftConnective == Connective.AND && rightConnective == Connective.AND){
				for(Sentence leftPart : leftInput.getConnectedChildred()) {
					for(Sentence rightPartMember : rightInput.getConnectedChildred()) {
						Sentence tempMember = new Sentence(Connective.OR, new Sentence[] {leftPart, rightPartMember});
						tempOutputList.add(convertToCNF(tempMember));
					}				
				}
				leftInput = new Sentence(Connective.AND, tempOutputList.toArray(new Sentence[tempOutputList.size()]));
			}
			else if(rightConnective == Connective.AND){				
				for(Sentence rightPartMember : rightInput.getConnectedChildred()) {
					Sentence tempMember = new Sentence(Connective.OR, new Sentence[] {leftInput, rightPartMember});
					tempOutputList.add(convertToCNF(tempMember));
				}
				leftInput = (new Sentence(Connective.AND, tempOutputList.toArray(new Sentence[tempOutputList.size()])));
			}
			else if(leftConnective == Connective.AND){				
				for(Sentence leftPartMember : leftInput.getConnectedChildred()) {
					Sentence tempMember = new Sentence(Connective.OR, new Sentence[] {leftPartMember, rightInput});
					tempOutputList.add(convertToCNF(tempMember));
				}
				leftInput = (new Sentence(Connective.AND, tempOutputList.toArray(new Sentence[tempOutputList.size()])));
			}
			else {
				leftInput = (new Sentence(Connective.OR, new Sentence[]{leftInput, rightInput}));	
			}
			index++;
		}
		return leftInput;
	}
	
	private static void updateClauseList(Sentence input, List<Sentence> clauseList) {
		if(input.isSymbol() || input.getConnectedChildred() == null 
				|| input.getConnectedChildred().length == 1
				|| Connective.AND != input.getConnective()) {
			clauseList.add(input);			
		}
		else {
			for(Sentence clause : input.getConnectedChildred()) {
				updateClauseList(clause, clauseList);
			}
		}
	}
	/**
	 * Method to converting the given input sentence 
	 * to CNF using the rules
	 * @param input
	 * @return
	 */
	public static Sentence convertToCNF(Sentence input) {
		if(input.isSymbol()) {
			return input;
		}
		
		convertBiConditional(input);
		convertImplication(input);
		
		input = pushNegation(input);
		
		if(Connective.AND == input.getConnective()) {
			Sentence[] outputChildren = new Sentence[input.getConnectedChildred().length];
			Sentence[] inputChildren = input.getConnectedChildred();
			for(int i=0, length=inputChildren.length; i< length; i++) {
				outputChildren[i] = convertToCNF(inputChildren[i]);
			}			
			if(outputChildren.length == 1) {
				return outputChildren[0];
			}
			
			List<Sentence> clauseList = new ArrayList<Sentence>(); 
			Sentence output = new Sentence(Connective.AND, outputChildren);
			updateClauseList(output, clauseList);
			return new Sentence(Connective.AND, clauseList.toArray(new Sentence[clauseList.size()]));
		}
		
		if(Connective.OR == input.getConnective()) {
			return convertCNFForOrCase(input);
		}		
		return input;
	}
}
