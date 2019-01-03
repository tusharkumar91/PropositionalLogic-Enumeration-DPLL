package com.uofr.course.csc442.hw.hw2.reasoning.problems;

import java.util.List;

import com.uofr.course.csc442.hw.hw2.reasoning.DPLL;
import com.uofr.course.csc442.hw.hw2.reasoning.PropositionValidator;
import com.uofr.course.csc442.hw.hw2.reasoning.model.AbstractReasoningProblem;
import com.uofr.course.csc442.hw.hw2.reasoning.model.Connective;
import com.uofr.course.csc442.hw.hw2.reasoning.model.Sentence;

/**
 * Class for capturing the more liars and truth teller
 * sample problem mentioned
 * in the project requirements.
 * @author tusharkumar
 *
 */
public class MoreLiarsAndTruthTellers extends AbstractReasoningProblem {

	@Override
	public Sentence generateKnowledgeBase() {
		Sentence amyTruthTeller = new Sentence("Amy");
		Sentence halTruthTeller = new Sentence("Hal");
		Sentence idaTruthTeller = new Sentence("Ida");
		Sentence bobTruthTeller = new Sentence("Bob");
		Sentence leeTruthTeller = new Sentence("Lee");
		Sentence calTruthTeller = new Sentence("Cal");
		Sentence gilTruthTeller = new Sentence("Gil");
		Sentence deeTruthTeller = new Sentence("Dee");
		Sentence eliTruthTeller = new Sentence("Eli");
		Sentence fayTruthTeller = new Sentence("Fay");
		Sentence jayTruthTeller = new Sentence("Jay");
		Sentence kayTruthTeller = new Sentence("Kay");

		
		Sentence halAndIda = new Sentence(Connective.AND, new Sentence[]{halTruthTeller, idaTruthTeller});	
		Sentence amysaysHalAndIda = new Sentence(Connective.IFF, new Sentence[]{amyTruthTeller, halAndIda});
		
		Sentence amyAndLee = new Sentence(Connective.AND, new Sentence[]{amyTruthTeller, leeTruthTeller});	
		Sentence bobSaysAmyAndLee = new Sentence(Connective.IFF, new Sentence[]{bobTruthTeller, amyAndLee});
		
		Sentence bobAndGil = new Sentence(Connective.AND, new Sentence[]{bobTruthTeller, gilTruthTeller});	
		Sentence calSaysBobAndGil = new Sentence(Connective.IFF, new Sentence[]{calTruthTeller, bobAndGil});
		
		Sentence eliAndLee = new Sentence(Connective.AND, new Sentence[]{eliTruthTeller, leeTruthTeller});	
		Sentence deeSaysEliAndLee = new Sentence(Connective.IFF, new Sentence[]{deeTruthTeller, eliAndLee});
		
		Sentence calAndHal = new Sentence(Connective.AND, new Sentence[]{calTruthTeller, halTruthTeller});	
		Sentence eliSaysCalAndHal = new Sentence(Connective.IFF, new Sentence[]{eliTruthTeller, calAndHal});
		
		Sentence deeAndIda = new Sentence(Connective.AND, new Sentence[]{deeTruthTeller, idaTruthTeller});	
		Sentence faySaysDeeAndIda = new Sentence(Connective.IFF, new Sentence[]{fayTruthTeller, deeAndIda});
		
		Sentence eliLiar = new Sentence(Connective.NOT, new Sentence[]{eliTruthTeller});
		Sentence jayLiar = new Sentence(Connective.NOT, new Sentence[]{jayTruthTeller});		
		Sentence eliAndJayLiar = new Sentence(Connective.AND, new Sentence[]{eliLiar, jayLiar});	
		Sentence gilSaysEliAndJayLiars = new Sentence(Connective.IFF, new Sentence[]{gilTruthTeller, eliAndJayLiar});
		
		Sentence fayLiar = new Sentence(Connective.NOT, new Sentence[]{fayTruthTeller});
		Sentence kayLiar = new Sentence(Connective.NOT, new Sentence[]{kayTruthTeller});		
		Sentence fayAndKayLiar = new Sentence(Connective.AND, new Sentence[]{fayLiar, kayLiar});	
		Sentence halSaysFayAndKayLiars = new Sentence(Connective.IFF, new Sentence[]{halTruthTeller, fayAndKayLiar});
		
		Sentence gilLiar = new Sentence(Connective.NOT, new Sentence[]{gilTruthTeller});
		Sentence gilAndKayLiar = new Sentence(Connective.AND, new Sentence[]{kayLiar, gilLiar});	
		Sentence idaSaysGilAndKayLiars = new Sentence(Connective.IFF, new Sentence[]{idaTruthTeller, gilAndKayLiar});
		
		Sentence amyLiar = new Sentence(Connective.NOT, new Sentence[]{amyTruthTeller});
		Sentence calLiar = new Sentence(Connective.NOT, new Sentence[]{calTruthTeller});		
		Sentence amyAndCalLiar = new Sentence(Connective.AND, new Sentence[]{amyLiar, calLiar});	
		Sentence jaySaysAmyAndCalLiars = new Sentence(Connective.IFF, new Sentence[]{jayTruthTeller, amyAndCalLiar});
		
		Sentence deeLiar = new Sentence(Connective.NOT, new Sentence[]{deeTruthTeller});
		Sentence deeAndFayLiar = new Sentence(Connective.AND, new Sentence[]{deeLiar, fayLiar});	
		Sentence kaySaysDeeAndFayLiars = new Sentence(Connective.IFF, new Sentence[]{kayTruthTeller, deeAndFayLiar});
		
		Sentence bobLiar = new Sentence(Connective.NOT, new Sentence[]{bobTruthTeller});
		Sentence bobAndJayLiar = new Sentence(Connective.AND, new Sentence[]{bobLiar, jayLiar});	
		Sentence leeSaysBobAndJayLiars = new Sentence(Connective.IFF, new Sentence[]{leeTruthTeller, bobAndJayLiar});
		 
		Sentence knowledgeBase = new Sentence(Connective.AND, new Sentence[]{
				amysaysHalAndIda, bobSaysAmyAndLee, calSaysBobAndGil, 
				deeSaysEliAndLee, eliSaysCalAndHal, faySaysDeeAndIda,
				gilSaysEliAndJayLiars, halSaysFayAndKayLiars, idaSaysGilAndKayLiars,
				jaySaysAmyAndCalLiars, kaySaysDeeAndFayLiars, leeSaysBobAndJayLiars
		});	
		return knowledgeBase;
	}
	
