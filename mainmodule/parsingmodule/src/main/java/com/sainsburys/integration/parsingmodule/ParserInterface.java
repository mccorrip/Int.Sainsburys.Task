package com.sainsburys.integration.parsingmodule;

import java.util.ArrayList;

import com.sainsburys.integration.parsingmodule.exception.BadHTMLException;

public interface ParserInterface {
	
	public ArrayList<String> parseUrls(String html) throws BadHTMLException;
	public Object parseProductData(String html);
	
}
