package com.sainsburys.integration.runnermodule;

import java.util.ArrayList;

import com.sainsburys.integration.parsingmodule.ParserInterface;
import com.sainsburys.integration.parsingmodule.model.Result;
import com.sainsburys.integration.runnermodule.model.Response;
import com.sainsburys.integration.scrapermodule.ScraperInterface;

public class Runner {
	public Response init(ScraperInterface scraper, ParserInterface parser, Configurable config) throws Exception {
		String html;

		html = scraper.scrape(config.getUrl());
		ArrayList<String> urlList = parser.parseUrls(html, config.getUrlTagName(), config.getDomain());
		ArrayList<Result> results = new ArrayList<Result>();
		for(String productUrl: urlList) {
			String productHtml = scraper.scrape(productUrl);
			Result result = parser.parseProductData(productHtml, config.getTitleQuery(), config.getDescriptionQuery(), config.getPricePerUnitQuery(), config.getKcal100gQuery());
			results.add(result);
		}
		return new Response(results, config.getVat());
		
	}
}
