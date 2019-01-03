package com.uofr.course.csc442.hw.hw2.reasoning.problems;

import java.util.List;

import com.uofr.course.csc442.hw.hw2.reasoning.DPLL;
import com.uofr.course.csc442.hw.hw2.reasoning.PropositionValidator;
import com.uofr.course.csc442.hw.hw2.reasoning.model.AbstractReasoningProblem;
import com.uofr.course.csc442.hw.hw2.reasoning.model.Connective;
import com.uofr.course.csc442.hw.hw2.reasoning.model.Sentence;

/**
 * Class for capturing the doors and enlightenment
 * question part A(Smullyan's problem) mentioned
 * in the project requirements.
 * @author tusharkumar
 *
 */
public class DoorsOfEnlightenmentSmullyan extends AbstractReasoningProblem {

	@Override
	public Sentence generateKnowledgeBase() {
		Sentence x = new Sentence("X");
		Sentence y = new Sentence("Y");
		Sentence z = new Sentence("Z");
		Sentence w = new Sentence("W");
		Sentence aTruthTeller = new Sentence("A");
		Sentence bTruthTeller = new Sentence("B");
		Sentence cTruthTeller = new Sentence("C");
		Sentence dTruthTeller = new Sentence("D");
		Sentence eTruthTeller = new Sentence("E");
		Sentence fTruthTeller = new Sentence("F");
		Sentence gTruthTeller = new Sentence("G");
		Sentence hTruthTeller = new Sentence("H");

		
		Sentence oneGoodDoor = new Sentence(Connective.OR, new Sentence[]{x, y, z, w});		
		Sentence aSaysXIsGood = new Sentence(Connective.IFF, new Sentence[]{aTruthTeller, x});
		
		Sentence yOrZIsGood = new Sentence(Connective.OR, new Sentence[]{y, z});	
		Sentence bSaysYOrZIsGood = new Sentence(Connective.IFF, new Sentence[]{bTruthTeller, yOrZIsGood});
		
		Sentence aAndBKnights = new Sentence(Connective.AND, new Sentence[]{aTruthTeller, bTruthTeller});	
		Sentence cSaysAAndBKnights = new Sentence(Connective.IFF, new Sentence[]{cTruthTeller, aAndBKnights});
		
		Sentence xAndYIsGood = new Sentence(Connective.AND, new Sentence[]{x, y});	
		Sentence dSaysXAndYIsGood = new Sentence(Connective.IFF, new Sentence[]{dTruthTeller, xAndYIsGood});
		
		Sentence xAndZIsGood = new Sentence(Connective.AND, new Sentence[]{x, z});	
		Sentence eSaysXAndZIsGood = new Sentence(Connective.IFF, new Sentence[]{eTruthTeller, xAndZIsGood});
		
		Sentence dOrEKnight = new Sentence(Connective.OR, new Sentence[]{dTruthTeller, eTruthTeller});	
		Sentence fSaysDOrEKnights = new Sentence(Connective.IFF, new Sentence[]{fTruthTeller, dOrEKnight});
		
		Sentence cImpFKnight = new Sentence(Connective.IMPLIES, new Sentence[]{cTruthTeller, fTruthTeller});	
		Sentence gSaysCImpFKnight = new Sentence(Connective.IFF, new Sentence[]{gTruthTeller, cImpFKnight});
		
		Sentence gAndHKnights = new Sentence(Connective.AND, new Sentence[]{gTruthTeller, hTruthTeller});
		Sentence gAndHKnightsImpA = new Sentence(Connective.IMPLIES, new Sentence[]{gAndHKnights, aTruthTeller});	
		Sentence hSaysGAndHKnightsImpA = new Sentence(Connective.IFF, new Sentence[]{hTruthTeller, gAndHKnightsImpA});
		 
		Sentence knowledgeBase = new Sentence(Connective.AND, new Sentence[]{
				oneGoodDoor, aSaysXIsGood, bSaysYOrZIsGood, 
				cSaysAAndBKnights, dSaysXAndYIsGood, eSaysXAndZIsGood,
				fSaysDOrEKnights, gSaysCImpFKnight, hSaysGAndHKnightsImpA,
		});	
		return knowledgeBase;
	}

