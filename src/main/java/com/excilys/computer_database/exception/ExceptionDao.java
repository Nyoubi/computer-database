package com.excilys.computer_database.exception;


public class ExceptionDao extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ExceptionDao(String errorMessage) {
        super(errorMessage);
    }
}