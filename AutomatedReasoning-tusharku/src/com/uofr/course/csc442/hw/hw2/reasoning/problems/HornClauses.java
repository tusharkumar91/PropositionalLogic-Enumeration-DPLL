package com.uofr.course.csc442.hw.hw2.reasoning.problems;

import java.util.List;

import com.uofr.course.csc442.hw.hw2.reasoning.DPLL;
import com.uofr.course.csc442.hw.hw2.reasoning.PropositionValidator;
import com.uofr.course.csc442.hw.hw2.reasoning.model.AbstractReasoningProblem;
import com.uofr.course.csc442.hw.hw2.reasoning.model.Connective;
import com.uofr.course.csc442.hw.hw2.reasoning.model.Sentence;

/**
 * Class for capturing the horn clause sample problem mentioned
 * in the project requirements.
 * @author tusharkumar
 *
 */
public class HornClauses extends AbstractReasoningProblem {

	public Sentence generateKnowledgeBase() {
		Sentence mortal = new Sentence("U_Mortal");
		Sentence magical = new Sentence("U_Magical");
		Sentence mythical = new Sentence("U_Mythical");
		
		Sentence notMortal = new Sentence(Connective.NOT, new Sentence[]{mortal});
		Sentence mythImpNotMortal = new Sentence(Connective.IMPLIES, new Sentence[]{mythical, notMortal});
		
		Sentence notMythical = new Sentence(Connective.NOT, new Sentence[]{mythical});
		Sentence mammal = new Sentence("U_Mammal");
		Sentence mortalAndMammal = new Sentence(Connective.AND, new Sentence[]{mortal, mammal});
		Sentence notMythImpMortalMammal = new Sentence(Connective.IMPLIES, new Sentence[]{notMythical, mortalAndMammal});
		
		Sentence immortalOrMammal = new Sentence(Connective.OR, new Sentence[]{notMortal, mammal});
		Sentence horned = new Sentence("U_Horned");
		Sentence notMortalOrMammalImpHorned = new Sentence(Connective.IMPLIES, new Sentence[]{immortalOrMammal, horned});
		
		Sentence hornedImpMagical = new Sentence(Connective.IMPLIES, new Sentence[]{horned, magical});
		
		Sentence knowledgeBase = new Sentence(Connective.AND, new Sentence[]{
				mythImpNotMortal, notMythImpMortalMammal, notMortalOrMammalImpHorned, hornedImpMagical});
		return knowledgeBase;		
	}	

	@Override
	public String getProblemName() {
		StringBuilder str = new StringBuilder();
		str.append("\n======================================================\n");
		str.append(ProblemTypes.HORN_CLAUSES.getProblemName());
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
		
		Sentence mythical = new Sentence("U_Mythical");
		setQuery(new Sentence(Connective.NOT, new Sentence[]{mythical}));
		boolean result = checkEntailmentByEnumeration();
		System.out.println("Is Unicorn not mythical inferred : " + result);	
		
		setQuery(mythical);
		result = checkEntailmentByEnumeration();
		System.out.println("Is Unicorn mythical inferred : " + result);	
		
		Sentence magical = new Sentence("U_Magical");
		setQuery(magical);
		result = checkEntailmentByEnumeration();
		System.out.println("Is Unicorn magical inferred : " + result);	
		
		Sentence horned = new Sentence("U_Horned");
		setQuery(horned);
		result = checkEntailmentByEnumeration();
		System.out.println("Is Unicorn horned inferred : " + result);	
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
		
		Sentence mythical = new Sentence("U_Mythical");
		setQuery(new Sentence(Connective.NOT, new Sentence[]{mythical}));
		List<Sentence> cnfClauseList = generateCNFClauses();	
		Sentence input = new Sentence(Connective.AND, cnfClauseList.toArray(new Sentence[cnfClauseList.size()]));
		
		startTime = System.currentTimeMillis();
		boolean result = checkEntailmentByInferenceRules(input);
		System.out.println("Is Unicorn not mythical inferred : " + result);	
		timeTaken += System.currentTimeMillis() - startTime;
		
		setQuery(mythical);
		cnfClauseList = generateCNFClauses();	
		input = new Sentence(Connective.AND, cnfClauseList.toArray(new Sentence[cnfClauseList.size()]));
		
		startTime = System.currentTimeMillis();
		result = checkEntailmentByInferenceRules(input);
		System.out.println("Is Unicorn mythical inferred : " + result);	
		timeTaken += System.currentTimeMillis() - startTime;
		
		Sentence magical = new Sentence("U_Magical");
		setQuery(magical);
		cnfClauseList = generateCNFClauses();	
		input = new Sentence(Connective.AND, cnfClauseList.toArray(new Sentence[cnfClauseList.size()]));
		
		startTime = System.currentTimeMillis();
		result = checkEntailmentByInferenceRules(input);
		System.out.println("Is Unicorn magical inferred : " + result);	
		timeTaken += System.currentTimeMillis() - startTime;
		
		Sentence horned = new Sentence("U_Horned");
		setQuery(horned);
		cnfClauseList = generateCNFClauses();	
		input = new Sentence(Connective.AND, cnfClauseList.toArray(new Sentence[cnfClauseList.size()]));
		
		startTime = System.currentTimeMillis();
		result = checkEntailmentByInferenceRules(input);
		System.out.println("Is Unicorn horned inferred : " + result);	
		timeTaken += System.currentTimeMillis() - startTime;
		System.out.println("\n");
		System.out.println("Number of recursive calls taken to generate result using DPLL: " + DPLL.getCountOfCalls());
		
		return timeTaken;
	}
	
	public static void main(String[] args) {
		HornClauses problem = new HornClauses();
		problem.solveProblem();
	}
}