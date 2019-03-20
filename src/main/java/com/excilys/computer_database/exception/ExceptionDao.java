package com.excilys.computer_database.exception;

@SuppressWarnings("serial")
public class ExceptionDao extends Exception {

	public ExceptionDao(String errorMessage) {
        super(errorMessage);
    }
}