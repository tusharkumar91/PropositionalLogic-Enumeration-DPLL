package com.uofr.course.csc442.hw.hw2.reasoning.problems;

import java.util.List;

import com.uofr.course.csc442.hw.hw2.reasoning.DPLL;
import com.uofr.course.csc442.hw.hw2.reasoning.PropositionValidator;
import com.uofr.course.csc442.hw.hw2.reasoning.model.AbstractReasoningProblem;
import com.uofr.course.csc442.hw.hw2.reasoning.model.Connective;
import com.uofr.course.csc442.hw.hw2.reasoning.model.Sentence;

/**
 * Class for capturing the Simple Wumpus World
 * sample problem mentioned in the project requirements.
 * @author tusharkumar
 *
 */
public class SimpleWumpusWorld extends AbstractReasoningProblem{

	@Override
	public Sentence generateKnowledgeBase() {
		Sentence notP11 = new Sentence(Connective.NOT, new Sentence[]{new Sentence("P11")});
		Sentence P12orP21 = new Sentence(Connective.OR, new Sentence[]{new Sentence("P12"), new Sentence("P21")});
		Sentence B11ImpP12orP21 = new Sentence(Connective.IFF, new Sentence[]{new Sentence("B11"), P12orP21});
		Sentence P11orP22orP31 = new Sentence(Connective.OR, 
				new Sentence[]{new Sentence("P11"), new Sentence("P22"), new Sentence("P31")});
		
		Sentence B21ImpP11orP22orP31 = new Sentence(Connective.IFF, new Sentence[]{new Sentence("B21"), P11orP22orP31});
		
		Sentence notB11 = new Sentence(Connective.NOT, new Sentence[]{new Sentence("B11")});
		Sentence B21 = new Sentence("B21");
		
		Sentence knowledgeBase = new Sentence(Connective.AND, new Sentence[]{
				notP11, B11ImpP12orP21, B21ImpP11orP22orP31, notB11, B21});			
		return knowledgeBase;
	}

	@Override
	public String getProblemName() {
		StringBuilder str = new StringBuilder();
		str.append("\n======================================================\n");
		str.append(ProblemTypes.WUMPUS_WORLD.getProblemName());
		str.append("\n======================================================\n");
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
		
		Sentence p12 = new Sentence("P12");
		
		setQuery(new Sentence(Connective.NOT, new Sentence[]{p12}));
		boolean result = checkEntailmentByEnumeration();
		System.out.println("Is there no Pit at [1,2] : " + result);	
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
		
		Sentence p12 = new Sentence("P12");
		setQuery(new Sentence(Connective.NOT, new Sentence[]{p12}));
		List<Sentence> cnfClauseList = generateCNFClauses();	
		Sentence input = new Sentence(Connective.AND, cnfClauseList.toArray(new Sentence[cnfClauseList.size()]));
		
		
		boolean result = checkEntailmentByInferenceRules(input);
		timeTaken += System.currentTimeMillis() - startTime;
		System.out.println("Is there no Pit at [1,2] : " + result);	
		System.out.println("\n");	
		System.out.println("Number of recursive calls taken to generate result using DPLL: " + DPLL.getCountOfCalls());
		return timeTaken;
	}
	
	public static void main(String[] args) {
		SimpleWumpusWorld ww = new SimpleWumpusWorld();
		ww.solveProblem();
	}
}