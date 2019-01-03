package com.uofr.course.csc442.hw.hw2.reasoning.problems;

import com.uofr.course.csc442.hw.hw2.reasoning.model.ReasoningProblem;

/**
 * ENUM to capture all the different types of problems
 * solved by the reasoning model built 
 * @author tusharkumar
 *
 */
public enum ProblemTypes {
	
	MODUS_PONENS("1. Modus Ponens", 
			new ModusPonens()),
	WUMPUS_WORLD("2. Wumpus World (Simple)", 
			new SimpleWumpusWorld()),
	HORN_CLAUSES("3. Horn Clauses (Russell & Norvig)", 
			new HornClauses()),
	LIARS_AND_TRUTH_TELLERS_A("4a. Liars and Truth-tellers-a (OSSMB 82-12)", 
			new LiarsAndTruthTellersA()),
	LIARS_AND_TRUTH_TELLERS_B("4b. Liars and Truth-tellers-b (OSSMB 83-11)", 
			new LiarsAndTruthTellersB()),
	MORE_LIARS_AND_TRUTH_TELLERS("5. More Liars and Truth-tellers (adapted from JRM14 392)", 
			new MoreLiarsAndTruthTellers()),
	DOORS_OF_ENLIGHTENMENT_SMULLYAN_PROBLEM("6a. The Doors of Enlightenment (from CRUX 357) : Smullyan’s problem",
			new DoorsOfEnlightenmentSmullyan()),
	DOORS_OF_ENLIGHTENMENT_LIU_PROBLEM("6b. The Doors of Enlightenment (from CRUX 357) : Liu’s problem", 
			new DoorsOfEnlightenmentLiu());
	
	private String problemName;
	private ReasoningProblem problem;
	
	private ProblemTypes(String problemName, ReasoningProblem problem) {
		this.problemName = problemName;
		this.problem = problem;
	}

	public String getProblemName() {
		return problemName;
	}

	public ReasoningProblem getProblem() {
		return problem;
	}	
}
