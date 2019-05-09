package com.excilys.computer_database.binding_exception;

public class InvalidInputException extends Exception  {

	private static final long serialVersionUID = -4902264783555761092L;
	
	public final String errorMessage;
	public InvalidInputException(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}