package com.sainsburys.integration.scrapermodule;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;

import com.sainsburys.integration.scrapermodule.exception.BadRequestException;
import com.sainsburys.integration.scrapermodule.exception.JSoupException;

public class JSoupScraper implements ScraperInterface{

	public String scrape(String url) throws Exception {
		
		if(url != null && !"".equals(url)) {
			try {
				return Jsoup.connect(url).get().html();
			} catch (HttpStatusException e) {
				throw new JSoupException("404 Endpoint Not Found", e);
			}
		} else {
			throw new BadRequestException("Null or empty URL provided");
		}
		
	}

}
