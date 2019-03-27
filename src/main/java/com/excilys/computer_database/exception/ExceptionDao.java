package com.excilys.computer_database.exception;


public class ExceptionDao extends Exception {

	private static final long serialVersionUID = -6977007286685678554L;
	
	public String errorMessage;
	public ExceptionDao(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}