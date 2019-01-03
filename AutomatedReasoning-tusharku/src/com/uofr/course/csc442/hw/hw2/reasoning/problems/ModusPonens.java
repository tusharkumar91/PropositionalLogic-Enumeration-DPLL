package com.uofr.course.csc442.hw.hw2.reasoning.problems;

import java.util.List;

import com.uofr.course.csc442.hw.hw2.reasoning.DPLL;
import com.uofr.course.csc442.hw.hw2.reasoning.PropositionValidator;
import com.uofr.course.csc442.hw.hw2.reasoning.model.AbstractReasoningProblem;
import com.uofr.course.csc442.hw.hw2.reasoning.model.Connective;
import com.uofr.course.csc442.hw.hw2.reasoning.model.Sentence;

/**
 * Class for capturing the Modus Ponens problem mentioned
 * in the project requirements.
 * @author tusharkumar
 *
 */
public class ModusPonens extends AbstractReasoningProblem{

	@Override
	public Sentence generateKnowledgeBase() {
		Sentence symbolQ = new Sentence("Q");
		Sentence symbolP = new Sentence("P");
		Sentence PimpQ = new Sentence(Connective.IMPLIES, new Sentence[]{symbolP, symbolQ});
		Sentence PAndPimpQ = new Sentence(Connective.AND, new Sentence[]{symbolP, PimpQ});	
		return PAndPimpQ;
	}
	
	@Override
	public String getProblemName() {
		StringBuilder str = new StringBuilder();
		str.append("\n======================================================\n");
		str.append(ProblemTypes.MODUS_PONENS.getProblemName());
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
		
		Sentence q = new Sentence("Q");
		setQuery(q);
		boolean result = checkEntailmentByEnumeration();
		System.out.println("Does {P, P => Q} |= Q : " + result);	
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
		
		Sentence q = new Sentence("Q");
		setQuery(q);
		List<Sentence> cnfClauseList = generateCNFClauses();	
		Sentence input = new Sentence(Connective.AND, cnfClauseList.toArray(new Sentence[cnfClauseList.size()]));
		
		boolean result = checkEntailmentByInferenceRules(input);
		System.out.println("Does {P, P => Q} |= Q : " + result);	
		timeTaken += System.currentTimeMillis() - startTime;
		
		System.out.println("\n");	
		System.out.println("Number of recursive calls taken to generate result using DPLL: " + DPLL.getCountOfCalls());
		return timeTaken;
	}
	
	public static void main(String[] args) {
		ModusPonens problem = new ModusPonens();
		problem.solveProblem();
	}
}
