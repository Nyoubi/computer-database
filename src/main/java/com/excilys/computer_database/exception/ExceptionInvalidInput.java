package com.excilys.computer_database.exception;

public class ExceptionInvalidInput extends Exception  {

	private static final long serialVersionUID = -4902264783555761092L;
	
	public String errorMessage;
	public ExceptionInvalidInput(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}