	@Override
	public String getProblemName() {
		StringBuilder str = new StringBuilder();
		str.append("\n======================================================\n");
		str.append(ProblemTypes.DOORS_OF_ENLIGHTENMENT_SMULLYAN_PROBLEM.getProblemName());
		str.append("\n======================================================\n");
		str.append("\n");
		return str.toString();
	}

	@Override
	public boolean checkEntailmentByEnumeration() {
		return PropositionValidator.checkEntailment(getKnowledgeBase(), 
				 getQuery());
	}

	@Override
	public long generateEnumerationResults() {
		PropositionValidator.setCountOfCalls(0);
		long startTime = System.currentTimeMillis();
		System.out.println("--------------------------------------");		
		System.out.println("Solving Problem using Enumeration Method");
		System.out.println("--------------------------------------");
		
		Sentence x = new Sentence("X");
		setQuery(x);
		boolean result = checkEntailmentByEnumeration();
		System.out.println("Is X good door : " + result);
		
		Sentence y = new Sentence("Y");
		setQuery(y);
		result = checkEntailmentByEnumeration();
		System.out.println("Is Y good door : " + result);
		
		Sentence z = new Sentence("Z");
		setQuery(z);
		result = checkEntailmentByEnumeration();
		System.out.println("Is Z good door : " + result);
		
		Sentence w = new Sentence("W");
		setQuery(w);
		result = checkEntailmentByEnumeration();
		System.out.println("Is W good door : " + result);
		System.out.println("\n");
		System.out.println("Number of recursive calls taken to generate result using Enumeration: " + PropositionValidator.getCountOfCalls());
		return System.currentTimeMillis() - startTime;
	}

	@Override
	public boolean checkEntailmentByInferenceRules(Sentence cnfClauses) {
		return !DPLL.isSatisfiable(cnfClauses);
	}

	@Override
	public long generateInferenceResults() {
		DPLL.setCountOfCalls(0);
		System.out.println("--------------------------------------");		
		System.out.println("Solving Problem using DPLL Inference Method");
		System.out.println("--------------------------------------");
		
		long startTime = System.currentTimeMillis();
		long timeTaken = 0;
		
		Sentence x = new Sentence("X");
		setQuery(x);
		List<Sentence> cnfClauseList = generateCNFClauses();
		Sentence input = new Sentence(Connective.AND, cnfClauseList.toArray(new Sentence[cnfClauseList.size()]));
		
		startTime = System.currentTimeMillis();;
		boolean result = checkEntailmentByInferenceRules(input);
		System.out.println("Is X good door : " + result);
		timeTaken += System.currentTimeMillis() - startTime;
		
		Sentence y = new Sentence("Y");
		setQuery(y);
		cnfClauseList = generateCNFClauses();
		input = new Sentence(Connective.AND, cnfClauseList.toArray(new Sentence[cnfClauseList.size()]));
		
		startTime = System.currentTimeMillis();;
		result = checkEntailmentByInferenceRules(input);
		System.out.println("Is Y good door : " + result);
		timeTaken += System.currentTimeMillis() - startTime;
		
		Sentence z = new Sentence("Z");
		setQuery(z);
		cnfClauseList = generateCNFClauses();
		input = new Sentence(Connective.AND, cnfClauseList.toArray(new Sentence[cnfClauseList.size()]));
		
		startTime = System.currentTimeMillis();;
		result = checkEntailmentByInferenceRules(input);
		System.out.println("Is Z good door : " + result);	
		timeTaken += System.currentTimeMillis() - startTime;
		
		Sentence w = new Sentence("W");
		setQuery(w);
		cnfClauseList = generateCNFClauses();
		input = new Sentence(Connective.AND, cnfClauseList.toArray(new Sentence[cnfClauseList.size()]));
		
		startTime = System.currentTimeMillis();;
		result = checkEntailmentByInferenceRules(input);
		System.out.println("Is W good door : " + result);		
		timeTaken += System.currentTimeMillis() - startTime;
		System.out.println("\n");
		System.out.println("Number of recursive calls taken to generate result using DPLL: " + DPLL.getCountOfCalls());
		return timeTaken;
	}
	
	public static void main(String[] args) {
		DoorsOfEnlightenmentSmullyan problem = new DoorsOfEnlightenmentSmullyan();
		problem.solveProblem();
	}

}
