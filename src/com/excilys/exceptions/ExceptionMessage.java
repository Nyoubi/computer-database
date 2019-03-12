package com.excilys.exceptions;

public class ExceptionMessage extends Exception { 
    public ExceptionMessage(String errorMessage) {
        super(errorMessage);
    }
}