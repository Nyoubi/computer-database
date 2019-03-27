package com.excilys.computer_database.exception;


public class ExceptionModel extends Exception {

	private static final long serialVersionUID = 4951032303868576816L;
	
	public String errorMessage;
	public ExceptionModel(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}