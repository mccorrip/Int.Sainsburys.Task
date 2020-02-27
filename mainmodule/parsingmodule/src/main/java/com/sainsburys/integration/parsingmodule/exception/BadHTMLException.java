package com.sainsburys.integration.parsingmodule.exception;

public class BadHTMLException extends Exception {
	public BadHTMLException(String errorMessage) {
        super(errorMessage);
    }
	
	public BadHTMLException(String errorMessage, Throwable err) {
	    super(errorMessage, err);
	}
}
