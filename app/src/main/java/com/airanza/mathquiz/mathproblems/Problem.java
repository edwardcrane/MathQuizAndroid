package com.airanza.mathquiz.mathproblems;

public class Problem {
	private Operation operation = null;
	private int a = 0;
	private int b = 0;
	
	public Operation setOperation(Operation op) {
		Operation rc = operation;
		operation = op;
		return rc;
	}
	
	public String toString() {
		return (a + " " + getOperationRepresentation(operation) + " " + b + " = ");
	}
	
	public String getOperationRepresentation(Operation op) {
		switch(op) {
		case ADD:
			return("+");
		case SUBTRACT:
			return("-");
		case MULTIPLY:
			return("x");
		case DIVIDE:
			return("/");
		default:
			return("UNKNOWN OPERATOR");
		}
	}
	
	public void setA(int a) {
		this.a = a;
	}
	
	public void setB(int b) {
		this.b = b;
	}
	
	public int evaluate() {
		switch(operation) {
		case ADD:
			return( a + b );
		case SUBTRACT:
			return( a - b );
		case MULTIPLY:
			return( a * b );
		case DIVIDE:
			if(b == 0) {
				System.out.println("YOU CANNOT DIVIDE BY ZERO!!!");
				return 0;
			}
			return( a / b );
		}
		return 0;
	}
}
