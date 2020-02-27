package com.sainsburys.integration.parsingmodule;

import java.util.ArrayList;

import com.sainsburys.integration.parsingmodule.exception.BadHTMLException;

public interface ParserInterface {
	
	public ArrayList<String> parseUrls(String html, String tagName, String domain) throws BadHTMLException;
	public Object parseProductData(String html, String titleQuery, String descriptionQuery, String pricePerUnitQuery, String kcal100gQuery) throws BadHTMLException;
	
}