	@Override
	public String getProblemName() {
		StringBuilder str = new StringBuilder();
		str.append("\n======================================================\n");
		str.append(ProblemTypes.MORE_LIARS_AND_TRUTH_TELLERS.getProblemName());
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
		
		setQuery(new Sentence(Connective.NOT, new Sentence[]{amyTruthTeller}));
		result = checkEntailmentByEnumeration();
		System.out.println("Is Amy's dishonesty inferred : " + result);
		
		Sentence halTruthTeller = new Sentence("Hal");
		setQuery(halTruthTeller);
		result = checkEntailmentByEnumeration();
		System.out.println("Is Hal's truthfulness inferred : " + result);
		
		setQuery(new Sentence(Connective.NOT, new Sentence[]{halTruthTeller}));
		result = checkEntailmentByEnumeration();
		System.out.println("Is Hal's dishonesty inferred : " + result);
		
		Sentence idaTruthTeller = new Sentence("Ida");
		setQuery(idaTruthTeller);
		result = checkEntailmentByEnumeration();
		System.out.println("Is Ida's truthfulness inferred : " + result);
		
		setQuery(new Sentence(Connective.NOT, new Sentence[]{idaTruthTeller}));
		result = checkEntailmentByEnumeration();
		System.out.println("Is Ida's dishonesty inferred : " + result);
		
		Sentence bobTruthTeller = new Sentence("Bob");
		setQuery(bobTruthTeller);
		result = checkEntailmentByEnumeration();
		System.out.println("Is Bob's truthfulness inferred : " + result);
		
		setQuery(new Sentence(Connective.NOT, new Sentence[]{bobTruthTeller}));
		result = checkEntailmentByEnumeration();
		System.out.println("Is Bob's dishonesty inferred : " + result);
		
		Sentence leeTruthTeller = new Sentence("Lee");
		setQuery(leeTruthTeller);
		result = checkEntailmentByEnumeration();
		System.out.println("Is Lee's truthfulness inferred : " + result);
		
		setQuery(new Sentence(Connective.NOT, new Sentence[]{leeTruthTeller}));
		result = checkEntailmentByEnumeration();
		System.out.println("Is Lee's dishonesty inferred : " + result);
		
		Sentence calTruthTeller = new Sentence("Cal");
		setQuery(calTruthTeller);
		result = checkEntailmentByEnumeration();
		System.out.println("Is Cal's truthfulness inferred : " + result);
		
		setQuery(new Sentence(Connective.NOT, new Sentence[]{calTruthTeller}));
		result = checkEntailmentByEnumeration();
		System.out.println("Is Cal's dishonesty inferred : " + result);
		
		Sentence gilTruthTeller = new Sentence("Gil");
		setQuery(gilTruthTeller);
		result = checkEntailmentByEnumeration();
		System.out.println("Is Gil's truthfulness inferred : " + result);
		
		setQuery(new Sentence(Connective.NOT, new Sentence[]{gilTruthTeller}));
		result = checkEntailmentByEnumeration();
		System.out.println("Is Gil's dishonesty inferred : " + result);
		
		Sentence deeTruthTeller = new Sentence("Dee");
		setQuery(deeTruthTeller);
		result = checkEntailmentByEnumeration();
		System.out.println("Is Dee's truthfulness inferred : " + result);
		
		setQuery(new Sentence(Connective.NOT, new Sentence[]{deeTruthTeller}));
		result = checkEntailmentByEnumeration();
		System.out.println("Is Dee's dishonesty inferred : " + result);
				
		Sentence eliTruthTeller = new Sentence("Eli");
		setQuery(eliTruthTeller);
		result = checkEntailmentByEnumeration();
		System.out.println("Is Eli's truthfulness inferred : " + result);
		
		setQuery(new Sentence(Connective.NOT, new Sentence[]{eliTruthTeller}));
		result = checkEntailmentByEnumeration();
		System.out.println("Is Eli's dishonesty inferred : " + result);

		Sentence fayTruthTeller = new Sentence("Fay");
		setQuery(fayTruthTeller);
		result = checkEntailmentByEnumeration();
		System.out.println("Is Fay's truthfulness inferred : " + result);
		
		setQuery(new Sentence(Connective.NOT, new Sentence[]{fayTruthTeller}));
		result = checkEntailmentByEnumeration();
		System.out.println("Is Fay's dishonesty inferred : " + result);
		
		Sentence jayTruthTeller = new Sentence("Jay");
		setQuery(jayTruthTeller);
		result = checkEntailmentByEnumeration();
		System.out.println("Is Jay's truthfulness inferred : " + result);
		
		Sentence kayTruthTeller = new Sentence("Kay");
		setQuery(kayTruthTeller);
		result = checkEntailmentByEnumeration();
		System.out.println("Is Kay's truthfulness inferred : " + result);
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
		
		Sentence amyTruthTeller = new Sentence("Amy");
		setQuery(amyTruthTeller);
		List<Sentence> cnfClauseList = generateCNFClauses();
		Sentence input = new Sentence(Connective.AND, cnfClauseList.toArray(new Sentence[cnfClauseList.size()]));
		
		startTime = System.currentTimeMillis();;
		boolean result = checkEntailmentByInferenceRules(input);
		System.out.println("Is Amy's truthfulness inferred : " + result);	
		timeTaken += System.currentTimeMillis() - startTime;
		
		setQuery(new Sentence(Connective.NOT, new Sentence[]{amyTruthTeller}));
		cnfClauseList = generateCNFClauses();
		input = new Sentence(Connective.AND, cnfClauseList.toArray(new Sentence[cnfClauseList.size()]));
		
		startTime = System.currentTimeMillis();;
		result = checkEntailmentByInferenceRules(input);
		System.out.println("Is Amy's dishonesty inferred : " + result);
		timeTaken += System.currentTimeMillis() - startTime;
		
		Sentence halTruthTeller = new Sentence("Hal");
		setQuery(halTruthTeller);
		cnfClauseList = generateCNFClauses();
		input = new Sentence(Connective.AND, cnfClauseList.toArray(new Sentence[cnfClauseList.size()]));
		
		startTime = System.currentTimeMillis();;
		result = checkEntailmentByInferenceRules(input);
		System.out.println("Is Hal's truthfulness inferred : " + result);
		timeTaken += System.currentTimeMillis() - startTime;
		
		setQuery(new Sentence(Connective.NOT, new Sentence[]{halTruthTeller}));
		cnfClauseList = generateCNFClauses();
		input = new Sentence(Connective.AND, cnfClauseList.toArray(new Sentence[cnfClauseList.size()]));
		
		startTime = System.currentTimeMillis();;
		result = checkEntailmentByInferenceRules(input);
		System.out.println("Is Hal's dishonesty inferred : " + result);
		timeTaken += System.currentTimeMillis() - startTime;
		
		Sentence idaTruthTeller = new Sentence("Ida");
		setQuery(idaTruthTeller);
		cnfClauseList = generateCNFClauses();
		input = new Sentence(Connective.AND, cnfClauseList.toArray(new Sentence[cnfClauseList.size()]));
		
		startTime = System.currentTimeMillis();;
		result = checkEntailmentByInferenceRules(input);
		System.out.println("Is Ida's truthfulness inferred : " + result);
		timeTaken += System.currentTimeMillis() - startTime;
		
		setQuery(new Sentence(Connective.NOT, new Sentence[]{idaTruthTeller}));
		cnfClauseList = generateCNFClauses();
		input = new Sentence(Connective.AND, cnfClauseList.toArray(new Sentence[cnfClauseList.size()]));
		
		startTime = System.currentTimeMillis();;
		result = checkEntailmentByInferenceRules(input);
		System.out.println("Is Ida's dishonesty inferred : " + result);
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
		
		Sentence leeTruthTeller = new Sentence("Lee");
		setQuery(leeTruthTeller);
		cnfClauseList = generateCNFClauses();
		input = new Sentence(Connective.AND, cnfClauseList.toArray(new Sentence[cnfClauseList.size()]));
		
		startTime = System.currentTimeMillis();;
		result = checkEntailmentByInferenceRules(input);
		System.out.println("Is Lee's truthfulness inferred : " + result);
		timeTaken += System.currentTimeMillis() - startTime;
		
		setQuery(new Sentence(Connective.NOT, new Sentence[]{leeTruthTeller}));
		cnfClauseList = generateCNFClauses();
		input = new Sentence(Connective.AND, cnfClauseList.toArray(new Sentence[cnfClauseList.size()]));
		
		startTime = System.currentTimeMillis();;
		result = checkEntailmentByInferenceRules(input);
		System.out.println("Is Lee's dishonesty inferred : " + result);
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
		
		Sentence gilTruthTeller = new Sentence("Gil");
		setQuery(gilTruthTeller);
		cnfClauseList = generateCNFClauses();
		input = new Sentence(Connective.AND, cnfClauseList.toArray(new Sentence[cnfClauseList.size()]));
		
		startTime = System.currentTimeMillis();;
		result = checkEntailmentByInferenceRules(input);
		System.out.println("Is Gil's truthfulness inferred : " + result);
		timeTaken += System.currentTimeMillis() - startTime;
		
		setQuery(new Sentence(Connective.NOT, new Sentence[]{gilTruthTeller}));
		cnfClauseList = generateCNFClauses();
		input = new Sentence(Connective.AND, cnfClauseList.toArray(new Sentence[cnfClauseList.size()]));
		
		startTime = System.currentTimeMillis();;
		result = checkEntailmentByInferenceRules(input);
		System.out.println("Is Gil's dishonesty inferred : " + result);
		timeTaken += System.currentTimeMillis() - startTime;
		
		Sentence deeTruthTeller = new Sentence("Dee");
		setQuery(deeTruthTeller);
		cnfClauseList = generateCNFClauses();
		input = new Sentence(Connective.AND, cnfClauseList.toArray(new Sentence[cnfClauseList.size()]));
		
		startTime = System.currentTimeMillis();;
		result = checkEntailmentByInferenceRules(input);
		System.out.println("Is Dee's truthfulness inferred : " + result);
		timeTaken += System.currentTimeMillis() - startTime;
		
		setQuery(new Sentence(Connective.NOT, new Sentence[]{deeTruthTeller}));
		cnfClauseList = generateCNFClauses();
		input = new Sentence(Connective.AND, cnfClauseList.toArray(new Sentence[cnfClauseList.size()]));
		
		startTime = System.currentTimeMillis();;
		result = checkEntailmentByInferenceRules(input);
		System.out.println("Is Dee's dishonesty inferred : " + result);
		timeTaken += System.currentTimeMillis() - startTime;
				
		Sentence eliTruthTeller = new Sentence("Eli");
		setQuery(eliTruthTeller);
		cnfClauseList = generateCNFClauses();
		input = new Sentence(Connective.AND, cnfClauseList.toArray(new Sentence[cnfClauseList.size()]));
		
		startTime = System.currentTimeMillis();;
		result = checkEntailmentByInferenceRules(input);
		System.out.println("Is Eli's truthfulness inferred : " + result);
		timeTaken += System.currentTimeMillis() - startTime;
		
		setQuery(new Sentence(Connective.NOT, new Sentence[]{eliTruthTeller}));
		cnfClauseList = generateCNFClauses();
		input = new Sentence(Connective.AND, cnfClauseList.toArray(new Sentence[cnfClauseList.size()]));
		
		startTime = System.currentTimeMillis();;
		result = checkEntailmentByInferenceRules(input);
		System.out.println("Is Eli's dishonesty inferred : " + result);
		timeTaken += System.currentTimeMillis() - startTime;

		Sentence fayTruthTeller = new Sentence("Fay");
		setQuery(fayTruthTeller);
		cnfClauseList = generateCNFClauses();
		input = new Sentence(Connective.AND, cnfClauseList.toArray(new Sentence[cnfClauseList.size()]));
		
		startTime = System.currentTimeMillis();;
		result = checkEntailmentByInferenceRules(input);
		System.out.println("Is Fay's truthfulness inferred : " + result);
		timeTaken += System.currentTimeMillis() - startTime;
		
		setQuery(new Sentence(Connective.NOT, new Sentence[]{fayTruthTeller}));
		cnfClauseList = generateCNFClauses();
		input = new Sentence(Connective.AND, cnfClauseList.toArray(new Sentence[cnfClauseList.size()]));
		
		startTime = System.currentTimeMillis();;
		result = checkEntailmentByInferenceRules(input);
		System.out.println("Is Fay's dishonesty inferred : " + result);
		timeTaken += System.currentTimeMillis() - startTime;
		
		Sentence jayTruthTeller = new Sentence("Jay");
		setQuery(jayTruthTeller);
		cnfClauseList = generateCNFClauses();
		input = new Sentence(Connective.AND, cnfClauseList.toArray(new Sentence[cnfClauseList.size()]));
		
		startTime = System.currentTimeMillis();;
		result = checkEntailmentByInferenceRules(input);
		System.out.println("Is Jay's truthfulness inferred : " + result);
		timeTaken += System.currentTimeMillis() - startTime;
		
		Sentence kayTruthTeller = new Sentence("Kay");
		setQuery(kayTruthTeller);
		cnfClauseList = generateCNFClauses();
		input = new Sentence(Connective.AND, cnfClauseList.toArray(new Sentence[cnfClauseList.size()]));
		
		startTime = System.currentTimeMillis();;
		result = checkEntailmentByInferenceRules(input);
		System.out.println("Is Kay's truthfulness inferred : " + result);
		timeTaken += System.currentTimeMillis() - startTime;
		System.out.println("\n");
		System.out.println("Number of recursive calls taken to generate result using DPLL: " + DPLL.getCountOfCalls());
		return timeTaken;
	}
	
	public static void main(String[] args) {
		MoreLiarsAndTruthTellers problem = new MoreLiarsAndTruthTellers();
		problem.solveProblem();
	}

}
