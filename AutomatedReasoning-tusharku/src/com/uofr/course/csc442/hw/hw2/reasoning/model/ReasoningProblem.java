package com.uofr.course.csc442.hw.hw2.reasoning.model;

/**
 * Interface to capture all the behavior common
 * to all the sample problems presented
 * as requirement for project.
 * @author tusharkumar
 *
 */
public interface ReasoningProblem {
	/**
	 * Method to generate the description of a problem
	 * @return
	 */
	public String getProblemName();
	
	/**
	 * Method to generate the knowledge base for a given problem
	 * @return
	 */
	public Sentence generateKnowledgeBase();
	
	/**
	 * Method to generate the query  for a given problem
	 * @return
	 */
	public void setQuery(Sentence query);
	
	/**
	 * Method to checkEntailment by truth table
	 * enumeration method for the given problem
	 * @return
	 */
	public boolean checkEntailmentByEnumeration();

	public long generateEnumerationResults();
	
	/**
	 * Method to checkEntailment using inference rules
	 * rather than enumerating all possible worlds
	 * for the given problem
	 * @return
	 */
	public boolean checkEntailmentByInferenceRules(Sentence input);
	
	public long generateInferenceResults();

	/**
	 * Method to solve the given problem using all
	 * the available techniques
	 */
	public void solveProblem();
}
