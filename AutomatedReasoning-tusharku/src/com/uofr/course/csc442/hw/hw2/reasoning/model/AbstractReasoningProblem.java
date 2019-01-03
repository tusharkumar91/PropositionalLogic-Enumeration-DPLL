package com.uofr.course.csc442.hw.hw2.reasoning.model;

import java.util.ArrayList;
import java.util.List;

import com.uofr.course.csc442.hw.hw2.reasoning.CNFConverter;
import com.uofr.course.csc442.hw.hw2.reasoning.PropositionValidator;

/**
 * Abstract implementation of the reasoning problem interface
 * used to provide a common template for 
 * solving all kinds of reasoning problem
 * @author tusharkumar
 *
 */
public abstract class AbstractReasoningProblem implements ReasoningProblem {
	private Sentence query;
	private Sentence knowledgeBase;
	
	public Sentence getQuery() {
		return query;
	}

	public void setQuery(Sentence query) {
		this.query = query;
	}

	public Sentence getKnowledgeBase() {
		return knowledgeBase;
	}

	public void setKnowledgeBase(Sentence knowledgeBase) {
		this.knowledgeBase = knowledgeBase;
	}

	protected final List<Sentence> generateCNFClauses() {
		Sentence kb = getKnowledgeBase();
		Sentence query = getQuery();
		Sentence kbCnf = CNFConverter.convertToCNF(kb);
		Sentence notQuery = new Sentence(Connective.NOT, new Sentence[]{query});
		Sentence notQueryCnf = CNFConverter.convertToCNF(notQuery);
		List<Sentence> clauseList = new ArrayList<Sentence>();
		if(Connective.AND.equals(kbCnf.getConnective())) {				
			for(Sentence sentence : kbCnf.getConnectedChildred()) {
				clauseList.add(sentence);
			}
			if(Connective.AND.equals(notQueryCnf.getConnective())) {	
				for(Sentence sentence : notQueryCnf.getConnectedChildred()) {
					clauseList.add(sentence);
				}
			}
			else {
				clauseList.add(notQueryCnf);
			}
		}
		else {
			clauseList.add(kbCnf);
			if(Connective.AND.equals(notQueryCnf.getConnective())) {	
				for(Sentence sentence : notQueryCnf.getConnectedChildred()) {
					clauseList.add(sentence);
				}
			}
			else {
				clauseList.add(notQueryCnf);
			}
		}		
		return clauseList;
	}
	
	/**
	 * Abstract method to solve any reasoning problem using both enumeration
	 * and inference methods
	 */
	@Override
	public final void solveProblem() {
		System.out.println(getProblemName());
		setKnowledgeBase(generateKnowledgeBase());
		long timeTakenForEnumeration = generateEnumerationResults();
		System.out.println("Time taken for solving using Basic Model Checking (in ms): " + timeTakenForEnumeration);
		System.out.println("\n");
		long timeTakenForAdvancedInference = generateInferenceResults();		
		System.out.println("Time taken for solving using Advanced Propositional Inference (in ms): " + timeTakenForAdvancedInference);
		System.out.println("\n");
	};
}
