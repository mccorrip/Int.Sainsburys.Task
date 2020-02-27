package com.sainsburys.integration.parsingmodule;

import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.sainsburys.integration.parsingmodule.exception.BadHTMLException;
import com.sainsburys.integration.parsingmodule.model.Result;

public class JSoupParser extends Parser implements ParserInterface{

	public ArrayList<String> parseUrls(String html, String tagName, String domain) throws BadHTMLException {
		if(html != null && !"".equals(html)) {
			ArrayList<String> urls = new ArrayList<String>();
	        Document doc = Jsoup.parse(html);
	        Elements elements = doc.select(tagName);
	        if(elements.size() > 0) {
		        for(Element element: elements){
		        	String linkHref = element.attr("href");
		        	if(linkHref != null && !"".equals(linkHref)) {
		        		urls.add(linkHref);
		        	};
		        }
		        return cleanUrls(urls, domain);
	        } else {
	        	throw new BadHTMLException("No products found under tag: " + tagName);
	        }
		} else {
			throw new BadHTMLException("Null or empty HTML input");
		}
	}
	
	public Result parseProductData(String html, String titleQuery, String descriptionQuery, String pricePerUnitQuery, String kcal100gQuery) throws BadHTMLException {
		if(html != null && !"".equals(html)) {
	        Document doc = Jsoup.parse(html);
	        String title = parseString(doc, titleQuery);
	        String description = parseString(doc, descriptionQuery);
	        Double pricePerUnit = parseDouble(doc, pricePerUnitQuery);
	        String kcal100g = parseString(doc, kcal100gQuery);
	        return new Result(title, removeNonNumerical(kcal100g), pricePerUnit, description);
		} else {
			throw new BadHTMLException("Null or empty HTML input");
		}
	}

	public String parseString(Document doc, String outerTag) {
		Element string = doc.selectFirst(outerTag);
		if(string != null && string.hasText()) {
			return string.ownText();
		} else {
			return null;
		}
	}
	
	public Double parseDouble(Document doc, String outerTag) {
		String price = parseString(doc, outerTag);
		price = removeCurrencySymbols(price);
		if(price != null) {
			return Double.valueOf(price);
		} else {
			return null;
		}
	}
}
