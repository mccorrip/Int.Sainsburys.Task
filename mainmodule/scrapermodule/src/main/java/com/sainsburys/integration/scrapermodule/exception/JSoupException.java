package com.sainsburys.integration.scrapermodule.exception;

public class JSoupException extends Exception {
	public JSoupException(String errorMessage) {
        super(errorMessage);
    }
	
	public JSoupException(String errorMessage, Throwable err) {
	    super(errorMessage, err);
	}
}
