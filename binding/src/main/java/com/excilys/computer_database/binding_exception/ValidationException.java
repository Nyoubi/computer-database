package com.excilys.computer_database.binding_exception;


public class ValidationException extends Exception {

	private static final long serialVersionUID = 4951032303868576816L;
	
	public String errorMessage;
	public ValidationException(String errorMessage) {
        this.errorMessage = errorMessage;
    }
	
	public String getMessage() {
		return this.errorMessage;
	}
}