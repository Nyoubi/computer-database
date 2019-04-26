package com.excilys.computer_database.exception;


public class DaoException extends Exception {

	private static final long serialVersionUID = -6977007286685678554L;
	
	public String errorMessage;
	public DaoException(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}