package com.excilys.computer_database.binding_exception;


public class DaoException extends Exception {

	private static final long serialVersionUID = -6977007286685678554L;
	
	public final String errorMessage;
	public DaoException(String errorMessage) {
        this.errorMessage = errorMessage;
    }
	
	@Override
	public String getMessage() {
		return this.errorMessage;
	}
}