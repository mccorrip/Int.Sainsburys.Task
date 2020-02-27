package com.sainsburys.integration.parsingmodule;

import java.util.ArrayList;

public class Parser {

	public ArrayList<String> cleanUrls(ArrayList<String> urls, String domain) {
		ArrayList<String> cleanedUrls = new ArrayList<String>();
		if(urls == null ) {
			return null;
		} else if (domain == null) {
			return urls; 
		} else {
			for(String url: urls) {
				cleanedUrls.add(url.replace("../../../../../..", domain));
			}
		}
		return cleanedUrls;
	}
	
	public Integer removeNonNumerical(String kcal100g) {
		if(kcal100g != null && !"".equals(kcal100g)) {
			kcal100g = kcal100g.replaceAll("[^\\d.]", "");
			if(!"".equals(kcal100g)) {
				return Integer.parseInt(kcal100g);
			}
		}
		return null;
	}

	public String removeCurrencySymbols(String price) {
		if(price != null) {
			return price.replaceAll("\\p{Sc}", "");
		} else {
			return price;
		}
	}

}
