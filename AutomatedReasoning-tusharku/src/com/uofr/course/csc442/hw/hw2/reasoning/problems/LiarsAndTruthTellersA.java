package com.uofr.course.csc442.hw.hw2.reasoning.problems;

import java.util.List;

import com.uofr.course.csc442.hw.hw2.reasoning.DPLL;
import com.uofr.course.csc442.hw.hw2.reasoning.PropositionValidator;
import com.uofr.course.csc442.hw.hw2.reasoning.model.AbstractReasoningProblem;
import com.uofr.course.csc442.hw.hw2.reasoning.model.Connective;
import com.uofr.course.csc442.hw.hw2.reasoning.model.Sentence;

/**
 * Class for capturing the liars and truth teller A
 * clause sample problem mentioned
 * in the project requirements.
 * @author tusharkumar
 *
 */
public class LiarsAndTruthTellersA extends AbstractReasoningProblem {

	@Override
	public Sentence generateKnowledgeBase() {
		Sentence amyTruthTeller = new Sentence("Amy");
		Sentence amyLiar = new Sentence(Connective.NOT, new Sentence[]{amyTruthTeller});
		Sentence calTruthTeller = new Sentence("Cal");
		Sentence calLiar = new Sentence(Connective.NOT, new Sentence[]{calTruthTeller});
		Sentence bobTruthTeller = new Sentence("Bob");
		
		Sentence calAndI = new Sentence(Connective.AND, new Sentence[]{calTruthTeller, amyTruthTeller});	
		Sentence amysaysCalAndI = new Sentence(Connective.IFF, new Sentence[]{amyTruthTeller, calAndI});
		
		Sentence bobSaysCalLiar = new Sentence(Connective.IFF, new Sentence[]{bobTruthTeller, calLiar});		
		
		Sentence bobTruthOrAmyLiar = new Sentence(Connective.OR, new Sentence[]{bobTruthTeller, amyLiar});		
		Sentence calSaysBobTruthOrAmyLiar = new Sentence(Connective.IFF, new Sentence[]{calTruthTeller, bobTruthOrAmyLiar});
		 
		Sentence knowledgeBase = new Sentence(Connective.AND, new Sentence[]{
				amysaysCalAndI, bobSaysCalLiar, 
				calSaysBobTruthOrAmyLiar});	
		System.out.println(knowledgeBase.getSentenceText());

		return knowledgeBase;
	}
	
	@Override
	public String getProblemName() {
		StringBuilder str = new StringBuilder();
		str.append("\n======================================================\n");
		str.append(ProblemTypes.LIARS_AND_TRUTH_TELLERS_A.getProblemName());
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
		long startTime = System.currentTimeMillis();
		
		PropositionValidator.setCountOfCalls(0);
		System.out.println("--------------------------------------");
		System.out.println("Solving Problem using Enumeration Method");
		System.out.println("--------------------------------------");
		
		Sentence amyTruthTeller = new Sentence("Amy");
		setQuery(amyTruthTeller);
		boolean result = checkEntailmentByEnumeration();
		System.out.println("Is Amy's truthfulness inferred : " + result);
		
		setQuery(new Sentence(Connective.NOT, new Sentence[] {amyTruthTeller}));
		result = checkEntailmentByEnumeration();
		System.out.println("Is Amy's dishonesty inferred : " + result);
		
		Sentence calTruthTeller = new Sentence("Cal");
		setQuery(calTruthTeller);
		result = checkEntailmentByEnumeration();
		System.out.println("Is Cal's truthfulness inferred : " + result);
		
		Sentence bobTruthTeller = new Sentence("Bob");
		setQuery(bobTruthTeller);
		result = checkEntailmentByEnumeration();
		System.out.println("Is Bob's truthfulness inferred : " + result);
		
		setQuery(new Sentence(Connective.NOT, new Sentence[] {bobTruthTeller}));
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
		
		long timeTaken = 0;
		long startTime = System.currentTimeMillis();
		
		Sentence amyTruthTeller = new Sentence("Amy");
		setQuery(amyTruthTeller);
		List<Sentence> cnfClauseList = generateCNFClauses();			
		Sentence input = new Sentence(Connective.AND, cnfClauseList.toArray(new Sentence[cnfClauseList.size()]));
		
		startTime = System.currentTimeMillis();
		boolean result = checkEntailmentByInferenceRules(input);
		System.out.println("Is Amy's truthfulness inferred : " + result);
		timeTaken += System.currentTimeMillis() - startTime;
		
		setQuery(new Sentence(Connective.NOT, new Sentence[] {amyTruthTeller}));
		cnfClauseList = generateCNFClauses();	
		input = new Sentence(Connective.AND, cnfClauseList.toArray(new Sentence[cnfClauseList.size()]));
		
		startTime = System.currentTimeMillis();
		result = checkEntailmentByInferenceRules(input);
		System.out.println("Is Amy's dishonesty inferred : " + result);
		timeTaken += System.currentTimeMillis() - startTime;
		
		Sentence calTruthTeller = new Sentence("Cal");
		setQuery(calTruthTeller);
		cnfClauseList = generateCNFClauses();	
		input = new Sentence(Connective.AND, cnfClauseList.toArray(new Sentence[cnfClauseList.size()]));
		
		startTime = System.currentTimeMillis();
		result = checkEntailmentByInferenceRules(input);
		System.out.println("Is Cal's truthfulness inferred : " + result);
		timeTaken += System.currentTimeMillis() - startTime;
		
		Sentence bobTruthTeller = new Sentence("Bob");
		setQuery(bobTruthTeller);
		cnfClauseList = generateCNFClauses();	
		input = new Sentence(Connective.AND, cnfClauseList.toArray(new Sentence[cnfClauseList.size()]));
		
		startTime = System.currentTimeMillis();
		result = checkEntailmentByInferenceRules(input);
		System.out.println("Is Bob's truthfulness inferred : " + result);
		timeTaken += System.currentTimeMillis() - startTime;
		
		setQuery(new Sentence(Connective.NOT, new Sentence[] {bobTruthTeller}));
		cnfClauseList = generateCNFClauses();	
		input = new Sentence(Connective.AND, cnfClauseList.toArray(new Sentence[cnfClauseList.size()]));
		
		startTime = System.currentTimeMillis();
		result = checkEntailmentByInferenceRules(input);
		timeTaken += System.currentTimeMillis() - startTime;
		
		System.out.println("Is Bob's dishonesty inferred : " + result);
		System.out.println("\n");	
		
		System.out.println("Number of recursive calls taken to generate result using DPLL: " + DPLL.getCountOfCalls());
		return timeTaken;
	}
	
	public static void main(String[] args) {
		LiarsAndTruthTellersA problem = new LiarsAndTruthTellersA();
		problem.solveProblem();
	}

}
