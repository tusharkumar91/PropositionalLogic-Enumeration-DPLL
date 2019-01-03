package com.uofr.course.csc442.hw.hw2.reasoning.problems;

import java.util.List;

import com.uofr.course.csc442.hw.hw2.reasoning.DPLL;
import com.uofr.course.csc442.hw.hw2.reasoning.PropositionValidator;
import com.uofr.course.csc442.hw.hw2.reasoning.model.AbstractReasoningProblem;
import com.uofr.course.csc442.hw.hw2.reasoning.model.Connective;
import com.uofr.course.csc442.hw.hw2.reasoning.model.Sentence;

/**
 * Class for capturing the liars and truth teller B
 * clause sample problem mentioned
 * in the project requirements.
 * @author tusharkumar
 *
 */
public class LiarsAndTruthTellersB extends AbstractReasoningProblem {

	@Override
	public Sentence generateKnowledgeBase() {
		Sentence amyTruthTeller = new Sentence("Amy");
		Sentence calTruthTeller = new Sentence("Cal");
		Sentence calLiar = new Sentence(Connective.NOT, new Sentence[]{calTruthTeller});
		Sentence bobTruthTeller = new Sentence("Bob");			
		Sentence amysaysCalLiar = new Sentence(Connective.IFF, new Sentence[]{amyTruthTeller, calLiar});				
		Sentence amyAndCal = new Sentence(Connective.AND, new Sentence[]{amyTruthTeller, calTruthTeller});
		Sentence bobSaysAmyAndCal = new Sentence(Connective.IFF, new Sentence[]{bobTruthTeller, amyAndCal});		
		Sentence calSaysBobTruth = new Sentence(Connective.IFF, new Sentence[]{calTruthTeller, bobTruthTeller});
		 
		Sentence knowledgeBase = new Sentence(Connective.AND, new Sentence[]{
				amysaysCalLiar, bobSaysAmyAndCal, 
				calSaysBobTruth});	
		return knowledgeBase;
	}
	
	@Override
	public String getProblemName() {
		StringBuilder str = new StringBuilder();
		str.append("\n======================================================\n");
		str.append(ProblemTypes.LIARS_AND_TRUTH_TELLERS_B.getProblemName());
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
		
		Sentence amyTruthTeller = new Sentence("Amy");
		setQuery(amyTruthTeller);
		boolean result = checkEntailmentByEnumeration();
		System.out.println("Is Amy's truthfulness inferred : " + result);
		
		Sentence calTruthTeller = new Sentence("Cal");
		setQuery(calTruthTeller);
		result = checkEntailmentByEnumeration();
		System.out.println("Is Cal's truthfulness inferred : " + result);
		
		setQuery(new Sentence(Connective.NOT, new Sentence[]{calTruthTeller}));
		result = checkEntailmentByEnumeration();
		System.out.println("Is Cal's dishonesty inferred : " + result);
		
		Sentence bobTruthTeller = new Sentence("Bob");
		setQuery(bobTruthTeller);
		result = checkEntailmentByEnumeration();
		System.out.println("Is Bob's truthfulness inferred : " + result);
		
		setQuery(new Sentence(Connective.NOT, new Sentence[]{bobTruthTeller}));
		result = checkEntailmentByEnumeration();
		System.out.println("Is Bob's dishonesty inferred : " + result);
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
		
		long startTime = System.currentTimeMillis();;
		long timeTaken = 0;
		
		Sentence amyTruthTeller = new Sentence("Amy");
		setQuery(amyTruthTeller);
		List<Sentence> cnfClauseList = generateCNFClauses();	
		Sentence input = new Sentence(Connective.AND, cnfClauseList.toArray(new Sentence[cnfClauseList.size()]));
		
		startTime = System.currentTimeMillis();;
		boolean result = checkEntailmentByInferenceRules(input);
		System.out.println("Is Amy's truthfulness inferred : " + result);
		timeTaken += System.currentTimeMillis() - startTime;
		
		Sentence calTruthTeller = new Sentence("Cal");
		setQuery(calTruthTeller);
		cnfClauseList = generateCNFClauses();	
		input = new Sentence(Connective.AND, cnfClauseList.toArray(new Sentence[cnfClauseList.size()]));
		
		startTime = System.currentTimeMillis();;
		result = checkEntailmentByInferenceRules(input);
		System.out.println("Is Cal's truthfulness inferred : " + result);
		timeTaken += System.currentTimeMillis() - startTime;
		
		setQuery(new Sentence(Connective.NOT, new Sentence[]{calTruthTeller}));
		cnfClauseList = generateCNFClauses();	
		input = new Sentence(Connective.AND, cnfClauseList.toArray(new Sentence[cnfClauseList.size()]));
		
		startTime = System.currentTimeMillis();;
		result = checkEntailmentByInferenceRules(input);
		System.out.println("Is Cal's dishonesty inferred : " + result);
		timeTaken += System.currentTimeMillis() - startTime;
		
		Sentence bobTruthTeller = new Sentence("Bob");
		setQuery(bobTruthTeller);
		cnfClauseList = generateCNFClauses();	
		input = new Sentence(Connective.AND, cnfClauseList.toArray(new Sentence[cnfClauseList.size()]));
		
		startTime = System.currentTimeMillis();;
		result = checkEntailmentByInferenceRules(input);
		System.out.println("Is Bob's truthfulness inferred : " + result);
		timeTaken += System.currentTimeMillis() - startTime;
		
		setQuery(new Sentence(Connective.NOT, new Sentence[]{bobTruthTeller}));
		cnfClauseList = generateCNFClauses();	
		input = new Sentence(Connective.AND, cnfClauseList.toArray(new Sentence[cnfClauseList.size()]));
		
		startTime = System.currentTimeMillis();;
		result = checkEntailmentByInferenceRules(input);
		System.out.println("Is Bob's dishonesty inferred : " + result);
		timeTaken += System.currentTimeMillis() - startTime;
		System.out.println("\n");	
		System.out.println("Number of recursive calls taken to generate result using DPLL: " + DPLL.getCountOfCalls());
		/*for(Sentence clause : cnfClauseList) {
			System.out.println(clause);
		}*/
		
		return timeTaken;
	}
	
	public static void main(String[] args) {
		LiarsAndTruthTellersB problem = new LiarsAndTruthTellersB();
		problem.solveProblem();
	}
}
