package com.uofr.course.csc442.hw.hw2.reasoning;

import com.uofr.course.csc442.hw.hw2.reasoning.model.ReasoningProblem;
import com.uofr.course.csc442.hw.hw2.reasoning.problems.ProblemTypes;

/**
 * Main class to handle the workflow and
 * solve all the reasoning problems
 * and generate their appropriate outputs to the 
 * console. 
 * @author tusharkumar
 *
 */
public class ReasoningProblemSolver {
	public static void main(String[] args) {
		if(args.length > 0) {
			if(args[0].charAt(0) != '-') {
				throw new IllegalArgumentException("argument name not prefixed with -");
			}
			if(args[0].length() <= 1) {
				throw new IllegalArgumentException("argument name must be of more than 1 character");
			}
			String problemIndex = (args[0].substring(1));
			if(problemIndex == null || !problemIndex.equalsIgnoreCase("index")) {
				throw new IllegalArgumentException("only argument name accepted is 'index' but was given - " + args[0].substring(1));
			}
			
			String problemIndexValue = args[1];
			ReasoningProblem problem = null;
			
			if("1".equalsIgnoreCase(problemIndexValue)){
				problem = ProblemTypes.MODUS_PONENS.getProblem();
			}
			if("2".equalsIgnoreCase(problemIndexValue)){
				problem = ProblemTypes.WUMPUS_WORLD.getProblem();
			}
			if("3".equalsIgnoreCase(problemIndexValue)){
				problem = ProblemTypes.HORN_CLAUSES.getProblem();
			}
			if("4a".equalsIgnoreCase(problemIndexValue)){
				problem = ProblemTypes.LIARS_AND_TRUTH_TELLERS_A.getProblem();
			}
			if("4b".equalsIgnoreCase(problemIndexValue)){
				problem = ProblemTypes.LIARS_AND_TRUTH_TELLERS_B.getProblem();
			}
			if("5".equalsIgnoreCase(problemIndexValue)){
				problem = ProblemTypes.MORE_LIARS_AND_TRUTH_TELLERS.getProblem();
			}
			if("6a".equalsIgnoreCase(problemIndexValue)){
				problem = ProblemTypes.DOORS_OF_ENLIGHTENMENT_SMULLYAN_PROBLEM.getProblem();
			}
			if("6b".equalsIgnoreCase(problemIndexValue)){
				problem = ProblemTypes.DOORS_OF_ENLIGHTENMENT_LIU_PROBLEM.getProblem();
			}			
			if(problem == null) {
				throw new IllegalArgumentException("Invalid value for argument 'index' was given " + problemIndexValue
						+ " but can only be {1, 2, 3, 4a, 4b, 5, 6a, 6b}");
			}
			else {
				problem.solveProblem();
			}
		}
		else {
			for(ProblemTypes problemType : ProblemTypes.values()) {
				ReasoningProblem problem = problemType.getProblem();
				problem.solveProblem();
			}
		}
	}

}
