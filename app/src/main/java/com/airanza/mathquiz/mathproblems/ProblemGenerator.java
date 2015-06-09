package com.airanza.mathquiz.mathproblems;

import java.util.Random;

public class ProblemGenerator {
	private boolean includeAddition = true;
	private boolean includeSubtraction = true;
	private boolean includeMultiplication = true;
	private boolean includeDivision = true;
	private boolean includeNegativeNumbers = true;

	// see the pseudorandom number generator with time:
	private Random generator = new Random(System.currentTimeMillis());
	
	public boolean setIncludeAddition(boolean a) {
		boolean rc = includeAddition;
		includeAddition = a;
		return(rc);
	}

	public boolean setIncludeSubtraction(boolean a) {
		boolean rc = includeSubtraction;
		includeSubtraction = a;
		return(rc);
	}
	
	public boolean setIncludeMultiplication(boolean a) {
		boolean rc = includeMultiplication;
		includeMultiplication = a;
		return(rc);
	}
	
	public boolean setIncludeDivision(boolean a) {
		boolean rc = includeDivision;
		includeDivision = a;
		return(rc);
	}
	
	public boolean setIncludeNegativeNumbers(boolean a) {
		boolean rc = includeNegativeNumbers;
		includeNegativeNumbers = a;
		return(rc);
	}
	
	public Problem generateProblem() {
		if(!(includeAddition || includeSubtraction || includeMultiplication || includeDivision)) {
			System.out.println("AT LEAST ONE OPERATION MUST BE INCLUDED!");
			return (null);
		}
		
		Problem p = new Problem();
		Operation op = null;
		
		// while randomly chosen operation is not OK per the booleans:
		while(!isIncluded(op)) {
			// randomly choose the type of problem:
			int pick = generator.nextInt(Operation.values().length);
			op = Operation.values()[pick];
		}
		
		p.setOperation(op);
				
		if(op == Operation.DIVIDE) {
			int a = generator.nextInt(13);
			int b = 0;
			while ( b== 0 ) {
				b = generator.nextInt(13);
			}
			p.setA(a * b);
			p.setB(b);
			return(p);
		}
		
		if(op == Operation.ADD) { 
			p.setA(generator.nextInt(100));
			p.setB(generator.nextInt(100));
		}
		
		if(op == Operation.SUBTRACT) {
			if(includeNegativeNumbers) {
				p.setA(generator.nextInt(100));
				p.setB(generator.nextInt(100));
			} else {
				int a = generator.nextInt(100);
				int b = generator.nextInt(100);
				p.setA(Math.max(a, b));
				p.setB(Math.min(a, b));
			}
		}
		
		if(op == Operation.MULTIPLY) {
			p.setA(generator.nextInt(13));
			p.setB(generator.nextInt(13));
		}
		return(p);
	}
	
	private boolean isIncluded(Operation op) {
		if(op == null) { return false; }
		if(op == Operation.ADD && includeAddition) { return true; }
		if(op == Operation.SUBTRACT && includeSubtraction) { return true; }
		if(op == Operation.MULTIPLY && includeMultiplication) { return true; }
		if(op == Operation.DIVIDE && includeDivision) { return true; }
		return false;
	}
	
	public boolean getIncludeAddition() {
		return (includeAddition);
	}
	
	public boolean getIncludeSubtraction() {
		return (includeSubtraction);
	}
	
	public boolean getIncludeMultiplication() {
		return (includeMultiplication);
	}
	
	public boolean getIncludeDivision() {
		return (includeDivision);
	}
	
	public boolean getIncludeNegativeNumbers() {
		return (includeNegativeNumbers);
	}
	
	public static void main(String[] args) {
		ProblemGenerator pg = new ProblemGenerator();
		Problem p = null;
		while(true) {
			p = pg.generateProblem();
			System.out.println(p + " " + p.evaluate());
		}
	}
}
