package com.sainsburys.integration.parsingmodule;

import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.sainsburys.integration.parsingmodule.exception.BadHTMLException;

public class Parser implements ParserInterface{

	public ArrayList<String> parseUrls(String html) throws BadHTMLException {
		//Move these two variables to inputs to allow more extensibility
		String tagName = "div.productInfo";
		String domain = "https://jsainsburyplc.github.io/serverside-test/site/www.sainsburys.co.uk";
		if(html != null && !"".equals(html)) {
			ArrayList<String> urls = new ArrayList<String>();
	        Document doc = Jsoup.parse(html);
	        Elements elements = doc.select("div.productInfo");
	        if(elements.size() > 0) {
		        for(Element element: elements){
		        	Element url = element.select("a").first();
		        	String linkHref = url.attr("href");
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

	public Object parseProductData(String html) {
		
		return null;
	}

}
