package com.sainsburys.integration.parsingmodule.exception;

public class BadRequestException extends Exception {
	public BadRequestException(String errorMessage) {
        super(errorMessage);
    }
	
	public BadRequestException(String errorMessage, Throwable err) {
	    super(errorMessage, err);
	}
}
